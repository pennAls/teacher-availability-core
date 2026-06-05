package org.example.msplanning.modules.availability.application.usecases;

import org.example.msplanning.modules.availability.infra.dtos.AdminTeacherAvailabilityReportDto;
import org.example.msplanning.modules.availability.infra.persistence.AvailabilityRepository;
import org.example.msplanning.modules.interests.infra.clients.TeacherClient;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class GetAdminAvailabilityReportUseCase {

    private final AvailabilityRepository repository;
    private final TeacherClient teacherClient;

    public GetAdminAvailabilityReportUseCase(
            AvailabilityRepository repository,
            TeacherClient teacherClient
    ) {
        this.repository = repository;
        this.teacherClient = teacherClient;
    }

    public List<AdminTeacherAvailabilityReportDto> execute(String bearerToken) {

        var allAvailabilities = repository.findAll();

        var teachers = teacherClient.getAllTeachers(bearerToken);

        var groupedByTeacher = allAvailabilities.stream()
                .collect(Collectors.groupingBy(
                        availability -> availability.getTeacherId(),
                        LinkedHashMap::new,
                        Collectors.toList()
                ));

        return groupedByTeacher.entrySet().stream()
                .map(entry -> {

                    var teacherId = entry.getKey();

                    var teacher = teachers.stream()
                            .filter(t -> t.id().equals(teacherId))
                            .findFirst()
                            .orElse(null);

                    var availabilityList = entry.getValue().stream()
                            .sorted((a1, a2) -> {
                                int dayCompare =
                                        a1.getDayOfWeek().compareTo(a2.getDayOfWeek());

                                if (dayCompare != 0) {
                                    return dayCompare;
                                }

                                return a1.getStartTime()
                                        .compareTo(a2.getStartTime());
                            })
                            .map(a ->
                                    new AdminTeacherAvailabilityReportDto.AvailabilityDetailDto(
                                            a.getDayOfWeek().getDescription(),
                                            a.getStartTime(),
                                            a.getEndTime()
                                    )
                            )
                            .toList();

                    return new AdminTeacherAvailabilityReportDto(
                            teacherId,
                            teacher != null ? teacher.name() : "Professor não encontrado",
                            availabilityList
                    );
                })
                .toList();
    }
}