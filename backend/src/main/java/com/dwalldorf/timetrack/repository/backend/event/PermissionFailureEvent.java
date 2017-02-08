package com.dwalldorf.timetrack.repository.backend.event;

import com.dwalldorf.timetrack.model.UserModel;

public class PermissionFailureEvent {

    private final UserModel user;

    private final String message;

    public static PermissionFailureEvent failureEvent(final String message) {
        return failureEvent(null, message);
    }

    public static PermissionFailureEvent failureEvent(final UserModel user, final String message) {
        return new PermissionFailureEvent(user, message);
    }

    public PermissionFailureEvent(final UserModel user, final String message) {
        this.user = user;
        this.message = message;
    }

    public UserModel getUser() {
        return user;
    }

    public String getMessage() {
        return message;
    }
}