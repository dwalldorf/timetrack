package com.dwalldorf.timetrack.repository.exception;

public class BadPasswordException extends RuntimeException {

    public BadPasswordException(String username) {
        super(username);
    }
}