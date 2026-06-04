package com.example.teacheravailabilityapi.modules.interests.domain.exceptions;

public class DisciplineInterestNotFoundException extends RuntimeException {
    public DisciplineInterestNotFoundException(String message) {
        super(message);
    }
}