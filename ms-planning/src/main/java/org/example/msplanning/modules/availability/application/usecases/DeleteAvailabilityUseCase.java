package org.example.msplanning.modules.availability.application.usecases;

import org.example.msplanning.modules.availability.domain.exceptions.AvailabilityNotFoundException;
import org.example.msplanning.modules.availability.infra.persistence.AvailabilityRepository;
import org.example.msplanning.modules.interests.domain.exceptions.UniqueSchoolViolationException;
import org.example.msplanning.modules.interests.infra.clients.TeacherClient;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class DeleteAvailabilityUseCase {

    private final AvailabilityRepository availabilityRepository;
    private final TeacherClient teacherClient;

    public DeleteAvailabilityUseCase(
            AvailabilityRepository availabilityRepository,
            TeacherClient teacherClient
    ) {
        this.availabilityRepository = availabilityRepository;
        this.teacherClient = teacherClient;
    }

    public void execute(
            UUID availabilityId,
            String bearerToken
    ) {

        var teacher = teacherClient.getMe(bearerToken);

        var availability = availabilityRepository.findById(availabilityId)
                .orElseThrow(() ->
                        new AvailabilityNotFoundException(
                                "Bloco de horário não encontrado."
                        )
                );

        if (!availability.getTeacherId().equals(teacher.id())) {
            throw new UniqueSchoolViolationException(
                    "Você não pode remover o horário de outro professor."
            );
        }

        availabilityRepository.delete(availability);
    }
}