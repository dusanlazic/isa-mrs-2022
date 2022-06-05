package com.team4.isamrs.exception;

public class ReservationReportAlreadyExistsException extends RuntimeException {
    public ReservationReportAlreadyExistsException() {
    }

    public ReservationReportAlreadyExistsException(String message) {
        super(message);
    }

    public ReservationReportAlreadyExistsException(String message, Throwable cause) {
        super(message, cause);
    }
}
