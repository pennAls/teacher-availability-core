package org.example.msplanning.modules.interests.domain.exceptions;

public class DisciplineNotFoundException extends RuntimeException {
    public DisciplineNotFoundException(String message) {
        super(message);
    }
}