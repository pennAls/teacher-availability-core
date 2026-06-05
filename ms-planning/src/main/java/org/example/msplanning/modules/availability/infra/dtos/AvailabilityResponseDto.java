package org.example.msplanning.modules.availability.infra.dtos;

import org.example.msplanning.modules.availability.domain.Availability;
import org.example.msplanning.modules.availability.domain.types.DayOfWeek;

import java.time.LocalTime;
import java.util.UUID;

public record AvailabilityResponseDto(
        UUID id,
        DayOfWeek dayOfWeek,
        LocalTime startTime,
        LocalTime endTime
) {
    public AvailabilityResponseDto(Availability availability) {
        this(
                availability.getId(),
                availability.getDayOfWeek(),
                availability.getStartTime(),
                availability.getEndTime()
        );
    }
}