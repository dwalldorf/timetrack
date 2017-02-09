package com.dwalldorf.timetrack.model.stub;

import com.dwalldorf.timetrack.model.util.RandomUtil;
import com.dwalldorf.timetrack.model.UserModel;
import javax.inject.Inject;
import org.joda.time.DateTime;
import org.springframework.stereotype.Component;

@Component
public class UserStub {

    private static final String USERNAME_PREFIX = "test_";

    private final RandomUtil randomUtil;

    @Inject
    public UserStub(RandomUtil randomUtil) {
        this.randomUtil = randomUtil;
    }

    public UserModel createUser() {
        return createUser(null, null, null, null, null, null);
    }

    public UserModel createUser(String userId) {
        return createUser(userId, null, null, null, null, null);
    }

    public UserModel createUser(boolean admin) {
        return createUser(null, null, null, null, null, admin);
    }

    public UserModel createUser(String userId, String username, String email, DateTime registration) {
        return createUser(userId, username, email, null, registration, null);
    }

    public UserModel createUser(String userId, String username, String email, String password, DateTime registration, Boolean admin) {
        // user
        if (userId == null) {
            userId = randomUtil.randomString(24);
        }

        UserModel user = new UserModel()
                .setId(userId);

        // userProperties
        if (username == null) {
            username = USERNAME_PREFIX + randomUtil.randomString(10);
        }
        if (password == null) {
            password = randomUtil.randomString(20);
        }
        if (email == null) {
            email = randomUtil.randomString(8) + "@" + randomUtil.randomString(8) + ".com";
        }
        if (registration == null) {
            registration = new DateTime().minusDays(randomUtil.randomInt(2, 30));
        }
        DateTime lastLogin = new DateTime().minusMinutes(randomUtil.randomInt(10, 600));

        user.setUsername(username)
            .setEmail(email)
            .setConfirmedEmail(randomUtil.randomBoolean())
            .setPassword(password)
            .setRegistration(registration);

        if (user.isConfirmedEmail()) {
            user.setFirstLogin(registration.plusMinutes(randomUtil.randomInt(10, 600)))
                .setLastLogin(lastLogin);
        }
        password = null;

        // userSettings
        if (admin == null) {
            admin = randomUtil.randomBoolean();
        }
        user.setAdmin(admin);

        return user;
    }
}