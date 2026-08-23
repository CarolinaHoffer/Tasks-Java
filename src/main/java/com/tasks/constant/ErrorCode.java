package com.tasks.constant;

import org.springframework.http.HttpStatus;

public enum ErrorCode {

	// Users errors
	
    CURRENT_PASSWORD_INCORRECT(
        "CURRENT_PASSWORD_INCORRECT",
        "The current password is incorrect.",
        HttpStatus.BAD_REQUEST
    ),

    PASSWORDS_MUST_BE_DIFFERENT(
        "PASSWORDS_MUST_BE_DIFFERENT",
        "Passwords must be differents",
        HttpStatus.BAD_REQUEST
    ),

    PASSWORD_TOO_SHORT(
        "PASSWORD_TOO_SHORT",
        "The password must contain al least 6 characters.",
        HttpStatus.BAD_REQUEST
    ),
	
    EMAIL_ALREADY_USED(
    	    "EMAIL_ALREADY_USED",
    	    "This email is already in use.",
    	    HttpStatus.CONFLICT
    ),
    
    INVALID_CREDENTIALS(
    	    "INVALID_CREDENTIALS",
    	    "Invalid email or password.",
    	    HttpStatus.UNAUTHORIZED
    	),
    
    USER_NOT_FOUND(
    	    "USER_NOT_FOUND",
    	    "User not found.",
    	    HttpStatus.NOT_FOUND
    ),
	
	// Tasks errors
    
    TASK_NOT_FOUND(
    	    "TASK_NOT_FOUND",
    	    "Task not found.",
    	    HttpStatus.NOT_FOUND
    	),
    
    TASK_ACCESS_FORBIDDEN(
    	    "TASK_ACCESS_FORBIDDEN",
    	    "You do not have permission to access this task.",
    	    HttpStatus.FORBIDDEN
    	),
	
	// Label errors
    
    LABEL_NOT_FOUND(
    	    "LABEL_NOT_FOUND",
    	    "Label not found.",
    	    HttpStatus.NOT_FOUND
    	),
    
    LABEL_ALREADY_EXISTS(
    	    "LABEL_ALREADY_EXISTS",
    	    "This label already exists.",
    	    HttpStatus.CONFLICT
    	),
    
    // General errors
    
    ACCESS_FORBIDDEN(
    	    "ACCESS_FORBIDDEN",
    	    "You do not have permission to access this resource.",
    	    HttpStatus.FORBIDDEN
    	),
    
    UNAUTHORIZED(
    	    "UNAUTHORIZED",
    	    "Authentication is required.",
    	    HttpStatus.UNAUTHORIZED
    	),
    
    INVALID_ADMIN_PASSWORD(
    	    "INVALID_ADMIN_PASSWORD",
    	    "Invalid admin password.",
    	    HttpStatus.UNAUTHORIZED
    	),

    ;
    private final String code;
    private final String message;
    private final HttpStatus status;

    ErrorCode(String code, String message, HttpStatus status) {
        this.code = code;
        this.message = message;
        this.status = status;
    }

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public HttpStatus getStatus() {
        return status;
    }
}