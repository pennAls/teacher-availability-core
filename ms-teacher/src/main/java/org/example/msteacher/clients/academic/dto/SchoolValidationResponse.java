package org.example.msteacher.clients.academic.dto;

import java.util.UUID;

public record SchoolValidationResponse(
        UUID id,
        String name,
        String coordinator,
        Boolean isActive
) {}
