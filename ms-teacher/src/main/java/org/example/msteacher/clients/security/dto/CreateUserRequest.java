package org.example.msteacher.clients.security.dto;

public record CreateUserRequest(
        String email,
        String password
) {}
