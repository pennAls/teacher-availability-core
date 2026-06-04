package com.example.teacheravailabilityapi.modules.availability.infra.dtos;

import java.time.LocalTime;
import java.util.List;
import java.util.UUID;

public record AdminTeacherAvailabilityReportDto(
        UUID teacherId,
        String teacherName,
        List<AvailabilityDetailDto> availabilities
) {
    public record AvailabilityDetailDto(
        String dayOfWeek,
        LocalTime startTime,
        LocalTime endTime
    ) {}
}


