package org.example.msacademic.modules.disciplines.domain.exceptions;

public class DisciplineNotFoundException extends RuntimeException {
    public DisciplineNotFoundException(String message) {
        super(message);
    }
}
