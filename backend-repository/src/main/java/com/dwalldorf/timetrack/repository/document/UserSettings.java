package com.dwalldorf.timetrack.repository.document;

import java.io.Serializable;
import org.mongodb.morphia.annotations.Embedded;

/**
 * Not to be used outside of backend-repository
 */
@Embedded
public class UserSettings implements Serializable {

    private boolean admin = false;

    public boolean isAdmin() {
        return admin;
    }

    public UserSettings setAdmin(boolean admin) {
        this.admin = admin;
        return this;
    }
}