package com.dwalldorf.timetrack.backend.event;

public class PermissionFailureEvent {

    private final String message;

    public static PermissionFailureEvent failureEvent(final String message) {
        return new PermissionFailureEvent(message);
    }

    public static PermissionFailureEvent failureEvent(final String format, final Object... args) {
        return new PermissionFailureEvent(String.format(format, args));
    }

    private PermissionFailureEvent(final String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}