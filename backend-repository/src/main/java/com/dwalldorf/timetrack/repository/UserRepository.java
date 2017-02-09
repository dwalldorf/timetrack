package com.dwalldorf.timetrack.repository;

import com.dwalldorf.timetrack.repository.document.UserDocument;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends MongoRepository<UserDocument, String> {

    UserDocument findByUserProperties_Username(String username);
}