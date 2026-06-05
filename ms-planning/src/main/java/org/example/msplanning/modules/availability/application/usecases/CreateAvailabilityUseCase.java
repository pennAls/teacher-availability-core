package org.example.msplanning.modules.availability.application.usecases;

import org.example.msplanning.modules.availability.domain.Availability;
import org.example.msplanning.modules.availability.domain.exceptions.DuplicateAvailabilityException;
import org.example.msplanning.modules.availability.domain.utils.AvailabilityValidator;
import org.example.msplanning.modules.availability.infra.dtos.CreateAvailabilityRequestDto;
import org.example.msplanning.modules.availability.infra.persistence.AvailabilityRepository;
import org.example.msplanning.modules.interests.infra.clients.TeacherClient;
import org.springframework.stereotype.Service;

@Service
public class CreateAvailabilityUseCase {

    private final AvailabilityRepository availabilityRepository;
    private final TeacherClient teacherClient;

    public CreateAvailabilityUseCase(
            AvailabilityRepository availabilityRepository,
            TeacherClient teacherClient
    ) {
        this.availabilityRepository = availabilityRepository;
        this.teacherClient = teacherClient;
    }

    public Availability execute(
            CreateAvailabilityRequestDto data,
            String bearerToken
    ) {

        var teacher = teacherClient.getMe(bearerToken);

        AvailabilityValidator.validate(
                data.dayOfWeek(),
                data.startTime(),
                data.endTime()
        );

        boolean alreadyExists =
                availabilityRepository.existsByTeacherIdAndDayOfWeekAndStartTime(
                        teacher.id(),
                        data.dayOfWeek(),
                        data.startTime()
                );

        if (alreadyExists) {
            throw new DuplicateAvailabilityException(
                    "Você já possui disponibilidade cadastrada para "
                            + data.dayOfWeek().getDescription()
                            + " às "
                            + data.startTime()
                            + "."
            );
        }

        Availability availability = new Availability(
                data.dayOfWeek(),
                data.startTime(),
                data.endTime(),
                teacher.id()
        );

        return availabilityRepository.save(availability);
    }
}