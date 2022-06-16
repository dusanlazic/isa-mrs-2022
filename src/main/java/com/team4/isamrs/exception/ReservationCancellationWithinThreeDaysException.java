package com.team4.isamrs.exception;

public class ReservationCancellationWithinThreeDaysException extends RuntimeException {
    public ReservationCancellationWithinThreeDaysException() {
    }

    public ReservationCancellationWithinThreeDaysException(String message) {
        super(message);
    }

    public ReservationCancellationWithinThreeDaysException(String message, Throwable cause) {
        super(message, cause);
    }
}
