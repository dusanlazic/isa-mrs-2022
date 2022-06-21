package com.team4.isamrs.exception;

public class TooManyPenaltiesException extends RuntimeException {
    public TooManyPenaltiesException() {
    }

    public TooManyPenaltiesException(String message) {
        super(message);
    }

    public TooManyPenaltiesException(String message, Throwable cause) {
        super(message, cause);
    }
}

