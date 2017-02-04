package com.dwalldorf.timetrack.repository;

import com.dwalldorf.timetrack.document.WorklogEntry;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface WorklogRepository extends MongoRepository<WorklogEntry, String> {
}
