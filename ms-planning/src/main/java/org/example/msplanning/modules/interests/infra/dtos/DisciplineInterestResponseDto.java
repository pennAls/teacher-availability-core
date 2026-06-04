package org.example.msplanning.modules.interests.infra.dtos;

import org.example.msplanning.modules.interests.domain.DisciplineInterest;

import java.util.UUID;

public record DisciplineInterestResponseDto(
        UUID id,
        Integer priority,
        DisciplineSummaryDto discipline
) {
    public DisciplineInterestResponseDto(DisciplineInterest interest, DisciplineClientResponse disciplineData) {
        this(
                interest.getId(),
                interest.getPriority(),
                new DisciplineSummaryDto(
                        disciplineData.id(),
                        disciplineData.acronym(),
                        disciplineData.description(),
                        disciplineData.workload()
                )
        );
    }

    public record DisciplineSummaryDto(UUID id, String acronym, String description, Double workload) {}
}