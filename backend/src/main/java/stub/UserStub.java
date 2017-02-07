package stub;

import com.dwalldorf.timetrack.document.User;
import com.dwalldorf.timetrack.document.UserProperties;
import com.dwalldorf.timetrack.document.UserSettings;
import com.dwalldorf.timetrack.service.PasswordService;
import com.dwalldorf.timetrack.util.RandomUtil;
import javax.inject.Inject;
import org.joda.time.DateTime;
import org.springframework.stereotype.Component;

@Component
public class UserStub {

    private static final String USERNAME_PREFIX = "test_";

    private final RandomUtil randomUtil;

    private final PasswordService passwordService;

    @Inject
    public UserStub(RandomUtil randomUtil, PasswordService passwordService) {
        this.randomUtil = randomUtil;
        this.passwordService = passwordService;
    }

    public User createUser() {
        return createUser(null, null, null, null, null, null);
    }

    public User createUser(String userId) {
        return createUser(userId, null, null, null, null, null);
    }

    public User createUser(boolean admin) {
        return createUser(null, null, null, null, null, admin);
    }

    public User createUser(String userId, String username, String email, DateTime registration) {
        return createUser(userId, username, email, null, registration, null);
    }

    public User createUser(String userId, String username, String email, String password, DateTime registration, Boolean admin) {
        // user
        if (userId == null) {
            userId = randomUtil.randomString(24);
        }

        User user = new User()
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
        byte[] salt = passwordService.createSalt();
        DateTime lastLogin = new DateTime().minusMinutes(randomUtil.randomInt(10, 600));

        UserProperties userProperties = new UserProperties()
                .setUsername(username)
                .setEmail(email)
                .setConfirmedEmail(randomUtil.randomBoolean())
                .setPassword(password)
                .setSalt(salt)
                .setHashedPassword(passwordService.hash(password.toCharArray(), salt))
                .setRegistration(registration);

        if (userProperties.isConfirmedEmail()) {
            userProperties.setFirstLogin(registration.plusMinutes(randomUtil.randomInt(10, 600)))
                          .setLastLogin(lastLogin);
        }
        salt = null;
        password = null;

        // userSettings
        if (admin == null) {
            admin = randomUtil.randomBoolean();
        }
        final UserSettings userSettings = new UserSettings()
                .setAdmin(admin);

        userProperties.setUserSettings(userSettings);
        user.setUserProperties(userProperties);

        return user;
    }
}