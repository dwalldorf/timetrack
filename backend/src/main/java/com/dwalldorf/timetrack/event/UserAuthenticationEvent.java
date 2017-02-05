package com.dwalldorf.timetrack.event;


import static com.dwalldorf.timetrack.event.UserEvent.Action.LOGIN;
import static com.dwalldorf.timetrack.event.UserEvent.Action.LOGOUT;
import static com.dwalldorf.timetrack.event.UserEvent.Action.REGISTER;
import static com.dwalldorf.timetrack.event.UserEvent.Result.SUCCESS;

import com.dwalldorf.timetrack.document.User;

public class UserAuthenticationEvent extends UserEvent {

    UserAuthenticationEvent(final Action action, final User user, final Result result) {
        super(action, user, result);
    }

    public static UserAuthenticationEvent loginSuccessEvent(final User user) {
        return new UserAuthenticationEvent(LOGIN, user, SUCCESS);
    }

    public static UserAuthenticationEvent loginFailureEvent(final String loginName) {
        return new LoginFailedEvent(loginName);
    }

    public static UserAuthenticationEvent logoutEvent(final User user) {
        return new UserAuthenticationEvent(LOGOUT, user, SUCCESS);
    }

    public static UserAuthenticationEvent registerEvent(final User user) {
        return new UserAuthenticationEvent(REGISTER, user, SUCCESS);
    }
}