package com.dwalldorf.timetrack.backend.exception;

public class IdentityConflictException extends RuntimeException {

    public IdentityConflictException(final String format, final Object... args) {
        super(String.format(format, args));
    }
}