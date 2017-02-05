package com.dwalldorf.timetrack.service;

import com.dwalldorf.timetrack.document.User;
import com.dwalldorf.timetrack.document.UserProperties;
import com.dwalldorf.timetrack.event.LoginFailedEvent;
import com.dwalldorf.timetrack.event.UserAuthenticationEvent;
import com.dwalldorf.timetrack.exception.InvalidInputException;
import com.dwalldorf.timetrack.repository.UserRepository;
import java.util.Date;
import javax.inject.Inject;
import javax.servlet.http.HttpSession;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserService {

    private static final String SESSION_USER_ATTRIBUTE = "user";

    private final UserRepository userRepository;

    private final ApplicationEventPublisher eventPublisher;

    private final HttpSession httpSession;

    private final PasswordService passwordService;

    @Inject
    public UserService(
            UserRepository userRepository,
            ApplicationEventPublisher eventPublisher,
            HttpSession httpSession,
            PasswordService passwordService) {

        this.userRepository = userRepository;
        this.eventPublisher = eventPublisher;
        this.httpSession = httpSession;
        this.passwordService = passwordService;
    }

    @Transactional
    public User register(User user) {
        UserProperties properties = user.getUserProperties();

        User byUsernameOrEmail = userRepository.findByUserProperties_Username(properties.getUsername());
        if (byUsernameOrEmail != null) {
            throw new InvalidInputException("username or email already in use");
        }

        properties.setRegistration(new Date())
                  .setSalt(passwordService.createSalt())
                  .setHashedPassword(
                          passwordService.hash(properties.getPassword().toCharArray(), properties.getSalt())
                  );

        User persistedUser = userRepository.save(user);

        eventPublisher.publishEvent(UserAuthenticationEvent.registerEvent(persistedUser));
        return getSecureUserCopy(persistedUser);
    }

    public User login(final String username, final String password) {
        User dbUser = userRepository.findByUserProperties_Username(username);
        if (dbUser == null) {
            eventPublisher.publishEvent(new LoginFailedEvent(username));
            return null;
        }

        UserProperties properties = dbUser.getUserProperties();
        boolean passwordMatch = passwordService.isExpectedPassword(
                password.toCharArray(),
                properties.getSalt(),
                properties.getHashedPassword()
        );
        if (!passwordMatch) {
            eventPublisher.publishEvent(UserAuthenticationEvent.loginFailureEvent(username));
            return null;
        }

        initializeUserSession(dbUser);
        return getCurrentUser();
    }

    public void logout() {
        eventPublisher.publishEvent(UserAuthenticationEvent.logoutEvent(getCurrentUser()));
        httpSession.invalidate();
    }

    private void initializeUserSession(User user) {
        user = getSecureUserCopy(user);
        httpSession.setAttribute(SESSION_USER_ATTRIBUTE, user);

        eventPublisher.publishEvent(UserAuthenticationEvent.loginSuccessEvent(user));
    }

    public User getCurrentUser() {
        return (User) httpSession.getAttribute(SESSION_USER_ATTRIBUTE);
    }

    User getSecureUserCopy(final User user) {
        user.getUserProperties()
            .setPassword(null)
            .setSalt(null)
            .setHashedPassword(null);

        return user;
    }
}