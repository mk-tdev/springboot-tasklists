package com.mktdev.tasklist.domain.dto;

public record ErrorResponse(
        int status,
        String message,
        String details
) {
}
