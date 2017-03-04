package com.dwalldorf.timetrack.repository.document;

import java.io.Serializable;
import org.mongodb.morphia.annotations.Id;
import org.mongodb.morphia.annotations.Reference;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Not to be used outside of backend-repository
 */
@Document(collection = UserDocument.COLLECTION_NAME)
public class UserDocument implements Serializable {

    static final String COLLECTION_NAME = "users";

    @Id
    private String id;

    @Reference
    private UserProperties userProperties;

    public UserDocument() {
        this.userProperties = new UserProperties();
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