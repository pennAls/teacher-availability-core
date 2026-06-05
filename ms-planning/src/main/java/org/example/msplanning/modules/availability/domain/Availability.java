package org.example.msplanning.modules.availability.domain;

import org.example.msplanning.modules.availability.domain.types.DayOfWeek;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.LocalTime;
import java.util.UUID;

@Entity
@Table(name = "availabilities")
@Getter
@Setter
@NoArgsConstructor
public class Availability {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private DayOfWeek dayOfWeek;

    @Column(nullable = false)
    private LocalTime startTime;

    @Column(nullable = false)
    private LocalTime endTime;
    
    @Column(nullable = false)
    private UUID teacherId;

    public Availability(
            DayOfWeek dayOfWeek,
            LocalTime startTime,
            LocalTime endTime,
            UUID teacherId
    ) {
        this.dayOfWeek = dayOfWeek;
        this.startTime = startTime;
        this.endTime = endTime;
        this.teacherId = teacherId;
    }
}