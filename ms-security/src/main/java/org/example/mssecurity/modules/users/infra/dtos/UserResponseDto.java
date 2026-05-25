package org.example.mssecurity.modules.users.infra.dtos;

import org.example.mssecurity.modules.users.domain.User;
import org.example.mssecurity.modules.users.domain.types.UserRole;

import java.util.UUID;

public record UserResponseDto(
        UUID id,
        String email,
        UserRole role,
        Boolean isActive
) {
    public static UserResponseDto fromEntity(User user) {
        return new UserResponseDto(
                user.getId(),
                user.getEmail(),
                user.getRole(),
                user.getIsActive()
        );

    }
}