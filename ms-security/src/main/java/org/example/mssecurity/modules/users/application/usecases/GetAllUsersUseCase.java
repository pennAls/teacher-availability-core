package org.example.mssecurity.modules.users.application.usecases;

import org.example.mssecurity.modules.users.domain.User;
import org.example.mssecurity.modules.users.infra.dtos.UserResponseDto;
import org.example.mssecurity.modules.users.infra.persistence.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GetAllUsersUseCase {
    private final UserRepository userRepository;

    public GetAllUsersUseCase(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<UserResponseDto> execute() {
        List<User> users = userRepository.findAll();
        return users.stream().map(UserResponseDto::fromEntity).toList();
    }
}
