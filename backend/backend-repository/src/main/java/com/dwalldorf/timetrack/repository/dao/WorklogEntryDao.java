package com.dwalldorf.timetrack.repository.dao;

import com.dwalldorf.timetrack.model.UserModel;
import com.dwalldorf.timetrack.model.WorklogEntryModel;
import com.dwalldorf.timetrack.model.internal.GraphConfig;
import com.dwalldorf.timetrack.repository.document.WorklogEntryDocument;
import com.dwalldorf.timetrack.repository.repository.WorklogRepository;
import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import javax.inject.Inject;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Component;

@Component
public class WorklogEntryDao {

    private final MongoTemplate mongoTemplate;

    private final WorklogRepository worklogRepository;

    @Inject
    public WorklogEntryDao(MongoTemplate mongoTemplate, WorklogRepository worklogRepository) {
        this.mongoTemplate = mongoTemplate;
        this.worklogRepository = worklogRepository;
    }

    public List<WorklogEntryModel> save(List<WorklogEntryModel> worklogEntries) {
        List<WorklogEntryDocument> documents = toDocumentList(worklogEntries);
        documents = worklogRepository.save(documents);

        return toModelList(documents);
    }

    public WorklogEntryModel save(WorklogEntryModel entry) {
        WorklogEntryDocument persistedEntry = worklogRepository.save(toDocument(entry));
        return toModel(persistedEntry);
    }

    public List<WorklogEntryModel> findByUser(UserModel user) {
        return toModelList(worklogRepository.findByUserIdOrderByStartDesc(user.getId()));
    }

    public WorklogEntryModel findById(String id) {
        return toModel(worklogRepository.findOne(id));
    }

    public void delete(WorklogEntryModel entry) {
        worklogRepository.delete(toDocument(entry));
    }

    public void delete(List<WorklogEntryModel> entries) {
        List<WorklogEntryDocument> documents = toDocumentList(entries);
        worklogRepository.delete(documents);
    }

    public List<WorklogEntryModel> findByGraphConfig(UserModel user, GraphConfig graphConf) {
        List<WorklogEntryDocument> documents = worklogRepository.findByUserIdAndStartBetween(
                user.getId(),
                graphConf.getFrom(),
                graphConf.getTo()
        );
        return toModelList(documents);
    }

    public List<String> getUserCustomers(UserModel user, String search) {
        return getDistinctValueFromWorklog(user, "customer", search);
    }

    public List<String> getUserProjects(UserModel user, String search) {
        return getDistinctValueFromWorklog(user, "project", search);
    }

    private List<String> getDistinctValueFromWorklog(UserModel user, String valueName, String search) {
        List<String> result = new ArrayList<>();
        DBCollection collection = mongoTemplate.getCollection(WorklogEntryDocument.COLLECTION_NAME);

        List<BasicDBObject> pipeline = Arrays.asList(
                new BasicDBObject("$match", new BasicDBObject("userId", user.getId())),
                new BasicDBObject("$group", new BasicDBObject("_id", "$" + valueName))
        );
        Iterable<DBObject> distinctValues = collection.aggregate(pipeline).results();

        for (DBObject dbObject : distinctValues) {
            String currentValue = String.valueOf(dbObject.get("_id"));

            if (search == null || search.isEmpty()) {
                result.add(currentValue);
            } else {
                if (currentValue.toLowerCase().contains(search.toLowerCase())) {
                    result.add(currentValue);
                }
            }
        }
        return result;
    }

    WorklogEntryDocument toDocument(WorklogEntryModel model) {
        if (model == null) {
            return null;
        }

        return new WorklogEntryDocument()
                .setId(model.getId())
                .setUserId(model.getUserId())
                .setCustomer(model.getCustomer())
                .setProject(model.getProject())
                .setTask(model.getTask())
                .setStart(model.getStart())
                .setStop(model.getStop())
                .setDuration(model.getDuration())
                .setComment(model.getComment());
    }

    List<WorklogEntryDocument> toDocumentList(List<WorklogEntryModel> models) {
        return models.stream()
                     .filter(Objects::nonNull)
                     .map(this::toDocument)
                     .collect(Collectors.toList());
    }

    WorklogEntryModel toModel(WorklogEntryDocument document) {
        if (document == null) {
            return null;
        }

        return new WorklogEntryModel()
                .setId(document.getId())
                .setUserId(document.getUserId())
                .setCustomer(document.getCustomer())
                .setProject(document.getProject())
                .setTask(document.getTask())
                .setStart(document.getStart())
                .setStop(document.getStop())
                .setDuration(document.getDuration())
                .setComment(document.getComment());
    }

    List<WorklogEntryModel> toModelList(List<WorklogEntryDocument> documents) {
        return documents.stream()
                        .filter(Objects::nonNull)
                        .map(this::toModel)
                        .collect(Collectors.toList());
    }
}