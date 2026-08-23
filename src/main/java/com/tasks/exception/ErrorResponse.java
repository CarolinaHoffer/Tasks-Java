package com.tasks.exception;

public record ErrorResponse(
    String code,
    String message
) {}