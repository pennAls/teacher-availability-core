package org.example.msplanning.modules.availability.application.usecases;

import org.example.msplanning.modules.availability.infra.dtos.AvailabilityResponseDto;
import org.example.msplanning.modules.availability.infra.persistence.AvailabilityRepository;
import org.example.msplanning.modules.interests.infra.clients.TeacherClient;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class GetMyAvailabilitiesUseCase {

    private final AvailabilityRepository availabilityRepository;
    private final TeacherClient teacherClient;

    public GetMyAvailabilitiesUseCase(
            AvailabilityRepository availabilityRepository,
            TeacherClient teacherClient
    ) {
        this.availabilityRepository = availabilityRepository;
        this.teacherClient = teacherClient;
    }

    public List<AvailabilityResponseDto> execute(String bearerToken) {

        var teacher = teacherClient.getMe(bearerToken);

        var availabilities = availabilityRepository.findByTeacherId(teacher.id());

        return availabilities.stream()
                .sorted((a1, a2) -> {
                    int dayCompare = a1.getDayOfWeek().compareTo(a2.getDayOfWeek());

                    if (dayCompare != 0) {
                        return dayCompare;
                    }

                    return a1.getStartTime().compareTo(a2.getStartTime());
                })
                .map(AvailabilityResponseDto::new)
                .collect(Collectors.toList());
    }
}