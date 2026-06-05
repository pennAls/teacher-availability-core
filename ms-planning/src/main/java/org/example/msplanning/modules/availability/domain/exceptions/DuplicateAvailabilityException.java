package org.example.msplanning.modules.availability.domain.exceptions;

public class DuplicateAvailabilityException extends RuntimeException {
    public DuplicateAvailabilityException(String message) {
        super(message);
    }
}