package org.example.msacademic.modules.disciplines.infra.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.util.UUID;

public record DisciplineRequestDto(@NotBlank(message = "A sigla é obrigatória.")
                                    String acronym,

                                   @NotBlank(message = "O nome da disciplina é obrigatório.")
                                    String description,

                                   @NotNull(message = "A carga horária é obrigatória.")
                                    @Positive(message = "A carga horária deve ser maior que zero.")
                                    Double workload,

                                   @NotNull(message = "O ID da escola é obrigatório.")
                                   UUID schoolId) {
}
