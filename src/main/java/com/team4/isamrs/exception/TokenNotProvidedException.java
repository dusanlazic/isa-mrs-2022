package com.team4.isamrs.exception;

public class TokenNotProvidedException extends RuntimeException {
    public TokenNotProvidedException() {
    }

    public TokenNotProvidedException(String message) {
        super(message);
    }

    public TokenNotProvidedException(String message, Throwable cause) {
        super(message, cause);
    }
}
