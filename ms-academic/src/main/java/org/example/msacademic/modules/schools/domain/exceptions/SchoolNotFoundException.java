package org.example.msacademic.modules.schools.domain.exceptions;

public class SchoolNotFoundException extends RuntimeException {
    public SchoolNotFoundException(String message) {
        super(message);
    }
}
