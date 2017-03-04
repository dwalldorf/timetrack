package com.dwalldorf.timetrack.repository.repository;

import com.dwalldorf.timetrack.repository.document.WorklogEntryDocument;
import java.util.List;
import org.joda.time.DateTime;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WorklogRepository extends MongoRepository<WorklogEntryDocument, String> {

    List<WorklogEntryDocument> findByUserIdOrderByStartDesc(String userId);

    List<WorklogEntryDocument> findByUserIdAndStartBetweenOrderByStartDesc(String userId, DateTime from, DateTime to);
}