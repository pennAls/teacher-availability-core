package org.example.msteacher.modules.teacher.application_usecase;

import org.springframework.stereotype.Service;
import org.example.msteacher.modules.teacher.infra.dtos.TeacherResponseDto;
import org.example.msteacher.modules.teacher.infra.persistence.TeacherRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ListAllTeachersUseCase {

    private final TeacherRepository teacherRepository;

    public ListAllTeachersUseCase(TeacherRepository teacherRepository) {
        this.teacherRepository = teacherRepository;
    }

    public List<TeacherResponseDto> execute() {
        return teacherRepository.findAll()
                .stream()
                .map(TeacherResponseDto::new)
                .collect(Collectors.toList());
    }
}
