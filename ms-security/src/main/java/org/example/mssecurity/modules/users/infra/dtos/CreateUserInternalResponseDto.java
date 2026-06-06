package org.example.mssecurity.modules.users.infra.dtos;

import java.util.UUID;

public record CreateUserInternalResponseDto(
        UUID userId
) {}
