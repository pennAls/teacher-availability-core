package org.example.msteacher.modules.teacher.infra.dtos;

import org.example.mssecurity.modules.users.domain.types.UserRole;
import org.example.msteacher.modules.academicDegree.infra.dtos.AcademicDegreeResponseDto;
import org.hibernate.mapping.List;
import org.hibernate.validator.constraints.UUID;
import org.example.msteacher.modules.teacher.domain.Teacher;

public record TeacherResponseDto(
        UUID id,
        UUID userId,
        String email,
        UserRole role,
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
                teacher.getUser().getId(),
                teacher.getUser().getEmail(),
                teacher.getUser().getRole(),
                teacher.getInstitutionalEmail(),
                teacher.getFullName(),
                teacher.getRegistration(),
                teacher.getPhone(),
                teacher.getSchool().getId(),
                teacher.getDegrees().stream()
                        .map(AcademicDegreeResponseDto::new)
                        .collect(Collectors.toList())
        );
    }
}
