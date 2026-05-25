package org.example.mssecurity.exceptions;

public record ErrorResponse(
        Integer code,
        String status
) {}