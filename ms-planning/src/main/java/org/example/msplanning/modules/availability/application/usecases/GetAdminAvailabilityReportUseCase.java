package com.example.teacheravailabilityapi.modules.availability.application.usecases;
import com.example.teacheravailabilityapi.modules.availability.domain.Availability;
import com.example.teacheravailabilityapi.modules.availability.infra.dtos.AdminTeacherAvailabilityReportDto;
import com.example.teacheravailabilityapi.modules.availability.infra.persistence.AvailabilityRepository;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class GetAdminAvailabilityReportUseCase {
    private final AvailabilityRepository repository;

    public GetAdminAvailabilityReportUseCase(AvailabilityRepository repository) {
        this.repository = repository;
    }

    public List<AdminTeacherAvailabilityReportDto> execute() {
        var allAvailabilities = repository.findAllForAdminReport();

        var groupedByTeacher = allAvailabilities.stream()
                .collect(Collectors.groupingBy(
                        Availability::getTeacher,
                        LinkedHashMap::new,
                        Collectors.toList()
                ));

        return groupedByTeacher.entrySet().stream().map(entry -> {
            var teacher = entry.getKey();

            var availabilityList = entry.getValue().stream()
                    .sorted((a1, a2) -> {
                        int dayCompare = a1.getDayOfWeek().compareTo(a2.getDayOfWeek());
                        if (dayCompare != 0) return dayCompare;
                        return a1.getStartTime().compareTo(a2.getStartTime());
                    })
                    .map(a -> new AdminTeacherAvailabilityReportDto.AvailabilityDetailDto(
                            a.getDayOfWeek().getDescription(),
                            a.getStartTime(),
                            a.getEndTime()
                    ))
                    .toList();

            return new AdminTeacherAvailabilityReportDto(
                    teacher.getId(),
                    teacher.getFullName(),
                    availabilityList
            );
        }).toList();
    }
}