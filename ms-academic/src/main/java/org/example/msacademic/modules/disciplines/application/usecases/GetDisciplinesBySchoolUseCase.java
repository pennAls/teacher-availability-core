package org.example.msacademic.modules.disciplines.application.usecases;

import org.example.msacademic.modules.disciplines.domain.Discipline;
import org.example.msacademic.modules.disciplines.infra.persistence.DisciplineRepository;
import org.example.msacademic.modules.schools.domain.exceptions.SchoolNotFoundException;
import org.example.msacademic.modules.schools.infra.persistence.SchoolRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class GetDisciplinesBySchoolUseCase {

    private final DisciplineRepository disciplineRepository;
    private final SchoolRepository schoolRepository;

    public GetDisciplinesBySchoolUseCase(DisciplineRepository disciplineRepository, SchoolRepository schoolRepository) {
        this.disciplineRepository = disciplineRepository;
        this.schoolRepository = schoolRepository;
    }

    public List<Discipline> execute(UUID schoolId) {
        if (!schoolRepository.existsById(schoolId)) {
            throw new SchoolNotFoundException("Escola não encontrada com o ID: " + schoolId);
        }

        return disciplineRepository.findBySchoolId(schoolId);
    }
}
