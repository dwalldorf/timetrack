package com.dwalldorf.timetrack.backend.event;

public class PermissionFailureEvent {

    private final String message;

    public static PermissionFailureEvent failureEvent(final String message) {
        return new PermissionFailureEvent(message);
    }

    private PermissionFailureEvent(final String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}