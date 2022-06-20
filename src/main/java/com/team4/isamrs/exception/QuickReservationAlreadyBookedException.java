package com.team4.isamrs.exception;

public class QuickReservationAlreadyBookedException extends RuntimeException {
    public QuickReservationAlreadyBookedException() {
    }

    public QuickReservationAlreadyBookedException(String message) {
        super(message);
    }

    public QuickReservationAlreadyBookedException(String message, Throwable cause) {
        super(message, cause);
    }
}
