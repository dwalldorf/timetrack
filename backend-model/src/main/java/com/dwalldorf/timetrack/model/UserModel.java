package com.dwalldorf.timetrack.model;

import java.io.Serializable;
import javax.validation.constraints.Size;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;
import org.joda.time.DateTime;

public class UserModel implements Serializable {

    private String id;

    @NotEmpty(message = "username must not be empty")
    @Size(min = 3, max = 100,message = "username must be between 3 and 100 characters")
    private String username;

    @NotEmpty(message = "email must not be empty")
    @Email(message = "email is invalid")
    private String email;

    private boolean confirmedEmail;

    @NotEmpty(message = "password must not be empty")
    private String password;

    private DateTime registration;

    private DateTime firstLogin;

    private DateTime lastLogin;

    private boolean admin;

    public String getId() {
        return id;
    }

    public UserModel setId(String id) {
        this.id = id;
        return this;
    }

    public String getUsername() {
        return username;
    }

    public UserModel setUsername(String username) {
        this.username = username;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public UserModel setEmail(String email) {
        this.email = email;
        return this;
    }

    public boolean isConfirmedEmail() {
        return confirmedEmail;
    }

    public UserModel setConfirmedEmail(boolean confirmedEmail) {
        this.confirmedEmail = confirmedEmail;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public UserModel setPassword(String password) {
        this.password = password;
        return this;
    }

    public DateTime getRegistration() {
        return registration;
    }

    public UserModel setRegistration(DateTime registration) {
        this.registration = registration;
        return this;
    }

    public DateTime getFirstLogin() {
        return firstLogin;
    }

    public UserModel setFirstLogin(DateTime firstLogin) {
        this.firstLogin = firstLogin;
        return this;
    }

    public DateTime getLastLogin() {
        return lastLogin;
    }

    public UserModel setLastLogin(DateTime lastLogin) {
        this.lastLogin = lastLogin;
        return this;
    }

    public boolean isAdmin() {
        return admin;
    }

    public UserModel setAdmin(boolean admin) {
        this.admin = admin;
        return this;
    }
}