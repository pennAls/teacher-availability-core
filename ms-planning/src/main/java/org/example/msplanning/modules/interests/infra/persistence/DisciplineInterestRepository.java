package com.example.teacheravailabilityapi.modules.interests.infra.persistence;

import com.example.teacheravailabilityapi.modules.interests.domain.DisciplineInterest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface DisciplineInterestRepository extends JpaRepository<DisciplineInterest, UUID> {

    @Query("SELECT di FROM DisciplineInterest di WHERE di.teacher.id = :teacherId AND di.discipline.isActive = true ORDER BY di.priority ASC")
    List<DisciplineInterest> findByTeacherIdOrderByPriorityAsc(UUID teacherId);

    boolean existsByTeacherIdAndDisciplineId(UUID teacherId, UUID disciplineId);

    @Query("SELECT di FROM DisciplineInterest di " +
            "JOIN FETCH di.teacher t " +
            "JOIN FETCH di.discipline d " +
            "ORDER BY t.fullName ASC, di.priority ASC")
    List<DisciplineInterest> findAllForAdminReport();
}