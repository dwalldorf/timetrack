package com.dwalldorf.timetrack.event;


import com.dwalldorf.timetrack.document.User;

public class UserRegisterEvent extends UserEvent {

    public UserRegisterEvent(final User user) {
        super(Action.REGISTER, user);
    }
}