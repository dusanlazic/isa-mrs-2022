package com.team4.isamrs.exception;

public class ExceededMaxAttendeesException extends RuntimeException {
    public ExceededMaxAttendeesException() {
    }

    public ExceededMaxAttendeesException(String message) {
        super(message);
    }

    public ExceededMaxAttendeesException(String message, Throwable cause) {
        super(message, cause);
    }
}
