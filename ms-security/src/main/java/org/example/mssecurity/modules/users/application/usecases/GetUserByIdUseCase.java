package org.example.mssecurity.modules.users.application.usecases;


import org.example.mssecurity.modules.users.domain.exceptions.UserNotFoundException;
import org.example.mssecurity.modules.users.infra.dtos.UserResponseDto;
import org.example.mssecurity.modules.users.infra.persistence.UserRepository;
import org.example.mssecurity.utils.UseAuth;
import org.springframework.stereotype.Service;

@Service
public class GetUserByIdUseCase {

    private final UserRepository userRepository;

    public GetUserByIdUseCase(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserResponseDto execute() {
        var user = userRepository.findById(UseAuth.GetAuthenticatedUser())
                .orElseThrow(() -> new UserNotFoundException(UseAuth.GetAuthenticatedUser().toString()));

        return new UserResponseDto(
                user.getId(),
                user.getEmail(),
                user.getRole(),
                user.getIsActive()
        );
    }
}