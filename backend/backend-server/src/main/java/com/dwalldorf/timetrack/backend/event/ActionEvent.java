package com.dwalldorf.timetrack.backend.event;

import com.dwalldorf.timetrack.model.UserModel;

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

    private final UserModel actor;

    private final Result result;

    ActionEvent(Action action, UserModel actor, Result result) {
        this.action = action;
        this.actor = actor;
        this.result = result;
    }

    public Action getAction() {
        return action;
    }

    public UserModel getActor() {
        return actor;
    }

    public Result getResult() {
        return result;
    }

    public String getUsername() {
        if (actor == null || actor.getUsername() == null) {
            return "";
        }
        return actor.getUsername();
    }
}