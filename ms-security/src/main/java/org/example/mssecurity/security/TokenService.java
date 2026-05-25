package org.example.mssecurity.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import org.example.mssecurity.modules.users.domain.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Service
public class TokenService {

    @Value("${api.security.token.secret}")
    private String secret;

    public String generateAccessToken(User user) {
        return createToken(user, 2, "access-token");
    }

    public String generateRefreshToken(User user) {
        return createToken(user, 48, "refresh-token");
    }

    private String createToken(User user, int hours, String subject_type) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);
            return JWT.create()
                    .withIssuer("ms-security")
                    .withSubject(user.getId().toString())
                    .withClaim("type", subject_type)
                    .withClaim("role", user.getRole().name())
                    .withExpiresAt(genExpirationDate(hours))
                    .sign(algorithm);
        } catch (Exception e) {
            throw new RuntimeException("Erro ao gerar token", e);
        }
    }

    public String validateToken(String token) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);
            return JWT.require(algorithm)
                    .withIssuer("ms-security")
                    .build()
                    .verify(token)
                    .getSubject();
        } catch (Exception e) {
            return null;
        }
    }
    public String validateRefreshToken(String token) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);
            return JWT.require(algorithm)
                    .withIssuer("ms-security")
                    .withClaim("type", "refresh-token")
                    .build()
                    .verify(token)
                    .getSubject();
        } catch (Exception exception) {
            return null;
        }
    }

    private Instant genExpirationDate(int hours) {
        return LocalDateTime.now().plusHours(hours).toInstant(ZoneOffset.UTC);
    }
}