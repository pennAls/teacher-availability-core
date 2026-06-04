package org.example.msplanning.modules.interests.domain.exceptions;

public class UniqueSchoolViolationException extends RuntimeException {
    public UniqueSchoolViolationException(String message) {
        super(message);
    }
}