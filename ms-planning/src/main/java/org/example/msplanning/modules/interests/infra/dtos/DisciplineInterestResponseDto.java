package com.example.teacheravailabilityapi.modules.interests.infra.dtos;

import com.example.teacheravailabilityapi.modules.interests.domain.DisciplineInterest;
import java.util.UUID;

public record DisciplineInterestResponseDto(
        UUID id,
        Integer priority,
        DisciplineSummaryDto discipline
) {
    public DisciplineInterestResponseDto(DisciplineInterest interest) {
        this(
                interest.getId(),
                interest.getPriority(),
                new DisciplineSummaryDto(
                        interest.getDiscipline().getId(),
                        interest.getDiscipline().getAcronym(),
                        interest.getDiscipline().getDescription(),
                        interest.getDiscipline().getWorkload()
                )
        );
    }
    public record DisciplineSummaryDto(UUID id, String acronym, String description, Double workload) {}
}