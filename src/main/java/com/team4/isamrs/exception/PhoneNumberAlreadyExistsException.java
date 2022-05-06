package com.team4.isamrs.exception;

public class PhoneNumberAlreadyExistsException  extends RuntimeException {
    public PhoneNumberAlreadyExistsException() {
    }

    public PhoneNumberAlreadyExistsException(String message) {
        super(message);
    }

    public PhoneNumberAlreadyExistsException(String message, Throwable cause) {
        super(message, cause);
    }
}
