package com.dwalldorf.timetrack.repository.dao;

import com.dwalldorf.timetrack.model.UserModel;
import com.dwalldorf.timetrack.model.WorklogDayMetricModel;
import com.dwalldorf.timetrack.model.internal.GraphConfig;
import com.dwalldorf.timetrack.repository.document.WorklogDayMetricDocument;
import com.dwalldorf.timetrack.repository.repository.WorklogDayMetricRepository;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import javax.inject.Inject;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Component;

@Component
public class WorklogDayMetricDao {

    private final MongoTemplate mongoTemplate;

    private final WorklogDayMetricRepository repository;

    @Inject
    public WorklogDayMetricDao(MongoTemplate mongoTemplate, WorklogDayMetricRepository metricDocumentRepository) {
        this.mongoTemplate = mongoTemplate;
        this.repository = metricDocumentRepository;
    }

    public List<WorklogDayMetricModel> findByUserAndGraphConfig(UserModel user, GraphConfig graphConfig) {
        List<WorklogDayMetricDocument> documents = repository.findByUserIdAndStartBetweenOrderByStartDesc(
                user.getId(),
                graphConfig.getFrom(),
                graphConfig.getTo()
        );
        return toModelList(documents);
    }

    WorklogDayMetricModel toModel(WorklogDayMetricDocument document) {
        return new WorklogDayMetricModel()
                .setId(document.getId())
                .setUserId(document.getUserId())
                .setDay(document.getDay())
                .setDuration(document.getDuration());
    }

    List<WorklogDayMetricModel> toModelList(List<WorklogDayMetricDocument> documents) {
        return documents.stream()
                        .filter(Objects::nonNull)
                        .map(this::toModel)
                        .collect(Collectors.toList());
    }
}