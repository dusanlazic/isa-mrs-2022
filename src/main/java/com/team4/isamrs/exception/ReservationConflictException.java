package com.team4.isamrs.exception;

public class ReservationConflictException extends RuntimeException {
    public ReservationConflictException() {
    }

    public ReservationConflictException(String message) {
        super(message);
    }

    public ReservationConflictException(String message, Throwable cause) {
        super(message, cause);
    }
}

