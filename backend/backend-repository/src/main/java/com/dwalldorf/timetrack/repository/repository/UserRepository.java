package com.dwalldorf.timetrack.repository.repository;

import com.dwalldorf.timetrack.repository.document.UserDocument;
import java.util.List;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends MongoRepository<UserDocument, String> {

    UserDocument findByUserProperties_Username(String username);

    List<UserDocument> findByUserProperties_UsernameLike(String username);
}