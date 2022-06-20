package com.team4.isamrs.exception;

public class DiscountCostsMoreThanOriginalException extends RuntimeException {
    public DiscountCostsMoreThanOriginalException() {
    }

    public DiscountCostsMoreThanOriginalException(String message) {
        super(message);
    }

    public DiscountCostsMoreThanOriginalException(String message, Throwable cause) {
        super(message, cause);
    }
}
