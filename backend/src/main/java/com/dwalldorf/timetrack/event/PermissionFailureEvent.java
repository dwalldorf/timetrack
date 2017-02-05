package com.dwalldorf.timetrack.event;

import com.dwalldorf.timetrack.document.User;

public class PermissionFailureEvent {

    private final User user;


    private final String message;

    public static PermissionFailureEvent failureEvent(final String message) {
        return failureEvent(null, message);
    }

    public static PermissionFailureEvent failureEvent(final User user, final String message) {
        return new PermissionFailureEvent(user, message);
    }

    public PermissionFailureEvent(final User user, final String message) {
        this.user = user;
        this.message = message;
    }

    public User getUser() {
        return user;
    }

    public String getMessage() {
        return message;
    }
}