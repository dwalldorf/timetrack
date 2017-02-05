package com.dwalldorf.timetrack.repository;

import com.dwalldorf.timetrack.document.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends MongoRepository<User, String> {

    User findByUserProperties_Username(String username);
}