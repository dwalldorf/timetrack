package com.dwalldorf.timetrack.backend.event;

public class PermissionFailureEvent {

    private final String message;

    public PermissionFailureEvent(final String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}