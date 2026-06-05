package org.example.msteacher.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import lombok.Value;
import org.example.mssecurity.modules.users.domain.User;
import org.joda.time.Instant;
import org.joda.time.LocalDateTime;
import org.springframework.stereotype.Service;

@Service
public class TokenService {

    @Value("${api.security.token.secret}")
    private String secret;

    public String generateAccessToken(User user) {
        return createToken(user, 8, "access-token");
    }

    public String generateRefreshToken(User user) {
        return createToken(user, 48, "refresh-token");
    }

    private String createToken(User user, int hours, String subject_type) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);
            return JWT.create()
                    .withIssuer("teacher-availability-api")
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
                    .withIssuer("teacher-availability-api")
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
                    .withIssuer("teacher-availability-api")
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