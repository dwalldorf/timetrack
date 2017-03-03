package com.dwalldorf.timetrack.backend.event;

public class IdentityConflictEvent {

    private final String message;

    public IdentityConflictEvent(String message) {this.message = message;}

    public String getMessage() {
        return message;
    }
}