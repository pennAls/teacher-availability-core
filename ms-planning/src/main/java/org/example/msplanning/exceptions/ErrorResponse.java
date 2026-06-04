package org.example.msplanning.exceptions;

public record ErrorResponse(
        Integer code,
        String status
) {}