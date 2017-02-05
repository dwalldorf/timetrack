package com.dwalldorf.timetrack.event;


import com.dwalldorf.timetrack.document.User;

public class UserLoginEvent extends UserEvent {

    public UserLoginEvent(final User user) {
        super(Action.LOGIN, user);
    }
}