package com.dwalldorf.timetrack.repository.repository;

import com.dwalldorf.timetrack.repository.document.WorklogDayMetricDocument;
import java.util.List;
import org.joda.time.DateTime;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface WorklogDayMetricRepository extends MongoRepository<WorklogDayMetricDocument, String> {

    List<WorklogDayMetricDocument> findByUserIdAndStartBetweenOrderByStartDesc(String userId, DateTime from, DateTime to);
}