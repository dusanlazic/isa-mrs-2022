package com.team4.isamrs.exception;

public class RegistrationRequestResponseConflictException extends RuntimeException {
    public RegistrationRequestResponseConflictException() {
    }

    public RegistrationRequestResponseConflictException(String message) {
        super(message);
    }

    public RegistrationRequestResponseConflictException(String message, Throwable cause) {
        super(message, cause);
    }
}
