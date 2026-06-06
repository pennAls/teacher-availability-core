package org.example.msacademic.modules.disciplines.application.usecases;

import org.example.msacademic.modules.disciplines.domain.Discipline;
import org.example.msacademic.modules.disciplines.domain.exceptions.DisciplineAlreadyExistsException;
import org.example.msacademic.modules.disciplines.infra.dtos.DisciplineRequestDto;
import org.example.msacademic.modules.disciplines.infra.persistence.DisciplineRepository;
import org.example.msacademic.modules.schools.domain.exceptions.SchoolNotFoundException;
import org.example.msacademic.modules.schools.infra.persistence.SchoolRepository;
import org.example.msacademic.exceptions.InactiveEntityException;
import org.springframework.stereotype.Service;

@Service
public class CreateDisciplineUseCase {

    private final DisciplineRepository disciplineRepository;
    private final SchoolRepository schoolRepository;

    public CreateDisciplineUseCase(DisciplineRepository disciplineRepository, SchoolRepository schoolRepository) {
        this.disciplineRepository = disciplineRepository;
        this.schoolRepository = schoolRepository;
    }

    public Discipline execute(DisciplineRequestDto data) {
        var school = schoolRepository.findById(data.schoolId())
                .orElseThrow(() -> new SchoolNotFoundException("Escola não encontrada com o ID: " + data.schoolId()));

        if (disciplineRepository.existsByAcronymAndSchoolId(data.acronym(), data.schoolId())) {
            throw new DisciplineAlreadyExistsException(
                    "A sigla '" + data.acronym() + "' já está cadastrada para esta escola."
            );
        }
        if (!school.getIsActive()) {
            throw new InactiveEntityException("Não é possível cadastrar uma disciplina em uma Escola inativa.");
        }
        var newDiscipline = new Discipline(data.acronym(), data.description(), data.workload(), school);

        return disciplineRepository.save(newDiscipline);
    }

}
