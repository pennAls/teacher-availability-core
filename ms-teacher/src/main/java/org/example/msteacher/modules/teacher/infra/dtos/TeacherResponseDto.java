package org.example.msteacher.modules.teacher.infra.dtos;

import org.example.msteacher.modules.academicDegree.infra.dtos.AcademicDegreeResponseDto;
import org.example.msteacher.modules.teacher.domain.Teacher;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public record TeacherResponseDto(
        UUID id,
        UUID userId,
        String institutionalEmail,
        String fullName,
        String registration,
        String phone,
        UUID schoolId,
        List<AcademicDegreeResponseDto> degrees
) {
    public TeacherResponseDto(Teacher teacher) {
        this(
                teacher.getId(),
                teacher.getUserId(),
                teacher.getInstitutionalEmail(),
                teacher.getFullName(),
                teacher.getRegistration(),
                teacher.getPhone(),
                teacher.getSchoolId(),
                teacher.getDegrees().stream()
                        .map(AcademicDegreeResponseDto::new)
                        .collect(Collectors.toList())
        );
    }
}
