package org.example.msacademic.modules.schools.infra.dtos;

import org.example.msacademic.modules.ies.domain.Ies;
import org.example.msacademic.modules.schools.domain.School;

import java.util.UUID;

public record SchoolResponseDto(
    UUID id,
    String name,
    String coordinator,
    Ies ies,
    Boolean isActive
) {
    public SchoolResponseDto(School school) {
            this(school.getId(), school.getName(), school.getCoordinator(), school.getIes(), school.getIsActive());
        }
}
