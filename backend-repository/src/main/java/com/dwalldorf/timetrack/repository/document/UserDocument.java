package com.dwalldorf.timetrack.repository.document;

import org.mongodb.morphia.annotations.Id;
import org.mongodb.morphia.annotations.Reference;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "users")
public class UserDocument {

    @Id
    private String id;

    @Reference
    private UserProperties userProperties;

    public UserDocument(String id) {
        this.id = id;
        this.userProperties = new UserProperties();
    }

    public UserDocument() {
        this(null);
    }

    public String getId() {
        return id;
    }

    public UserDocument setId(String id) {
        this.id = id;
        return this;
    }

    public UserProperties getUserProperties() {
        return userProperties;
    }

    public UserDocument setUserProperties(UserProperties userProperties) {
        this.userProperties = userProperties;
        return this;
    }
}