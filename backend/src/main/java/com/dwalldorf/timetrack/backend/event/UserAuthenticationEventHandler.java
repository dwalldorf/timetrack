package com.dwalldorf.timetrack.backend.event;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.Marker;
import org.slf4j.MarkerFactory;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
public class UserAuthenticationEventHandler {

    private static final Logger logger = LoggerFactory.getLogger(UserAuthenticationEventHandler.class);
    private static final Marker marker = MarkerFactory.getMarker("user_auth");

    @Async
    @EventListener(UserAuthenticationEvent.class)
    public void onUserAuthenticationEvent(final UserAuthenticationEvent authenticationEvent) {
        switch (authenticationEvent.getAction()) {
            case REGISTER:
                handleRegistrationEvent(authenticationEvent);
                break;
            case LOGIN:
                handleLoginEvent(authenticationEvent);
                break;
            case LOGOUT:
                handleLogoutEvent(authenticationEvent);
                break;
        }
    }

    private void handleRegistrationEvent(final UserAuthenticationEvent authenticationEvent) {
        switch (authenticationEvent.getResult()) {
            case SUCCESS:
                logAuthenticationInfo("Successful registration: '{}'", authenticationEvent.getUsername());
                break;
            case FAILURE:
                logAuthenticationInfo("Failure during registration with username: {}, message: {}",
                        authenticationEvent.getUsername(),
                        authenticationEvent.getMessage()
                );
        }
    }

    private void handleLoginEvent(final UserAuthenticationEvent authenticationEvent) {
        switch (authenticationEvent.getResult()) {
            case SUCCESS:
                logAuthenticationInfo("Successful login: '{}'", authenticationEvent.getUsername());
                break;
            case FAILURE:
                logAuthenticationInfo("Failure during login with name: '{}', message: {}",
                        authenticationEvent.getUsername(),
                        authenticationEvent.getMessage());
                break;
        }
    }

    private void handleLogoutEvent(final UserAuthenticationEvent authenticationEvent) {
        if (authenticationEvent.getResult() == ActionEvent.Result.SUCCESS) {
            logAuthenticationInfo("Logout: '{}'", authenticationEvent.getUsername());
        }
    }

    private void logAuthenticationInfo(final String format, final Object... arguments) {
        logger.info(marker, format, arguments);
    }
}