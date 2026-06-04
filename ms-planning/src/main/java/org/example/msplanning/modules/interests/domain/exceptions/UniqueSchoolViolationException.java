package com.example.teacheravailabilityapi.modules.interests.domain.exceptions;

public class UniqueSchoolViolationException extends RuntimeException {
    public UniqueSchoolViolationException(String message) {
        super(message);
    }
}