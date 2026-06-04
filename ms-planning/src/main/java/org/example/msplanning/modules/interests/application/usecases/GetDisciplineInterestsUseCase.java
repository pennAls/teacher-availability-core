package com.example.teacheravailabilityapi.modules.interests.application.usecases;

import com.example.teacheravailabilityapi.modules.interests.infra.dtos.DisciplineInterestResponseDto;
import com.example.teacheravailabilityapi.modules.interests.infra.persistence.DisciplineInterestRepository;
import com.example.teacheravailabilityapi.modules.teacher.domain.exceptions.TeacherNotFoundException;
import com.example.teacheravailabilityapi.modules.teacher.infra.persistence.TeacherRepository;
import com.example.teacheravailabilityapi.modules.users.domain.User;
import com.example.teacheravailabilityapi.utils.UseAuth;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class GetDisciplineInterestsUseCase {

    private final DisciplineInterestRepository interestRepository;
    private final TeacherRepository teacherRepository;

    public GetDisciplineInterestsUseCase(DisciplineInterestRepository interestRepository, TeacherRepository teacherRepository) {
        this.interestRepository = interestRepository;
        this.teacherRepository = teacherRepository;
    }

    public List<DisciplineInterestResponseDto> execute() {

        var teacher = teacherRepository.findByUserId(UseAuth.GetAuthenticatedUser())
                .orElseThrow(() -> new TeacherNotFoundException("Perfil de professor não encontrado."));

        return interestRepository.findByTeacherIdOrderByPriorityAsc(teacher.getId())
                .stream()
                .map(DisciplineInterestResponseDto::new)
                .collect(Collectors.toList());
    }
}