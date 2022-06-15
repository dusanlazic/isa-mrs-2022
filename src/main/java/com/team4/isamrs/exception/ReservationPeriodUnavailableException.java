package com.team4.isamrs.exception;

public class ReservationPeriodUnavailableException extends RuntimeException {
    public ReservationPeriodUnavailableException() {
    }

    public ReservationPeriodUnavailableException(String message) {
        super(message);
    }

    public ReservationPeriodUnavailableException(String message, Throwable cause) {
        super(message, cause);
    }
}
