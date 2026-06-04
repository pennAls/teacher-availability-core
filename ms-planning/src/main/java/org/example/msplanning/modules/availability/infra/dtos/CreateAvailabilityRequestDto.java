package com.example.teacheravailabilityapi.modules.availability.infra.dtos;

import com.example.teacheravailabilityapi.modules.availability.domain.types.DayOfWeek;
import jakarta.validation.constraints.NotNull;
import java.time.LocalTime;

public record CreateAvailabilityRequestDto(
        @NotNull(message = "O dia da semana é obrigatório.")
        DayOfWeek dayOfWeek,

        @NotNull(message = "O horário de início é obrigatório.")
        LocalTime startTime,

        @NotNull(message = "O horário de término é obrigatório.")
        LocalTime endTime
) {}