package org.example.msacademic.modules.schools.infra.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record SchoolRequestDto(
    @NotBlank(message = "O nome da escola é obrigatório.")
    String name,

    @NotBlank(message = "O nome do coordenador é obrigatório.")
    String coordinator,

    @NotNull(message = "A instituição (IES) é obrigatória.")
    UUID iesId
) {
}
