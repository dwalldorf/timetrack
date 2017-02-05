package com.dwalldorf.timetrack.exception;

public class AdminRequiredException extends RuntimeException {

    public AdminRequiredException() {
    }

    public AdminRequiredException(String message) {
        super(message);
    }

    public AdminRequiredException(String message, Throwable cause) {
        super(message, cause);
    }

    public AdminRequiredException(Throwable cause) {
        super(cause);
    }

    public AdminRequiredException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}