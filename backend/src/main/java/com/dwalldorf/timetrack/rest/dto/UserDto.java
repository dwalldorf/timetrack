package com.dwalldorf.timetrack.rest.dto;

import com.dwalldorf.timetrack.document.User;
import com.dwalldorf.timetrack.document.UserProperties;
import com.dwalldorf.timetrack.document.UserSettings;
import java.io.Serializable;
import javax.validation.constraints.Size;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;
import org.joda.time.DateTime;
import org.springframework.data.annotation.Transient;

public class UserDto implements Serializable {

    private String id;

    @NotEmpty(message = "username must not be empty")
    @Size(min = 5, max = 40)
    private String username;

    @Email
    @NotEmpty(message = "email must not be empty")
    private String email;

    @Transient
    @NotEmpty
    private String password;

    private DateTime registration;

    private boolean confirmedEmail;

    private UserSettings userSettings;

    public UserDto() {
        this.userSettings = new UserSettings();
    }

    public static UserDto fromUser(User user) {
        if (user == null) {
            return null;
        }

        UserProperties properties = user.getUserProperties();

        UserDto userDto = new UserDto();
        userDto.setId(user.getId())
               .setUsername(properties.getUsername())
               .setEmail(properties.getEmail())
               .setRegistration(properties.getRegistration())
               .setConfirmedEmail(properties.isConfirmedEmail())
               .setUserSettings(properties.getUserSettings());

        return userDto;
    }

    public static User toUser(UserDto userDto) {
        User user = new User(userDto.getId());
        user.getUserProperties()
            .setUsername(userDto.getUsername())
            .setEmail(userDto.getEmail())
            .setPassword(userDto.getPassword())
            .setRegistration(userDto.getRegistration())
            .setConfirmedEmail(userDto.isConfirmedEmail())
            .setUserSettings(userDto.getUserSettings());

        return user;
    }

    public String getId() {
        return id;
    }

    public UserDto setId(String id) {
        this.id = id;
        return this;
    }

    public String getUsername() {
        return username;
    }

    public UserDto setUsername(String username) {
        this.username = username;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public UserDto setEmail(String email) {
        this.email = email;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public UserDto setPassword(String password) {
        this.password = password;
        return this;
    }

    public DateTime getRegistration() {
        return registration;
    }

    public UserDto setRegistration(DateTime registration) {
        this.registration = registration;
        return this;
    }

    public boolean isConfirmedEmail() {
        return confirmedEmail;
    }

    public UserDto setConfirmedEmail(boolean confirmedEmail) {
        this.confirmedEmail = confirmedEmail;
        return this;
    }

    public UserSettings getUserSettings() {
        return userSettings;
    }

    public UserDto setUserSettings(UserSettings userSettings) {
        this.userSettings = userSettings;
        return this;
    }
}
