package org.example.mssecurity.modules.auth.application.usecases;

import org.example.mssecurity.modules.users.domain.User;
import org.example.mssecurity.modules.users.infra.persistence.UserRepository;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class RefreshTokenUseCase {
    private final UserRepository userRepository;

    public RefreshTokenUseCase(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    public User execute(UUID userId){
        var user = userRepository.findById(userId).orElse(null);
         if (user == null) {
            throw new UsernameNotFoundException("Usuário não encontrado");
        }
        return user;
    }
}
