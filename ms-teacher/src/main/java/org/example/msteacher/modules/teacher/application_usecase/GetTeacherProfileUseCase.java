package org.example.msteacher.modules.teacher.application_usecase;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.example.msteacher.modules.teacher.domain.exceptions.TeacherNotFoundException;
import org.example.msteacher.modules.teacher.infra.dtos.TeacherResponseDto;
import org.example.msteacher.modules.teacher.infra.persistence.TeacherRepository;

import java.util.UUID;

@Service
public class GetTeacherProfileUseCase {

    private final TeacherRepository teacherRepository;

    public GetTeacherProfileUseCase(TeacherRepository teacherRepository) {
        this.teacherRepository = teacherRepository;
    }

    public TeacherResponseDto execute() {
        var authentication = SecurityContextHolder.getContext().getAuthentication();
        UUID userId = UUID.fromString((String) authentication.getPrincipal());

        var teacher = teacherRepository.findByUserId(userId)
                .orElseThrow(() -> new TeacherNotFoundException("Perfil de professor não encontrado."));

        return new TeacherResponseDto(teacher);
    }
}