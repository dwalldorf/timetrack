package com.dwalldorf.timetrack.repository.document;

import org.joda.time.DateTime;
import org.mongodb.morphia.annotations.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Not to be used outside of backend-repository
 */
@Document(collection = WorklogEntryDocument.COLLECTION_NAME)
public class WorklogEntryDocument {

    public static final String COLLECTION_NAME = "worklogs";

    @Id
    private String id;

    @Indexed
    private String userId;

    @Indexed
    private String customer;

    @Indexed
    private String project;

    private String task;

    @Indexed
    private DateTime start;

    private DateTime stop;

    private Integer duration;

    private String comment;

    public String getId() {
        return id;
    }

    public WorklogEntryDocument setId(String id) {
        this.id = id;
        return this;
    }

    public String getUserId() {
        return userId;
    }

    public WorklogEntryDocument setUserId(String userId) {
        this.userId = userId;
        return this;
    }

    public String getCustomer() {
        return customer;
    }

    public WorklogEntryDocument setCustomer(String customer) {
        this.customer = customer;
        return this;
    }

    public String getProject() {
        return project;
    }

    public WorklogEntryDocument setProject(String project) {
        this.project = project;
        return this;
    }

    public String getTask() {
        return task;
    }

    public WorklogEntryDocument setTask(String task) {
        this.task = task;
        return this;
    }

    public DateTime getStart() {
        return start;
    }

    public WorklogEntryDocument setStart(DateTime start) {
        this.start = start;
        return this;
    }

    public DateTime getStop() {
        return stop;
    }

    public WorklogEntryDocument setStop(DateTime stop) {
        this.stop = stop;
        return this;
    }

    public Integer getDuration() {
        return duration;
    }

    public WorklogEntryDocument setDuration(Integer duration) {
        this.duration = duration;
        return this;
    }

    public String getComment() {
        return comment;
    }

    public WorklogEntryDocument setComment(String comment) {
        this.comment = comment;
        return this;
    }
}