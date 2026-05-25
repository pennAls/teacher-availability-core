package org.example.mssecurity.utils;

import org.example.mssecurity.modules.users.domain.User;
import org.example.mssecurity.modules.users.domain.types.UserRole;
import org.example.mssecurity.modules.users.infra.persistence.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class AdminSeeder implements CommandLineRunner {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public AdminSeeder(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) throws Exception {
        String adminEmail = "admin@ucsal.edu.br";

        if (!userRepository.existsByEmail(adminEmail)) {
            User admin = new User(
                    adminEmail,
                    passwordEncoder.encode("admin123"),
                    UserRole.ADMIN
            );

            userRepository.save(admin);
        }
    }
}