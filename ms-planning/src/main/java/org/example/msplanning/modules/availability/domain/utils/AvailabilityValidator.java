package com.example.teacheravailabilityapi.modules.availability.domain.utils;
import com.example.teacheravailabilityapi.modules.availability.domain.exceptions.InvalidAvailabilityException;
import com.example.teacheravailabilityapi.modules.availability.domain.types.DayOfWeek;

import java.time.Duration;
import java.time.LocalTime;
import java.util.List;

public class AvailabilityValidator {

    private static final List<LocalTime> VALID_START_TIMES = List.of(
            LocalTime.of(7, 0),
            LocalTime.of(9, 50),
            LocalTime.of(13, 0),
            LocalTime.of(15, 50),
            LocalTime.of(19, 0)
    );

    public static void validate(DayOfWeek dayOfWeek, LocalTime startTime, LocalTime endTime) {

        if (dayOfWeek == DayOfWeek.SUNDAY) {
            throw new InvalidAvailabilityException("Não é permitido cadastrar disponibilidade aos domingos.");
        }

        long durationInMinutes = Duration.between(startTime, endTime).toMinutes();
        if (durationInMinutes != 150) {
            throw new InvalidAvailabilityException("O período informado deve ter a duração exata de 2 horas e 30 minutos.");
        }

        if (!VALID_START_TIMES.contains(startTime)) {
            throw new InvalidAvailabilityException(
                    "Horário de início inválido. Os turnos devem iniciar às 07:00, 09:50, 13:00, 15:50 ou 19:00."
            );
        }


        if (dayOfWeek == DayOfWeek.SATURDAY && startTime.isAfter(LocalTime.of(12, 0))) {
            throw new InvalidAvailabilityException("Aos sábados, apenas o turno da MANHÃ é permitido (07:00 ou 09:50).");
        }
    }
}