package com.dwalldorf.timetrack.repository.dao;

import com.dwalldorf.timetrack.model.UserModel;
import com.dwalldorf.timetrack.repository.UserRepository;
import com.dwalldorf.timetrack.repository.document.UserDocument;
import com.dwalldorf.timetrack.repository.document.UserProperties;
import com.dwalldorf.timetrack.repository.document.UserSettings;
import com.dwalldorf.timetrack.repository.exception.BadPasswordException;
import com.dwalldorf.timetrack.repository.exception.UserNotFoundException;
import com.dwalldorf.timetrack.repository.service.PasswordService;
import javax.inject.Inject;
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

        return fromDocument(user);
    }

    public UserModel findByUsername(final String username) throws UserNotFoundException {
        UserDocument userDocument = userRepository.findByUserProperties_Username(username);
        return fromDocument(userDocument);
    }

    public UserModel register(UserModel user) {
        byte[] salt = passwordService.createSalt();
        UserProperties properties = new UserProperties()
                .setUsername(user.getUsername())
                .setEmail(user.getEmail())
                .setRegistration(user.getRegistration())
                .setSalt(salt)
                .setHashedPassword(passwordService.hash(user.getPassword().toCharArray(), salt))
                .setUserSettings(new UserSettings());
        salt = null;

        UserDocument userDocument = new UserDocument()
                .setUserProperties(properties);

        UserDocument persistedUserDocument = userRepository.save(userDocument);
        return fromDocument(persistedUserDocument);
    }

    UserModel fromDocument(UserDocument document) {
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
}