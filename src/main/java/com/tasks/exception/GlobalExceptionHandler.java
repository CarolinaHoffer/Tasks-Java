package com.tasks.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ErrorResponse> handleBadRequest(
            BadRequestException ex
    ) {
    	return ResponseEntity
                .status(ex.getErrorCode().getStatus())
                .body(new ErrorResponse(
                    ex.getErrorCode().getCode(),
                    ex.getErrorCode().getMessage()
                ));
}}