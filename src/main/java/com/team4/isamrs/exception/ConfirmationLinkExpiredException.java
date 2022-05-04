package com.team4.isamrs.exception;

public class ConfirmationLinkExpiredException extends RuntimeException {
    public ConfirmationLinkExpiredException() {
    }

    public ConfirmationLinkExpiredException(String message) {
        super(message);
    }

    public ConfirmationLinkExpiredException(String message, Throwable cause) {
        super(message, cause);
    }
}
