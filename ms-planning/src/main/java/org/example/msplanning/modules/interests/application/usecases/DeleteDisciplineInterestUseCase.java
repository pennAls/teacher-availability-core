package com.example.teacheravailabilityapi.modules.interests.application.usecases;

import com.example.teacheravailabilityapi.modules.interests.domain.exceptions.DisciplineInterestNotFoundException;
import com.example.teacheravailabilityapi.modules.interests.domain.exceptions.UniqueSchoolViolationException;
import com.example.teacheravailabilityapi.modules.interests.infra.persistence.DisciplineInterestRepository;
import com.example.teacheravailabilityapi.modules.teacher.domain.exceptions.TeacherNotFoundException;
import com.example.teacheravailabilityapi.modules.teacher.infra.persistence.TeacherRepository;
import com.example.teacheravailabilityapi.modules.users.domain.User;
import com.example.teacheravailabilityapi.utils.UseAuth;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class DeleteDisciplineInterestUseCase {
    private final DisciplineInterestRepository interestRepository;
    private final TeacherRepository teacherRepository;

    public DeleteDisciplineInterestUseCase(DisciplineInterestRepository interestRepository, TeacherRepository teacherRepository) {
        this.interestRepository = interestRepository;
        this.teacherRepository = teacherRepository;
    }

    public void execute(UUID interestId) {

        var teacher = teacherRepository.findByUserId(UseAuth.GetAuthenticatedUser())
                .orElseThrow(() -> new TeacherNotFoundException("Professor não encontrado."));

        var interest = interestRepository.findById(interestId)
                .orElseThrow(() -> new DisciplineInterestNotFoundException("Registro de interesse não encontrado."));

        if (!interest.getTeacher().getId().equals(teacher.getId())) {
            throw new UniqueSchoolViolationException("Acesso negado: Você não pode remover o interesse de outro professor.");
        }

        interestRepository.delete(interest);
    }
}
