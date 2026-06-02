package org.example.msacademic.modules.disciplines.infra.persistence;

import org.example.msacademic.modules.disciplines.domain.Discipline;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface DisciplineRepository extends JpaRepository<Discipline, UUID> {
    List<Discipline> findBySchoolId(UUID schoolId);
    boolean existsByAcronymAndSchoolId(String acronym, UUID schoolId);

}
