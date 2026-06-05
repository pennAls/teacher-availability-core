package org.example.msteacher.modules.academicDegree.infra.persistence;


import org.example.msteacher.modules.academicDegree.domain.AcademicDegree;
import org.hibernate.validator.constraints.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AcademicDegreeRepository extends JpaRepository<AcademicDegree, UUID> {
}