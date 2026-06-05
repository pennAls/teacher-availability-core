package teacher.infra.persistence;

import org.apache.el.stream.Optional;
import org.hibernate.validator.constraints.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import teacher.domain.Teacher;

@Repository
public interface TeacherRepository extends JpaRepository<Teacher, UUID> {

    boolean existsByRegistration(String registration);

    @Query("SELECT t FROM Teacher t WHERE t.user.id = :userId AND t.user.isActive = true")
    Optional<Teacher> findByUserId(UUID userId);
}
