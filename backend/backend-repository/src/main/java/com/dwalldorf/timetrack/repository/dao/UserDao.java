package com.dwalldorf.timetrack.repository.dao;

import com.dwalldorf.timetrack.model.UserModel;
import com.dwalldorf.timetrack.repository.document.UserDocument;
import com.dwalldorf.timetrack.repository.document.UserProperties;
import com.dwalldorf.timetrack.repository.exception.BadPasswordException;
import com.dwalldorf.timetrack.repository.exception.UserNotFoundException;
import com.dwalldorf.timetrack.repository.repository.UserRepository;
import com.dwalldorf.timetrack.repository.service.PasswordService;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
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

        if (user.getUsername() != null) {
            userProps.setUsername(user.getUsername());
        }

        if (user.getEmail() != null) {
            userProps.setEmail(user.getEmail());
        }

        if (user.getRegistration() != null) {
            userProps.setRegistration(user.getRegistration());
        }

        if (user.getFirstLogin() != null) {
            userProps.setFirstLogin(user.getFirstLogin());
        }

        if (user.getLastLogin() != null) {
            userProps.setLastLogin(user.getLastLogin());
        }

        if (user.getWorkingHoursWeek() != null) {
            userProps.setWorkingHoursWeek(user.getWorkingHoursWeek());
        }

        dbUser = userRepository.save(dbUser);
        return toModel(dbUser);
    }

    public List<UserModel> findTestUsers() {
        List<UserDocument> users = userRepository.findByUserProperties_UsernameLike("test_");
        return toModelList(users);
    }

    public void delete(UserModel user) {
        userRepository.delete(toDocument(user));
    }

    UserModel toModel(UserDocument document) {
        if (document == null) {
            return null;
        }

        UserProperties userProperties = document.getUserProperties();
        return new UserModel()
                .setId(document.getId())
                .setUsername(userProperties.getUsername())
                .setEmail(userProperties.getEmail())
                .setPassword(null) // nobody needs to know
                .setConfirmedEmail(userProperties.isConfirmedEmail())
                .setRegistration(userProperties.getRegistration())
                .setFirstLogin(userProperties.getFirstLogin())
                .setLastLogin(userProperties.getLastLogin())
                .setWorkingHoursWeek(userProperties.getWorkingHoursWeek());
    }

    List<UserModel> toModelList(List<UserDocument> models) {
        return models.stream()
                     .filter(Objects::nonNull)
                     .map(this::toModel)
                     .collect(Collectors.toList());
    }

    UserDocument toDocument(UserModel user) {
        if (user == null) {
            return null;
        }

        UserProperties properties = new UserProperties()
                .setUsername(user.getUsername())
                .setEmail(user.getEmail())
                .setConfirmedEmail(user.isConfirmedEmail())
                .setRegistration(user.getRegistration())
                .setFirstLogin(user.getFirstLogin())
                .setLastLogin(user.getLastLogin())
                .setWorkingHoursWeek(user.getWorkingHoursWeek());

        return new UserDocument()
                .setId(user.getId())
                .setUserProperties(properties);
    }
}