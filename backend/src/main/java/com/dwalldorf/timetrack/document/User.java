package com.dwalldorf.timetrack.document;

import org.mongodb.morphia.annotations.Id;
import org.mongodb.morphia.annotations.Reference;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "users")
public class User {

    @Id
    private String id;

    @Reference
    private UserProperties userProperties;

    public User(String id) {
        this.id = id;
        this.userProperties = new UserProperties();
    }

    public User() {
        this(null);
    }

    public String getId() {
        return id;
    }

    public User setId(String id) {
        this.id = id;
        return this;
    }

    public UserProperties getUserProperties() {
        return userProperties;
    }

    public User setUserProperties(UserProperties userProperties) {
        this.userProperties = userProperties;
        return this;
    }
}