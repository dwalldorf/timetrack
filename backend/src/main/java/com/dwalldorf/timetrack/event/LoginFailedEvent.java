package com.dwalldorf.timetrack.event;

public class LoginFailedEvent extends UserAuthenticationEvent {

    private final String loginName;

    public LoginFailedEvent(final String loginName) {
        super(Action.LOGIN, null, Result.FAILURE);
        this.loginName = loginName;
    }

    public String getLoginName() {
        return loginName;
    }

    @Override
    public boolean hasUser() {
        return false;
    }
}