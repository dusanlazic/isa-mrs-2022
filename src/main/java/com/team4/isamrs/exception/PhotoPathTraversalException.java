package com.team4.isamrs.exception;

public class PhotoPathTraversalException extends RuntimeException {
    public PhotoPathTraversalException() {
    }

    public PhotoPathTraversalException(String message) {
        super(message);
    }

    public PhotoPathTraversalException(String message, Throwable cause) {
        super(message, cause);
    }
}
