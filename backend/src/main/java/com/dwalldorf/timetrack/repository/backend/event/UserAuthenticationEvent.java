package com.dwalldorf.timetrack.repository.backend.event;


import static com.dwalldorf.timetrack.repository.backend.event.ActionEvent.Action.LOGIN;
import static com.dwalldorf.timetrack.repository.backend.event.ActionEvent.Action.LOGOUT;
import static com.dwalldorf.timetrack.repository.backend.event.ActionEvent.Action.REGISTER;
import static com.dwalldorf.timetrack.repository.backend.event.ActionEvent.Result.FAILURE;
import static com.dwalldorf.timetrack.repository.backend.event.ActionEvent.Result.SUCCESS;

import com.dwalldorf.timetrack.model.UserModel;

public class UserAuthenticationEvent extends ActionEvent {

    private static final String MESSAGE_NONE = "NONE";

    private final String message;

    UserAuthenticationEvent(final Action action, final UserModel user, final Result result) {
        this(action, user, result, null);
    }

    UserAuthenticationEvent(final Action action, final UserModel user, final Result result, final String message) {
        super(action, user, result);
        this.message = message;
    }

    public static UserAuthenticationEvent loginSuccessEvent(final UserModel user) {
        return new UserAuthenticationEvent(LOGIN, user, SUCCESS);
    }

    public static UserAuthenticationEvent loginFailedEvent(final String loginName, final String message) {
        return new UserAuthenticationEvent(LOGIN, userFromUsername(loginName), FAILURE, message);
    }

    public static UserAuthenticationEvent logoutEvent(final UserModel user) {
        return new UserAuthenticationEvent(LOGOUT, user, SUCCESS);
    }

    public static UserAuthenticationEvent registrationSuccessEvent(final UserModel user) {
        return new UserAuthenticationEvent(REGISTER, user, SUCCESS);
    }

    public static UserAuthenticationEvent registrationFailedEvent(final String username, final String message) {
        return new UserAuthenticationEvent(REGISTER, userFromUsername(username), FAILURE, message);
    }

    private static UserModel userFromUsername(final String username) {
        return new UserModel()
                .setUsername(username);
    }

    public String getMessage() {
        if (message == null) {
            return MESSAGE_NONE;
        }

        return message;
    }
}