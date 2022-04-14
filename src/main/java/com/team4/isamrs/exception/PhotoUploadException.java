package com.team4.isamrs.exception;

public class PhotoUploadException extends RuntimeException {
    public PhotoUploadException() {
    }

    public PhotoUploadException(String message) {
        super(message);
    }

    public PhotoUploadException(String message, Throwable cause) {
        super(message, cause);
    }
}
