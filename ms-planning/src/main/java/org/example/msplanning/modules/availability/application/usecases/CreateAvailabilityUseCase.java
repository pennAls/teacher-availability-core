package com.example.teacheravailabilityapi.modules.availability.application.usecases;

import com.example.teacheravailabilityapi.exceptions.InactiveEntityException;
import com.example.teacheravailabilityapi.modules.availability.domain.Availability;
import com.example.teacheravailabilityapi.modules.availability.domain.exceptions.DuplicateAvailabilityException;
import com.example.teacheravailabilityapi.modules.availability.domain.utils.AvailabilityValidator;
import com.example.teacheravailabilityapi.modules.availability.infra.dtos.CreateAvailabilityRequestDto;
import com.example.teacheravailabilityapi.modules.availability.infra.persistence.AvailabilityRepository;
import com.example.teacheravailabilityapi.modules.teacher.domain.exceptions.TeacherNotFoundException;
import com.example.teacheravailabilityapi.modules.teacher.infra.persistence.TeacherRepository;
import com.example.teacheravailabilityapi.utils.UseAuth;
import org.springframework.stereotype.Service;

@Service
public class CreateAvailabilityUseCase {

    private final AvailabilityRepository availabilityRepository;
    private final TeacherRepository teacherRepository;

    public CreateAvailabilityUseCase(AvailabilityRepository availabilityRepository, TeacherRepository teacherRepository) {
        this.availabilityRepository = availabilityRepository;
        this.teacherRepository = teacherRepository;
    }

    public Availability execute(CreateAvailabilityRequestDto data) {

        var teacher = teacherRepository.findByUserId(UseAuth.GetAuthenticatedUser())
                .orElseThrow(() -> new TeacherNotFoundException("Perfil de professor não encontrado."));

        if (!teacher.getSchool().getIsActive()) {
            throw new InactiveEntityException("Sua escola encontra-se inativa. Não é possível cadastrar horários no momento.");
        }

        AvailabilityValidator.validate(data.dayOfWeek(), data.startTime(), data.endTime());

        boolean alreadyExists = availabilityRepository.existsByTeacherIdAndDayOfWeekAndStartTime(
                teacher.getId(),
                data.dayOfWeek(),
                data.startTime()
        );

        if (alreadyExists) {
            throw new DuplicateAvailabilityException(
                    "Você já possui disponibilidade cadastrada para " + data.dayOfWeek().getDescription() +
                            " às " + data.startTime() + "."
            );
        }

        Availability availability = new Availability(
                data.dayOfWeek(),
                data.startTime(),
                data.endTime(),
                teacher
        );

        return availabilityRepository.save(availability);
    }
}