package com.dwalldorf.timetrack.repository.service;

import com.dwalldorf.timetrack.repository.dao.UserDao;
import com.dwalldorf.timetrack.repository.backend.event.UserAuthenticationEvent;
import com.dwalldorf.timetrack.repository.exception.BadPasswordException;
import com.dwalldorf.timetrack.repository.exception.InvalidInputException;
import com.dwalldorf.timetrack.repository.exception.UserNotFoundException;
import com.dwalldorf.timetrack.model.UserModel;
import javax.inject.Inject;
import javax.servlet.http.HttpSession;
import org.joda.time.DateTime;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserService {

    private static final String SESSION_USER_ATTRIBUTE = "user";

    private final UserDao userDao;

    private final ApplicationEventPublisher eventPublisher;

    private final HttpSession httpSession;

    @Inject
    public UserService(UserDao userDao, ApplicationEventPublisher eventPublisher, HttpSession httpSession) {
        this.userDao = userDao;
        this.eventPublisher = eventPublisher;
        this.httpSession = httpSession;
    }

    @Transactional
    public UserModel register(UserModel user) {
        final String username = user.getUsername();

        UserModel userModel = userDao.findByUsername(username);
        if (userModel != null) {
            final String message = "username or email already in use";

            eventPublisher.publishEvent(UserAuthenticationEvent.registrationFailedEvent(username, message));
            throw new InvalidInputException(message);
        }

        user.setRegistration(new DateTime());
        user = userDao.register(user);

        eventPublisher.publishEvent(UserAuthenticationEvent.registrationSuccessEvent(user));
        return user;
    }

    public UserModel login(final String username, final String password) {
        if (getCurrentUser() != null) {
            eventPublisher.publishEvent(UserAuthenticationEvent.loginFailedEvent(username, "user already logged in"));
            return getCurrentUser();
        }

        try {
            initializeUserSession(userDao.login(username, password));
            return getCurrentUser();
        } catch (UserNotFoundException ex) {
            eventPublisher.publishEvent(UserAuthenticationEvent.loginFailedEvent(username, "username not found"));
            return null;
        } catch (BadPasswordException ex) {
            eventPublisher.publishEvent(UserAuthenticationEvent.loginFailedEvent(username, "wrong password"));
            return null;
        }
    }

    public void logout() {
        eventPublisher.publishEvent(UserAuthenticationEvent.logoutEvent(getCurrentUser()));
        httpSession.invalidate();
    }

    private void initializeUserSession(UserModel user) {
        httpSession.setAttribute(SESSION_USER_ATTRIBUTE, user);
        eventPublisher.publishEvent(UserAuthenticationEvent.loginSuccessEvent(user));
    }

    public UserModel getCurrentUser() {
        return (UserModel) httpSession.getAttribute(SESSION_USER_ATTRIBUTE);
    }
}