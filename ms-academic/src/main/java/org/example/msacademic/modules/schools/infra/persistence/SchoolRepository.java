package org.example.msacademic.modules.schools.infra.persistence;

import org.example.msacademic.modules.schools.domain.School;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface SchoolRepository extends JpaRepository<School, UUID> {
    boolean existsByName(String name);
    boolean existsByNameAndIesId(String name, UUID iesId);
}
