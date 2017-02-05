package com.dwalldorf.timetrack.event;

import com.dwalldorf.timetrack.document.User;

public abstract class ActionEvent {

    public enum Action {
        LOGIN,
        LOGOUT,
        REGISTER
    }

    public enum Result {
        SUCCESS,
        FAILURE
    }

    private final Action action;

    private final User actor;

    private final Result result;

    protected ActionEvent(Action action, User actor, Result result) {
        this.action = action;
        this.actor = actor;
        this.result = result;
    }

    public Action getAction() {
        return action;
    }

    public User getActor() {
        return actor;
    }

    public Result getResult() {
        return result;
    }

    public String getUsername() {
        if (actor == null || actor.getUserProperties() == null || actor.getUserProperties().getUsername() == null) {
            return "";
        }
        return actor.getUserProperties().getUsername();
    }
}