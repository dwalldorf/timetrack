package com.dwalldorf.timetrack.repository.document;

import org.mongodb.morphia.annotations.Embedded;

@Embedded
public class UserSettings {

    private boolean admin = false;

    public boolean isAdmin() {
        return admin;
    }

    public UserSettings setAdmin(boolean admin) {
        this.admin = admin;
        return this;
    }
}