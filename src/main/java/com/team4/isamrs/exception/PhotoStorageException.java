package com.team4.isamrs.exception;

public class PhotoStorageException extends RuntimeException {
    public PhotoStorageException() {
    }

    public PhotoStorageException(String message) {
        super(message);
    }

    public PhotoStorageException(String message, Throwable cause) {
        super(message, cause);
    }
}
