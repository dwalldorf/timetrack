package com.dwalldorf.timetrack.repository;

import com.dwalldorf.timetrack.document.WorklogEntry;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WorklogRepository extends MongoRepository<WorklogEntry, String> {
}