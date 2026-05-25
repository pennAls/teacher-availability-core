package org.example.mssecurity.modules.users.application.usecases;

import org.example.mssecurity.modules.users.domain.exceptions.UserNotFoundException;
import org.example.mssecurity.modules.users.infra.persistence.UserRepository;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UpdateUserStatusUseCase {
    private final UserRepository userRepository;

    public UpdateUserStatusUseCase(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    public void execute (boolean isActive , UUID userId) {
        var user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(userId.toString()));

        user.setIsActive(isActive);
        userRepository.save(user);

    }
}
