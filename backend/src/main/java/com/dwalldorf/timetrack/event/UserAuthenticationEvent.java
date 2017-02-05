package com.dwalldorf.timetrack.event;


import static com.dwalldorf.timetrack.event.ActionEvent.Action.LOGIN;
import static com.dwalldorf.timetrack.event.ActionEvent.Action.LOGOUT;
import static com.dwalldorf.timetrack.event.ActionEvent.Action.REGISTER;
import static com.dwalldorf.timetrack.event.ActionEvent.Result.FAILURE;
import static com.dwalldorf.timetrack.event.ActionEvent.Result.SUCCESS;

import com.dwalldorf.timetrack.document.User;
import com.dwalldorf.timetrack.document.UserProperties;

public class UserAuthenticationEvent extends ActionEvent {

    private static final String MESSAGE_NONE = "NONE";

    private final String message;

    private UserAuthenticationEvent(final Action action, final User user, final Result result) {
        this(action, user, result, null);
    }

    private UserAuthenticationEvent(final Action action, final User user, final Result result, final String message) {
        super(action, user, result);
        this.message = message;
    }

    public String getMessage() {
        if (message == null) {
            return MESSAGE_NONE;
        }
        return message;
    }

    public static UserAuthenticationEvent loginSuccessEvent(final User user) {
        return new UserAuthenticationEvent(LOGIN, user, SUCCESS);
    }

    public static UserAuthenticationEvent loginFailedEvent(final String loginName, final String message) {
        return new UserAuthenticationEvent(LOGIN, userFromUsername(loginName), FAILURE, message);
    }

    public static UserAuthenticationEvent logoutEvent(final User user) {
        return new UserAuthenticationEvent(LOGOUT, user, SUCCESS);
    }

    public static UserAuthenticationEvent registrationSuccessEvent(final User user) {
        return new UserAuthenticationEvent(REGISTER, user, SUCCESS);
    }

    public static UserAuthenticationEvent registrationFailedEvent(final String username, final String message) {
        return new UserAuthenticationEvent(REGISTER, userFromUsername(username), FAILURE, message);
    }

    private static User userFromUsername(final String username) {
        return new User().setUserProperties(
                new UserProperties().setUsername(username)
        );
    }
}