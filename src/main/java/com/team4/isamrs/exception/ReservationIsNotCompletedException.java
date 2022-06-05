package com.team4.isamrs.exception;

public class ReservationIsNotCompletedException extends RuntimeException {
    public ReservationIsNotCompletedException() {
    }

    public ReservationIsNotCompletedException(String message) {
        super(message);
    }

    public ReservationIsNotCompletedException(String message, Throwable cause) {
        super(message, cause);
    }
}
