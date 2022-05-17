package com.team4.isamrs.exception.error;

public class RemovalRequestAlreadyCreatedException extends RuntimeException {
    public RemovalRequestAlreadyCreatedException() {
    }

    public RemovalRequestAlreadyCreatedException(String message) {
        super(message);
    }

    public RemovalRequestAlreadyCreatedException(String message, Throwable cause) {
        super(message, cause);
    }
}
