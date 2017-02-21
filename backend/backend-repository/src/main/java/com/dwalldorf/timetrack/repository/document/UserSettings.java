package com.dwalldorf.timetrack.repository.document;

import java.io.Serializable;
import org.mongodb.morphia.annotations.Embedded;

/**
 * Not to be used outside of backend-repository
 */
@Embedded
public class UserSettings implements Serializable {
}