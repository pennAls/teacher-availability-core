package org.example.msplanning.exceptions;

public class InactiveEntityException extends RuntimeException {
    public InactiveEntityException(String message) {
        super(message);
    }
}