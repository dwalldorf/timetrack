package com.dwalldorf.timetrack.repository.document;

import java.io.Serializable;
import javax.validation.constraints.Size;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;
import org.joda.time.DateTime;
import org.mongodb.morphia.annotations.Embedded;
import org.mongodb.morphia.annotations.Reference;
import org.springframework.data.mongodb.core.index.Indexed;

/**
 * Not to be used outside of backend-repository
 */
@Embedded
public class UserProperties implements Serializable {

    @NotEmpty
    @Size(min = 5, max = 40)
    @Indexed(unique = true)
    private String username;

    @Email
    private String email;

    @NotEmpty
    private byte[] hashedPassword;

    @NotEmpty
    private byte[] salt;

    @NotEmpty
    private DateTime registration;

    private DateTime firstLogin;

    private DateTime lastLogin;

    private boolean confirmedEmail = false;

    @Reference
    private UserSettings userSettings;

    public String getUsername() {
        return username;
    }

    public UserProperties setUsername(String username) {
        this.username = username;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public UserProperties setEmail(String email) {
        this.email = email;
        return this;
    }

    public byte[] getHashedPassword() {
        return hashedPassword;
    }

    public UserProperties setHashedPassword(byte[] hashedPassword) {
        this.hashedPassword = hashedPassword;
        return this;
    }

    public byte[] getSalt() {
        return salt;
    }

    public UserProperties setSalt(byte[] salt) {
        this.salt = salt;
        return this;
    }

    public DateTime getRegistration() {
        return registration;
    }

    public UserProperties setRegistration(DateTime registration) {
        this.registration = registration;
        return this;
    }

    public DateTime getFirstLogin() {
        return firstLogin;
    }

    public UserProperties setFirstLogin(DateTime firstLogin) {
        this.firstLogin = firstLogin;
        return this;
    }

    public DateTime getLastLogin() {
        return lastLogin;
    }

    public UserProperties setLastLogin(DateTime lastLogin) {
        this.lastLogin = lastLogin;
        return this;
    }

    public boolean isConfirmedEmail() {
        return confirmedEmail;
    }

    public UserProperties setConfirmedEmail(boolean confirmedEmail) {
        this.confirmedEmail = confirmedEmail;
        return this;
    }

    public UserSettings getUserSettings() {
        return userSettings;
    }

    public UserProperties setUserSettings(UserSettings userSettings) {
        this.userSettings = userSettings;
        return this;
    }
}