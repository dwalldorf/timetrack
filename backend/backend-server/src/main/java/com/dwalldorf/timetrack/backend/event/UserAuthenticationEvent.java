package com.dwalldorf.timetrack.backend.event;


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
        return new UserAuthenticationEvent(Action.LOGIN, user, Result.SUCCESS);
    }

    public static UserAuthenticationEvent loginFailedEvent(final String loginName, final String message) {
        return new UserAuthenticationEvent(Action.LOGIN, userFromUsername(loginName), Result.FAILURE, message);
    }

    public static UserAuthenticationEvent logoutEvent(final UserModel user) {
        return new UserAuthenticationEvent(Action.LOGOUT, user, Result.SUCCESS);
    }

    public static UserAuthenticationEvent registrationSuccessEvent(final UserModel user) {
        return new UserAuthenticationEvent(Action.REGISTER, user, Result.SUCCESS);
    }

    public static UserAuthenticationEvent registrationFailedEvent(final String username, final String message) {
        return new UserAuthenticationEvent(Action.REGISTER, userFromUsername(username), Result.FAILURE, message);
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