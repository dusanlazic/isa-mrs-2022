package com.team4.isamrs.exception;

public class ExceededOptionMaxCountValueException extends RuntimeException {
    public ExceededOptionMaxCountValueException() {
    }

    public ExceededOptionMaxCountValueException(String message) {
        super(message);
    }

    public ExceededOptionMaxCountValueException(String message, Throwable cause) {
        super(message, cause);
    }
}