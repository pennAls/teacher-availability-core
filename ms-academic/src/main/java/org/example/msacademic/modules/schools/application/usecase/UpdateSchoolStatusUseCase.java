package org.example.msacademic.modules.schools.application.usecase;

import org.example.msacademic.modules.schools.infra.persistence.SchoolRepository;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UpdateSchoolStatusUseCase {
    private final SchoolRepository schoolRepository;

    public UpdateSchoolStatusUseCase(SchoolRepository schoolRepository) {
        this.schoolRepository = schoolRepository;
    }

    public void execute(UUID id, Boolean isActive) {
        var school = schoolRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Escola não encontrada"));

        school.setIsActive(isActive);
        schoolRepository.save(school);
    }
}
