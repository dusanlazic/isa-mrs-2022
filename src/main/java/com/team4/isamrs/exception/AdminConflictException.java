package com.team4.isamrs.exception;

public class AdminConflictException  extends RuntimeException {
    public AdminConflictException() {
    }

    public AdminConflictException(String message) {
        super(message);
    }

    public AdminConflictException(String message, Throwable cause) {
        super(message, cause);
    }
}
