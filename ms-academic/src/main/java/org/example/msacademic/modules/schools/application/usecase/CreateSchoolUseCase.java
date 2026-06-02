package org.example.msacademic.modules.schools.application.usecase;

import org.example.msacademic.modules.ies.domain.exceptions.IesNotFoundException;
import org.example.msacademic.modules.ies.infra.persistence.IesRepository;
import org.example.msacademic.modules.schools.domain.School;
import org.example.msacademic.modules.schools.domain.exceptions.SchoolAlreadyExistsException;
import org.example.msacademic.modules.schools.infra.dtos.SchoolRequestDto;
import org.example.msacademic.modules.schools.infra.persistence.SchoolRepository;
import org.springframework.stereotype.Service;

@Service
public class CreateSchoolUseCase {
    private final SchoolRepository schoolRepository;
    private final IesRepository iesRepository;

    public CreateSchoolUseCase(SchoolRepository schoolRepository, IesRepository iesRepository) {
        this.schoolRepository = schoolRepository;
        this.iesRepository = iesRepository;
    }

    public School execute(SchoolRequestDto data) {

        var ies = iesRepository.findById(data.iesId())
                .orElseThrow(() -> new IesNotFoundException("IES não encontrada com o ID informado."));

        School newSchool = new School(data.name(), data.coordinator(), ies);

        if (schoolRepository.existsByNameAndIesId(data.name(), data.iesId())) {
            throw new SchoolAlreadyExistsException(
                    "A escola '" + data.name() + "' já está cadastrada para esta Instituição."
            );
        }

        return schoolRepository.save(newSchool);
    }
}
