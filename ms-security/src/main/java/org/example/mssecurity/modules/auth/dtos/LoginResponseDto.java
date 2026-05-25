package org.example.mssecurity.modules.auth.dtos;

public record LoginResponseDto(
        String accessToken,
        String refreshToken
) {}