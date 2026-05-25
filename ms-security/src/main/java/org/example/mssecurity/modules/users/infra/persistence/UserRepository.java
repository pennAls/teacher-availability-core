package org.example.mssecurity.modules.users.infra.persistence;

import org.example.mssecurity.modules.users.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {
    boolean existsByEmail(String email);
    User getUserByEmail(String email);
}
