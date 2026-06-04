package org.example.msplanning.modules.interests.infra.dtos;


import java.util.UUID;

public record DisciplineClientResponse(
        UUID id,
        String name,
        String acronym,
        String description,
        boolean isActive,
        UUID schoolId,
        double workload
) {}