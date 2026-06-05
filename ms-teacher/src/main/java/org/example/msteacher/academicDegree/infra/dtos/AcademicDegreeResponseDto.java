package org.example.msteacher.academicDegree.infra.dtos;


import org.example.msteacher.academicDegree.domain.AcademicDegree;
import org.hibernate.validator.constraints.UUID;

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
