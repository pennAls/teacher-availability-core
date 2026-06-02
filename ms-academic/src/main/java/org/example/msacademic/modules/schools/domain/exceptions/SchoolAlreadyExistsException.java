package org.example.msacademic.modules.schools.domain.exceptions;

public class SchoolAlreadyExistsException extends RuntimeException {
    public SchoolAlreadyExistsException(String message) {
        super(message);
    }
}
