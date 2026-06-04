package com.example.teacheravailabilityapi.modules.interests.domain.exceptions;

public class DuplicateInterestException extends RuntimeException {
    public DuplicateInterestException(String message) { super(message); }
}