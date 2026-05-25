package org.example.mssecurity.modules.users.domain.exceptions;

public class UserNotFoundException extends RuntimeException {

    public UserNotFoundException(String id) {
        super("Usuário com ID " + id + " não foi encontrado.");
    }
}
