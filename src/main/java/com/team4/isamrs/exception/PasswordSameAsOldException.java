package com.team4.isamrs.exception;

public class PasswordSameAsOldException extends RuntimeException {
    public PasswordSameAsOldException() {
    }

    public PasswordSameAsOldException(String message) {
        super(message);
    }

    public PasswordSameAsOldException(String message, Throwable cause) {
        super(message, cause);
    }
}
