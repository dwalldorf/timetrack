package com.dwalldorf.timetrack.backend.exception;

public class LoginRequiredException extends RuntimeException {

    public LoginRequiredException(String message) {
        super(message);
    }
}