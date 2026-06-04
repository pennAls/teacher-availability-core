package com.example.teacheravailabilityapi.modules.interests.domain;

import com.example.teacheravailabilityapi.modules.disciplines.domain.Discipline;
import com.example.teacheravailabilityapi.modules.teacher.domain.Teacher;
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

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "teacher_id", nullable = false)
    private Teacher teacher;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "discipline_id", nullable = false)
    private Discipline discipline;

    @Column(nullable = false)
    private Integer priority;

    public DisciplineInterest(Teacher teacher, Discipline discipline, Integer priority) {
        this.teacher = teacher;
        this.discipline = discipline;
        this.priority = priority;
    }
}