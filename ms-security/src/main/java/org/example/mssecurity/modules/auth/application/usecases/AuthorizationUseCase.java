package org.example.mssecurity.modules.auth.application.usecases;

import org.example.mssecurity.modules.users.infra.persistence.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class AuthorizationUseCase implements UserDetailsService {
    private final UserRepository userRepository;

    public AuthorizationUseCase(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String userEmail) throws UsernameNotFoundException {
        var user = userRepository.getUserByEmail(userEmail);

        if (user == null) {
            throw new UsernameNotFoundException("Usuário não encontrado");
        }

        return user;
    }
}
