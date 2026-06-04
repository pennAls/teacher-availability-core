package org.example.msplanning.modules.interests.infra.dtos;

import java.util.UUID;

public record TeacherClientResponse(
        UUID id,
        String name,
        UUID userId,
        UUID schoolId
) {}