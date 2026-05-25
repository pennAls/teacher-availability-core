package org.example.mssecurity.modules.auth;

import jakarta.validation.Valid;
import org.example.mssecurity.modules.auth.application.usecases.RefreshTokenUseCase;
import org.example.mssecurity.modules.auth.dtos.LoginRequestDto;
import org.example.mssecurity.modules.auth.dtos.LoginResponseDto;
import org.example.mssecurity.modules.auth.dtos.RefreshTokenRequestDto;
import org.example.mssecurity.modules.users.domain.User;
import org.example.mssecurity.security.TokenService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final RefreshTokenUseCase refreshTokenUseCase;
    private final TokenService tokenService;

    public AuthController(AuthenticationManager authenticationManager, RefreshTokenUseCase refreshTokenUseCase, TokenService tokenService) {
        this.authenticationManager = authenticationManager;
        this.refreshTokenUseCase = refreshTokenUseCase;
        this.tokenService = tokenService;
    }

    @PostMapping("/login")
    public ResponseEntity<Object> login(@RequestBody @Valid LoginRequestDto data) {
        try {
            var usernamePassword = new UsernamePasswordAuthenticationToken(data.email(), data.password());
            var auth = this.authenticationManager.authenticate(usernamePassword);

            var user = (User) auth.getPrincipal();

            var accessToken = tokenService.generateAccessToken(user);
            var refreshToken = tokenService.generateRefreshToken(user);

            return ResponseEntity.ok(new LoginResponseDto(accessToken, refreshToken));
        } catch (DisabledException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body(Map.of(
                            "code", 403,
                            "error", "Sua conta foi desativada. Entre em contato com o suporte."
                    ));
        } catch (BadCredentialsException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of(
                            "code", 401,
                            "error", "E-mail ou senha incorretos."
                    ));
        }
    }

    @PostMapping("/refresh")
    public ResponseEntity<LoginResponseDto> refresh(@RequestBody @Valid RefreshTokenRequestDto data) {
        var userIdStr = tokenService.validateRefreshToken(data.refreshToken());

        if (userIdStr == null) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        User user = refreshTokenUseCase.execute(UUID.fromString(userIdStr));

        if (user == null) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        var newAccessToken = tokenService.generateAccessToken(user);
        var newRefreshToken = tokenService.generateRefreshToken(user);

        return ResponseEntity.ok(new LoginResponseDto(newAccessToken, newRefreshToken));
    }
}