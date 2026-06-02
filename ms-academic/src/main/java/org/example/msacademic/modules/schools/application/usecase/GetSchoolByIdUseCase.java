package org.example.msacademic.modules.schools.application.usecase;

import org.example.msacademic.modules.schools.domain.School;
import org.example.msacademic.modules.schools.infra.persistence.SchoolRepository;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class GetSchoolByIdUseCase {

    private final SchoolRepository schoolRepository;

    public GetSchoolByIdUseCase(SchoolRepository schoolRepository) {
        this.schoolRepository = schoolRepository;
    }

    public School execute(UUID id) {
        return schoolRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Escola não encontrada com o ID informado."));
    }
}
