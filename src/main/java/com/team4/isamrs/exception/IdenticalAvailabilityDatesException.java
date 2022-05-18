package com.team4.isamrs.exception;

public class IdenticalAvailabilityDatesException extends RuntimeException {

    public IdenticalAvailabilityDatesException() {
    }

    public IdenticalAvailabilityDatesException(String message) {
        super(message);
    }

    public IdenticalAvailabilityDatesException(String message, Throwable cause) {
        super(message, cause);
    }
}
