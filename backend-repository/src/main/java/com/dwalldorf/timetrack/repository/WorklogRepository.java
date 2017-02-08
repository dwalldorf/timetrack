package com.dwalldorf.timetrack.repository;

import com.dwalldorf.timetrack.repository.document.WorklogEntryDocument;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WorklogRepository extends MongoRepository<WorklogEntryDocument, String> {
}