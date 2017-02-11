package com.dwalldorf.timetrack.repository.document;

import java.io.Serializable;
import org.mongodb.morphia.annotations.Embedded;

/**
 * Not to be used outside of backend-repository
 */
@Embedded
public class UserSettings implements Serializable {

    private Boolean admin = false;

    public Boolean isAdmin() {
        return admin;
    }

    public UserSettings setAdmin(Boolean admin) {
        this.admin = admin;
        return this;
    }
}