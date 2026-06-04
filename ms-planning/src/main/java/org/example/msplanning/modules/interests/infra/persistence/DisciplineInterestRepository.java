package org.example.msplanning.modules.interests.infra.persistence;

import org.example.msplanning.modules.interests.domain.DisciplineInterest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface DisciplineInterestRepository extends JpaRepository<DisciplineInterest, UUID> {

    List<DisciplineInterest> findByTeacherIdOrderByPriorityAsc(UUID teacherId);
    boolean existsByTeacherIdAndDisciplineId(UUID teacherId, UUID disciplineId);

}