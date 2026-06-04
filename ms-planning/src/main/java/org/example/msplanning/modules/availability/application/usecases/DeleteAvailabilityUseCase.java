package com.example.teacheravailabilityapi.modules.availability.application.usecases;

import com.example.teacheravailabilityapi.modules.availability.domain.exceptions.AvailabilityNotFoundException;
import com.example.teacheravailabilityapi.modules.availability.infra.persistence.AvailabilityRepository;
import com.example.teacheravailabilityapi.modules.interests.domain.exceptions.UniqueSchoolViolationException;
import com.example.teacheravailabilityapi.modules.teacher.domain.exceptions.TeacherNotFoundException;
import com.example.teacheravailabilityapi.modules.teacher.infra.persistence.TeacherRepository;
import com.example.teacheravailabilityapi.utils.UseAuth;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class DeleteAvailabilityUseCase {

    private final AvailabilityRepository availabilityRepository;
    private final TeacherRepository teacherRepository;

    public DeleteAvailabilityUseCase(AvailabilityRepository availabilityRepository, TeacherRepository teacherRepository) {
        this.availabilityRepository = availabilityRepository;
        this.teacherRepository = teacherRepository;
    }

    public void execute(UUID availabilityId) {

        var teacher = teacherRepository.findByUserId(UseAuth.GetAuthenticatedUser())
                .orElseThrow(() -> new TeacherNotFoundException("Perfil de professor não encontrado."));

        var availability = availabilityRepository.findById(availabilityId)
                .orElseThrow(() -> new AvailabilityNotFoundException("Bloco de horário não encontrado."));

        if (!availability.getTeacher().getId().equals(teacher.getId())) {
            throw new UniqueSchoolViolationException("Você não pode remover o horário de outro professor.");
        }

        availabilityRepository.delete(availability);
    }
}