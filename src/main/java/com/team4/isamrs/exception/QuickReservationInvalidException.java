package com.team4.isamrs.exception;

public class QuickReservationInvalidException extends RuntimeException {
    public QuickReservationInvalidException() {
    }

    public QuickReservationInvalidException(String message) {
        super(message);
    }

    public QuickReservationInvalidException(String message, Throwable cause) {
        super(message, cause);
    }
}
