package com.example.teacheravailabilityapi.modules.interests.infra.dtos;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record CreateInterestRequestDto(
        @NotNull(message = "O ID da disciplina é obrigatório.")
        UUID disciplineId,

        @NotNull(message = "A prioridade é obrigatória.")
        @Min(value = 1, message = "A prioridade mínima é 1.")
        @Max(value = 5, message = "A prioridade máxima é 5.")
        Integer priority
) {}