package org.example.mssecurity.modules.users.application.usecases;

import org.example.mssecurity.modules.users.domain.User;
import org.example.mssecurity.modules.users.domain.exceptions.EmailAlreadyExistsException;
import org.example.mssecurity.modules.users.domain.types.UserRole;
import org.example.mssecurity.modules.users.infra.dtos.CreateUserInternalRequestDto;
import org.example.mssecurity.modules.users.infra.dtos.CreateUserInternalResponseDto;
import org.example.mssecurity.modules.users.infra.persistence.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class CreateUserUseCase {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public CreateUserUseCase(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public CreateUserInternalResponseDto execute(CreateUserInternalRequestDto data) {
        if (userRepository.existsByEmail(data.email())) {
            throw new EmailAlreadyExistsException("O e-mail informado já está em uso.");
        }

        User newUser = new User(
                data.email(),
                passwordEncoder.encode(data.password()),
                UserRole.TEACHER
        );

        User saved = userRepository.save(newUser);
        return new CreateUserInternalResponseDto(saved.getId());
    }
}
