package com.example.teacheravailabilityapi.modules.availability.domain.exceptions;

public class InvalidAvailabilityException extends RuntimeException {
    public InvalidAvailabilityException(String message) {
        super(message);
    }
}