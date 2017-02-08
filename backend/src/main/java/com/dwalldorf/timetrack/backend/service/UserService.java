package com.dwalldorf.timetrack.backend.service;

import com.dwalldorf.timetrack.backend.event.UserAuthenticationEvent;
import com.dwalldorf.timetrack.backend.exception.InvalidInputException;
import com.dwalldorf.timetrack.model.UserModel;
import com.dwalldorf.timetrack.repository.dao.UserDao;
import com.dwalldorf.timetrack.repository.exception.BadPasswordException;
import com.dwalldorf.timetrack.repository.exception.UserNotFoundException;
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
        final UserModel currentUser = getCurrentUser();
        if (currentUser != null) {
            final String currentUserUsername = currentUser.getUsername();
            if (!currentUserUsername.equals(username)) {
                eventPublisher.publishEvent(UserAuthenticationEvent.loginFailedEvent(
                        currentUserUsername,
                        String.format("user already logged in but tried to login as: '%s'", username))
                );
            } else {
                eventPublisher.publishEvent(UserAuthenticationEvent
                        .loginFailedEvent(currentUserUsername, "user already logged in")
                );
            }
            return currentUser;
        }

        try {
            UserModel loginUser = userDao.login(username, password);
            initializeUserSession(loginUser);
            return loginUser;
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