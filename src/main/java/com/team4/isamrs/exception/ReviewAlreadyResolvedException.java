package com.team4.isamrs.exception;

public class ReviewAlreadyResolvedException extends RuntimeException {
    public ReviewAlreadyResolvedException() {
    }

    public ReviewAlreadyResolvedException(String message) {
        super(message);
    }

    public ReviewAlreadyResolvedException(String message, Throwable cause) {
        super(message, cause);
    }
}
