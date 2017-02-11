package com.dwalldorf.timetrack.backend.exception;

public class AdminRequiredException extends RuntimeException {

    public AdminRequiredException(String message) {
        super(message);
    }
}