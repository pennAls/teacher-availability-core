package org.example.msplanning.modules.interests.infra.dtos;

import java.util.List;
import java.util.UUID;

public record AdminTeacherInterestsReportDto(
        UUID teacherId,
        String teacherName,
        List<InterestDetailDto> interests
) {
    public record InterestDetailDto(
            String acronym,
            String description,
            Integer priority
    ) {}
}