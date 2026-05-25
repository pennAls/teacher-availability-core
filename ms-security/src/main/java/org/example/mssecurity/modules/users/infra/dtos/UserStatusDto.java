package org.example.mssecurity.modules.users.infra.dtos;
import jakarta.validation.constraints.NotNull;

public record  UserStatusDto(
        @NotNull(message = "o status não pode ser vazio")
        boolean isActive
) {}
