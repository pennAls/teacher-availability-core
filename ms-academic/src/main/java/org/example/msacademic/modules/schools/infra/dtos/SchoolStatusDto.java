package org.example.msacademic.modules.schools.infra.dtos;

import jakarta.validation.constraints.NotNull;

public record SchoolStatusDto(@NotNull(message = "O campo isActive é obrigatório.")
                              Boolean isActive) {

}
