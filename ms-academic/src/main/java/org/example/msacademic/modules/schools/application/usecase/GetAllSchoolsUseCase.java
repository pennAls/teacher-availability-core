package org.example.msacademic.modules.schools.application.usecase;

import org.example.msacademic.modules.ies.domain.exceptions.IesNotFoundException;
import org.example.msacademic.modules.ies.infra.persistence.IesRepository;
import org.example.msacademic.modules.schools.domain.School;
import org.example.msacademic.modules.schools.domain.exceptions.SchoolAlreadyExistsException;
import org.example.msacademic.modules.schools.infra.dtos.SchoolRequestDto;
import org.example.msacademic.modules.schools.infra.persistence.SchoolRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GetAllSchoolsUseCase {
    private final SchoolRepository schoolRepository;

    public GetAllSchoolsUseCase(SchoolRepository schoolRepository) {
        this.schoolRepository = schoolRepository;
    }

    public List<School> execute() {
        return schoolRepository.findAll();
    }
}
