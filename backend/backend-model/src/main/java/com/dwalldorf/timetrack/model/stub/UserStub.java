package com.dwalldorf.timetrack.model.stub;

import com.dwalldorf.timetrack.model.UserModel;
import com.dwalldorf.timetrack.model.util.RandomUtil;
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
            email = USERNAME_PREFIX +
                    randomUtil.randomString(8) +
                    "@" +
                    randomUtil.randomString(8) +
                    ".tld";
        }
        if (registration == null) {
            registration = new DateTime().minusDays(randomUtil.randomInt(5, 120));
        }
        DateTime firstLogin = registration.plusMinutes(randomUtil.randomInt(10, 600));
        DateTime lastLogin = new DateTime().minusMinutes(randomUtil.randomInt(10, (24 * 60 * 2)));

        user.setUsername(username)
            .setEmail(email)
            .setConfirmedEmail(randomUtil.randomBoolean())
            .setPassword(password)
            .setRegistration(registration)
            .setFirstLogin(firstLogin)
            .setLastLogin(lastLogin);

        if (user.isConfirmedEmail()) {
            user.setFirstLogin(registration.plusMinutes(randomUtil.randomInt(10, 600)))
                .setLastLogin(lastLogin);
        }

        // userSettings
        if (admin == null) {
            admin = randomUtil.randomBoolean();
        }
        user.setAdmin(admin);

        return user;
    }
}