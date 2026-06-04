package org.example.msplanning.modules.interests.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Entity
@Table(name = "discipline_interests")
@Getter
@Setter
@NoArgsConstructor
public class DisciplineInterest {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false)
    private UUID teacherId;

    @Column(nullable = false)
    private UUID disciplineId;

    @Column(nullable = false)
    private Integer priority;

    public DisciplineInterest(UUID teacherId, UUID disciplineId, Integer priority) {
        this.teacherId = teacherId;
        this.disciplineId = disciplineId;
        this.priority = priority;
    }
}