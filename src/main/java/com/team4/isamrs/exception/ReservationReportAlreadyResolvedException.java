package com.team4.isamrs.exception;

public class ReservationReportAlreadyResolvedException extends RuntimeException {
    public ReservationReportAlreadyResolvedException() {
    }

    public ReservationReportAlreadyResolvedException(String message) {
        super(message);
    }

    public ReservationReportAlreadyResolvedException(String message, Throwable cause) {
        super(message, cause);
    }
}
