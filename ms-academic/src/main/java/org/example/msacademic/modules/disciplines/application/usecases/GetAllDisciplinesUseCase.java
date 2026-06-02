package org.example.msacademic.modules.disciplines.application.usecases;

import org.example.msacademic.modules.disciplines.domain.Discipline;
import org.example.msacademic.modules.disciplines.infra.persistence.DisciplineRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GetAllDisciplinesUseCase {
    private final DisciplineRepository disciplineRepository;

    public GetAllDisciplinesUseCase(DisciplineRepository disciplineRepository) {
        this.disciplineRepository = disciplineRepository;
    }

    public List<Discipline> execute() {
        return disciplineRepository.findAll();
    }
}
