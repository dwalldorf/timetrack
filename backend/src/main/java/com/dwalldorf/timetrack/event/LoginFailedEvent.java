package com.dwalldorf.timetrack.event;

public class LoginFailedEvent extends UserEvent {

    private final String loginName;

    public LoginFailedEvent(final String loginName) {
        super(Action.LOGIN, null);
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