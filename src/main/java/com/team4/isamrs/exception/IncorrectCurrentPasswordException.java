package com.team4.isamrs.exception;

public class IncorrectCurrentPasswordException extends RuntimeException {
    public IncorrectCurrentPasswordException() {
    }

    public IncorrectCurrentPasswordException(String message) {
        super(message);
    }

    public IncorrectCurrentPasswordException(String message, Throwable cause) {
        super(message, cause);
    }
}
