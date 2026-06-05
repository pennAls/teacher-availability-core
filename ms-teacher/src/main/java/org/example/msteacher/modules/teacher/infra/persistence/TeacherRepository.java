package org.example.msteacher.modules.teacher.infra.persistence;
import org.example.msteacher.modules.teacher.domain.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface TeacherRepository extends JpaRepository<Teacher, UUID> {

    boolean existsByRegistration(String registration);

    Optional<Teacher> findByUserId(UUID userId);
}