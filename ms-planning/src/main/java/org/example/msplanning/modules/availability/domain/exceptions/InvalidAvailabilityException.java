package org.example.msplanning.modules.availability.domain.exceptions;

public class InvalidAvailabilityException extends RuntimeException {
    public InvalidAvailabilityException(String message) {
        super(message);
    }
}