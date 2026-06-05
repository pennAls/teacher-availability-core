package org.example.msplanning.modules.availability.infra.persistence;

import org.example.msplanning.modules.availability.domain.Availability;
import org.example.msplanning.modules.availability.domain.types.DayOfWeek;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalTime;
import java.util.List;
import java.util.UUID;

public interface AvailabilityRepository extends JpaRepository<Availability, UUID> {

    List<Availability> findByTeacherId(UUID teacherId);

    boolean existsByTeacherIdAndDayOfWeekAndStartTime(
            UUID teacherId,
            DayOfWeek dayOfWeek,
            LocalTime startTime
    );
}

//FALTA ADICIONAR findAllForAdminReport exigindo outra solução feign para buscar dados do professor.