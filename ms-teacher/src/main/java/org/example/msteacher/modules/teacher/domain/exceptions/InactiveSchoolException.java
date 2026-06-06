package org.example.msteacher.modules.teacher.domain.exceptions;

public class InactiveSchoolException extends RuntimeException {
    public InactiveSchoolException(String message) {
        super(message);
    }
}
