package com.team4.isamrs.exception;

public class ComplaintAlreadyResolvedException extends RuntimeException {
    public ComplaintAlreadyResolvedException() {
    }

    public ComplaintAlreadyResolvedException(String message) {
        super(message);
    }

    public ComplaintAlreadyResolvedException(String message, Throwable cause) {
        super(message, cause);
    }
}
