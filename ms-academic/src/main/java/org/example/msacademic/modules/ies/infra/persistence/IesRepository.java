package org.example.msacademic.modules.ies.infra.persistence;

import org.example.msacademic.modules.ies.domain.Ies;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface IesRepository extends JpaRepository<Ies, UUID> {
    Optional<Ies> findByName(String name);
}
