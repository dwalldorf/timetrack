package com.dwalldorf.timetrack.event;


import com.dwalldorf.timetrack.document.User;

public abstract class UserEvent {

    private final Action action;

    private final User actor;

    private final Result result;

    public enum Action {
        LOGIN,
        LOGOUT,
        REGISTER,
        CREATED
    }

    public enum Result {
        SUCCESS,
        FAILURE
    }

    public UserEvent(Action action, User actor, Result result) {
        this.action = action;
        this.actor = actor;
        this.result = result;
    }

    public Action getAction() {
        return action;
    }

    public boolean hasUser() {
        return actor != null;
    }

    public User getActor() {
        return actor;
    }

    public Result getResult() {
        return result;
    }
}