package com.team4.isamrs.exception;

public class ActionNotAllowedException extends RuntimeException {
    public ActionNotAllowedException() {
    }

    public ActionNotAllowedException(String message) {
        super(message);
    }

    public ActionNotAllowedException(String message, Throwable cause) {
        super(message, cause);
    }
}
