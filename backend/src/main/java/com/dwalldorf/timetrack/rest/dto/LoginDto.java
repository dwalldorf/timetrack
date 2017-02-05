package com.dwalldorf.timetrack.rest.dto;

import java.io.Serializable;

public class LoginDto implements Serializable {

    private String username;

    private String password;

    public String getUsername() {
        return username;
    }

    public LoginDto setUsername(String username) {
        this.username = username;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public LoginDto setPassword(String password) {
        this.password = password;
        return this;
    }
}
