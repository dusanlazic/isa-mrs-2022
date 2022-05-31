package com.team4.isamrs.exception;

public class NoSuchGlobalSettingException extends RuntimeException {
    public NoSuchGlobalSettingException() {
    }

    public NoSuchGlobalSettingException(String message) {
        super(message);
    }

    public NoSuchGlobalSettingException(String message, Throwable cause) {
        super(message, cause);
    }
}
