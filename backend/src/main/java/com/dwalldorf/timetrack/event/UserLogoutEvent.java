package com.dwalldorf.timetrack.event;


import com.dwalldorf.timetrack.document.User;

public class UserLogoutEvent extends UserEvent {

    public UserLogoutEvent(final User user) {
        super(Action.LOGOUT, user);
    }
}
