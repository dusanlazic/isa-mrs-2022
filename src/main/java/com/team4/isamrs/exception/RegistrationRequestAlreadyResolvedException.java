package com.team4.isamrs.exception;

public class RegistrationRequestAlreadyResolvedException extends RuntimeException {
    public RegistrationRequestAlreadyResolvedException() {
    }

    public RegistrationRequestAlreadyResolvedException(String message) {
        super(message);
    }

    public RegistrationRequestAlreadyResolvedException(String message, Throwable cause) {
        super(message, cause);
    }
}
