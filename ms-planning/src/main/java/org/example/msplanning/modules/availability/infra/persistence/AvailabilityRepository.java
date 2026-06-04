package com.example.teacheravailabilityapi.modules.availability.infra.persistence;

import com.example.teacheravailabilityapi.modules.availability.domain.Availability;
import com.example.teacheravailabilityapi.modules.availability.domain.types.DayOfWeek;
import com.example.teacheravailabilityapi.modules.interests.domain.DisciplineInterest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalTime;
import java.util.List;
import java.util.UUID;

public interface AvailabilityRepository extends JpaRepository<Availability, UUID> {
    List<Availability> findByTeacherId(UUID teacherId);
    boolean existsByTeacherIdAndDayOfWeekAndStartTime(UUID teacherId, DayOfWeek dayOfWeek, LocalTime startTime);
    @Query("SELECT a FROM Availability a JOIN FETCH a.teacher t ORDER BY t.fullName ASC")
    List<Availability> findAllForAdminReport();
}