package org.example.msteacher.modules.academicDegree.infra.dtos;


import org.example.msteacher.modules.academicDegree.domain.AcademicDegree;

import java.util.UUID;

public record AcademicDegreeResponseDto(
        UUID id,
        String category,
        String institution,
        String course,
        Integer year
) {
    public AcademicDegreeResponseDto(AcademicDegree degree) {
        this(
                degree.getId(),
                degree.getCategory().name(),
                degree.getInstitution(),
                degree.getCourse(),
                degree.getDegreeYear()
        );
    }
}
