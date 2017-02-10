package com.dwalldorf.timetrack.repository.dao;

import com.dwalldorf.timetrack.model.UserModel;
import com.dwalldorf.timetrack.repository.document.UserDocument;
import com.dwalldorf.timetrack.repository.document.UserProperties;
import com.dwalldorf.timetrack.repository.document.UserSettings;
import com.dwalldorf.timetrack.repository.exception.BadPasswordException;
import com.dwalldorf.timetrack.repository.exception.UserNotFoundException;
import com.dwalldorf.timetrack.repository.repository.UserRepository;
import com.dwalldorf.timetrack.repository.service.PasswordService;
import javax.inject.Inject;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

@Component
public class UserDao {

    private final PasswordService passwordService;

    private final UserRepository userRepository;

    @Inject
    public UserDao(PasswordService passwordService, UserRepository userRepository) {
        this.passwordService = passwordService;
        this.userRepository = userRepository;
    }

    public UserModel login(final String username, final String password) throws UserNotFoundException, BadPasswordException {
        UserDocument user = userRepository.findByUserProperties_Username(username);

        if (user == null) {
            throw new UserNotFoundException(username);
        }

        UserProperties properties = user.getUserProperties();
        boolean passwordMatch = passwordService.isExpectedPassword(
                password.toCharArray(),
                properties.getSalt(),
                properties.getHashedPassword()
        );
        if (!passwordMatch) {
            throw new BadPasswordException(username);
        }

        return toModel(user);
    }

    public UserModel findByUsername(final String username) throws UserNotFoundException {
        UserDocument userDocument = userRepository.findByUserProperties_Username(username);
        return toModel(userDocument);
    }

    public UserModel register(UserModel user) {
        byte[] salt = passwordService.createSalt();

        UserDocument userDocument = toDocument(user);
        userDocument.getUserProperties()
                    .setSalt(salt)
                    .setHashedPassword(passwordService.hash(user.getPassword().toCharArray(), salt));
        salt = null;

        UserDocument persistedUserDocument = userRepository.save(userDocument);
        return toModel(persistedUserDocument);
    }

    /**
     * Updates only fields that are set in {@code user}.
     *
     * @param user user to update
     * @return updated user
     */
    public UserModel update(UserModel user) {
        final String userId = user.getId();

        UserDocument dbUser = userRepository.findOne(userId);
        UserProperties userProps = dbUser.getUserProperties();

        // username
        if (user.getUsername() != null &&
                !StringUtils.equals(userProps.getUsername(), user.getUsername())) {
            userProps.setUsername(user.getUsername());
        }

        // email
        if (user.getEmail() != null &&
                !StringUtils.equals(userProps.getEmail(), user.getEmail())) {
            userProps.setEmail(user.getEmail());
        }

        // registration
        if (user.getRegistration() != null &&
                !String.valueOf(userProps.getRegistration()).equals(String.valueOf(user.getRegistration()))) {
            userProps.setRegistration(user.getRegistration());
        }

        // firstLogin
        if (user.getFirstLogin() != null &&
                !String.valueOf(userProps.getFirstLogin()).equals(String.valueOf(user.getFirstLogin()))) {
            userProps.setFirstLogin(user.getFirstLogin());
        }

        // lastLogin
        if (user.getLastLogin() != null &&
                !String.valueOf(userProps.getLastLogin()).equals(String.valueOf(user.getLastLogin()))) {
            userProps.setLastLogin(user.getLastLogin());
        }
        if (user.isAdmin() != null &&
                !userProps.getUserSettings().isAdmin() == user.isAdmin()) {
            userProps.getUserSettings().setAdmin(user.isAdmin());
        }

        dbUser = userRepository.save(dbUser);
        return toModel(dbUser);
    }

    UserModel toModel(UserDocument document) {
        if (document == null) {
            return null;
        }

        return new UserModel()
                .setId(document.getId())
                .setUsername(document.getUserProperties().getUsername())
                .setEmail(document.getUserProperties().getEmail())
                .setPassword(null) // nobody needs to know
                .setConfirmedEmail(document.getUserProperties().isConfirmedEmail())
                .setRegistration(document.getUserProperties().getRegistration())
                .setFirstLogin(document.getUserProperties().getFirstLogin())
                .setLastLogin(document.getUserProperties().getLastLogin())
                .setAdmin(document.getUserProperties().getUserSettings().isAdmin());
    }

    UserDocument toDocument(UserModel user) {
        UserProperties properties = new UserProperties()
                .setUsername(user.getUsername())
                .setEmail(user.getEmail())
                .setRegistration(user.getRegistration())
                .setFirstLogin(user.getFirstLogin())
                .setLastLogin(user.getLastLogin())
                .setUserSettings(new UserSettings()
                        .setAdmin(user.isAdmin()));

        return new UserDocument()
                .setId(user.getId())
                .setUserProperties(properties);
    }
}