package org.example.msacademic.modules.disciplines.infra.dtos;

import jakarta.validation.constraints.NotNull;

public record DisciplineStatusDto(@NotNull(message = "O campo isActive é obrigatório.")
                                   Boolean isActive) {
}
