package com.team4.isamrs.exception;

public class ReservationsInUnavailabilityPeriodException extends RuntimeException {
    public ReservationsInUnavailabilityPeriodException() {
    }

    public ReservationsInUnavailabilityPeriodException(String message) {
        super(message);
    }

    public ReservationsInUnavailabilityPeriodException(String message, Throwable cause) {
        super(message, cause);
    }
}
