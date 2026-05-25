package org.example.mssecurity.exceptions;

public class InactiveEntityException extends RuntimeException {
    public InactiveEntityException(String message) {
        super(message);
    }
}