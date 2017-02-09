package com.dwalldorf.timetrack.repository.exception;

public class UserNotFoundException extends RuntimeException {

    public UserNotFoundException(String username) {
        super(username);
    }
}