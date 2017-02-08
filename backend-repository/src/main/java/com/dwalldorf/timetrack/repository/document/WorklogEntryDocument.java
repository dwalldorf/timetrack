package com.dwalldorf.timetrack.repository.document;

import org.joda.time.DateTime;
import org.mongodb.morphia.annotations.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "worklogs")
public class WorklogEntryDocument {

    @Id
    private String id;

    private String customer;

    private String project;

    private String task;

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