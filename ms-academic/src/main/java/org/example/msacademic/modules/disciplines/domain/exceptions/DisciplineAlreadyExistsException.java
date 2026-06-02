package org.example.msacademic.modules.disciplines.domain.exceptions;

public class DisciplineAlreadyExistsException extends RuntimeException {
    public DisciplineAlreadyExistsException(String message) {
        super(message);
    }
}
