package com.example.teacheravailabilityapi.modules.availability.domain.exceptions;

public class AvailabilityNotFoundException extends RuntimeException {
    public AvailabilityNotFoundException(String message) {
        super(message);
    }
}