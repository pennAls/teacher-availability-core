package org.example.msacademic.modules.schools.domain;


import jakarta.persistence.*;
import org.example.msacademic.modules.ies.domain.Ies;

import java.util.UUID;

@Entity
@Table(
        name = "schools",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "uk_name_per_ies",
                        columnNames = {"name", "ies_id"}
                )
        }
)
public class School {


        @Id
        @GeneratedValue(strategy = GenerationType.UUID)
        private UUID id;

        @Column(nullable = false, unique = true)
        private String name;

        @Column(nullable = false)
        private String coordinator;

        @ManyToOne(optional = false)
        @JoinColumn(name = "ies_id",nullable = false)
        private Ies ies;

        @Column(name = "is_active", nullable = false)
        private Boolean isActive;

        public School() {
        }

        public School(String name, String coordinator, Ies ies) {
            this.name = name;
            this.coordinator = coordinator;
            this.ies = ies;
            this.isActive = true;
        }

        public UUID getId() { return id; }
        public String getName() { return name; }
        public void setName(String name) { this.name = name; }
        public String getCoordinator() { return coordinator; }
        public void setCoordinator(String coordinator) { this.coordinator = coordinator; }
        public Ies getIes() { return ies; }
        public void setIes(Ies ies) { this.ies = ies; }
        public Boolean getIsActive() { return isActive; }
        public void setIsActive(Boolean active) { isActive = active; }

}
