package com.dwalldorf.timetrack.backend.event;

import com.dwalldorf.timetrack.model.UserModel;
import com.dwalldorf.timetrack.repository.dao.UserDao;
import javax.inject.Inject;
import org.joda.time.DateTime;
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

    private final UserDao userDao;

    @Inject
    public UserAuthenticationEventHandler(UserDao userDao) {
        this.userDao = userDao;
    }

    @Async
    @EventListener(UserAuthenticationEvent.class)
    public void onUserAuthenticationEvent(final UserAuthenticationEvent event) {
        switch (event.getAction()) {
            case REGISTER:
                handleRegistrationEvent(event);
                break;
            case LOGIN:
                handleLoginEvent(event);
                break;
            case LOGOUT:
                handleLogoutEvent(event);
                break;
        }
    }

    private void handleRegistrationEvent(final UserAuthenticationEvent event) {
        switch (event.getResult()) {
            case SUCCESS:
                logAuthenticationInfo("Successful registration: '{}'", event.getUsername());
                break;
            case FAILURE:
                logAuthenticationInfo("Failure during registration with username: {}, message: {}",
                        event.getUsername(),
                        event.getMessage()
                );
        }
    }

    private void handleLoginEvent(final UserAuthenticationEvent event) {
        switch (event.getResult()) {
            case SUCCESS:
                logAuthenticationInfo("Successful login: '{}'", event.getUsername());

                setLoginDate(event.getActor());
                break;
            case FAILURE:
                logAuthenticationInfo("Failure during login with name: '{}', message: {}",
                        event.getUsername(),
                        event.getMessage());
                break;
        }
    }

    private void setLoginDate(UserModel actor) {
        final DateTime now = new DateTime();

        if (actor.getFirstLogin() == null) {
            actor.setFirstLogin(now);
            logAuthenticationInfo("First login: '{}'", actor.getUsername());
        }

        actor.setLastLogin(now);
        userDao.update(actor);
    }

    private void handleLogoutEvent(final UserAuthenticationEvent event) {
        if (event.getResult() == ActionEvent.Result.SUCCESS) {
            logAuthenticationInfo("Logout: '{}'", event.getUsername());
        }
    }


    private void logAuthenticationInfo(final String format, final Object... arguments) {
        logger.info(marker, format, arguments);
    }
}