package org.example.msteacher.modules.academicDegree.infra.dtos;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.example.msteacher.modules.academicDegree.domain.types.DegreeCategory;

public record AcademicDegreeRequestDto(
        @NotNull(message = "A categoria da titulação é obrigatória.")
        DegreeCategory category,

        @NotBlank(message = "A instituição é obrigatória.")
        String institution,

        @NotBlank(message = "O curso é obrigatório.")
        String course,

        @NotNull(message = "O ano de conclusão é obrigatório.")
        @Min(value = 1950, message = "Ano inválido.")
        Integer year
) {}