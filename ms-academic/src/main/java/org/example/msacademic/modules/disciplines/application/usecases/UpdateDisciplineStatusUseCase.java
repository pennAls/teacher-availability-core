package org.example.msacademic.modules.disciplines.application.usecases;

import org.example.msacademic.modules.disciplines.domain.exceptions.DisciplineNotFoundException;
import org.example.msacademic.modules.disciplines.infra.persistence.DisciplineRepository;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UpdateDisciplineStatusUseCase {
    private final DisciplineRepository disciplineRepository;

    public UpdateDisciplineStatusUseCase(DisciplineRepository disciplineRepository) {
        this.disciplineRepository = disciplineRepository;
    }

    public void execute(UUID id, Boolean isActive) {
        var discipline = disciplineRepository.findById(id)
                .orElseThrow(() -> new DisciplineNotFoundException("Disciplina não encontrada com o ID: " + id));

        discipline.setIsActive(isActive);
        disciplineRepository.save(discipline);
    }
}
