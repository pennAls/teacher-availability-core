package org.example.msacademic.modules.disciplines.infra.dtos;

import org.example.msacademic.modules.disciplines.domain.Discipline;

import java.time.LocalDateTime;
import java.util.UUID;

public record DisciplineResponseDto(UUID id,
                                    String acronym,
                                    String description,
                                    Double workload,
                                    String schoolName,
                                    LocalDateTime createdAt,
                                    Boolean isActive) {

    public DisciplineResponseDto(Discipline discipline) {
        this(
                discipline.getId(),
                discipline.getAcronym(),
                discipline.getDescription(),
                discipline.getWorkload(),
                discipline.getSchool().getName(),
                discipline.getCreatedAt(),
                discipline.getIsActive()
        );
    }
}
