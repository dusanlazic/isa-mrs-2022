package com.team4.isamrs.exception;

public class EndDateBeforeStartDateException extends RuntimeException {
    public EndDateBeforeStartDateException() {
    }

    public EndDateBeforeStartDateException(String message) {
        super(message);
    }

    public EndDateBeforeStartDateException(String message, Throwable cause) {
        super(message, cause);
    }
}
