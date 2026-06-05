package org.example.msteacher.exceptions;

public record ErrorResponse(
        Integer code,
        String status
) {}
