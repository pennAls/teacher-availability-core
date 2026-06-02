package org.example.msacademic.modules.disciplines.domain;

import jakarta.persistence.*;
import org.example.msacademic.modules.schools.domain.School;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(
        name = "disciplines",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "uk_acronym_per_school",
                        columnNames = {"acronym", "school_id"}
                )
        }
)
public class Discipline {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false, length = 20)
    private String acronym;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private Double workload;

    @ManyToOne(optional = false)
    @JoinColumn(name = "school_id", nullable = false)
    private School school;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "is_active", nullable = false)
    private Boolean isActive;

    public Discipline() {
    }

    public Discipline(String acronym, String description, Double workload, School school) {
        this.acronym = acronym;
        this.description = description;
        this.workload = workload;
        this.school = school;
        this.createdAt = LocalDateTime.now();
        this.isActive = true;
    }

    public UUID getId() { return id; }
    public String getAcronym() { return acronym; }
    public void setAcronym(String acronym) { this.acronym = acronym; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public Double getWorkload() { return workload; }
    public void setWorkload(Double workload) { this.workload = workload; }
    public School getSchool() { return school; }
    public void setSchool(School school) { this.school = school; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public Boolean getIsActive() { return isActive; }
    public void setIsActive(Boolean active) { isActive = active; }
}
