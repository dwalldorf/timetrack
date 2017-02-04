package com.dwalldorf.timetrack.document;

import org.joda.time.DateTime;
import org.mongodb.morphia.annotations.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class WorklogEntry {

    @Id
    private String id;

    private String customer;

    private String project;

    private String task;

    private DateTime start;

    private DateTime stop;

    private Long minutes;

    private String comment;

    public boolean equalsLogically(WorklogEntry entry) {
        return (entry.getCustomer().equals(this.getCustomer()) &&
                entry.getProject().equals(this.getProject()) &&
                entry.getStart().equals(this.getStart()) &&
                entry.getStop().equals(this.getStop()) &&
                entry.getMinutes().equals(this.getMinutes()));
    }

    public String getId() {
        return id;
    }

    public WorklogEntry setId(String id) {
        this.id = id;
        return this;
    }

    public String getCustomer() {
        return customer;
    }

    public WorklogEntry setCustomer(String customer) {
        this.customer = customer;
        return this;
    }

    public String getProject() {
        return project;
    }

    public WorklogEntry setProject(String project) {
        this.project = project;
        return this;
    }

    public String getTask() {
        return task;
    }

    public WorklogEntry setTask(String task) {
        this.task = task;
        return this;
    }

    public DateTime getStart() {
        return start;
    }

    public WorklogEntry setStart(DateTime start) {
        this.start = start;
        return this;
    }

    public DateTime getStop() {
        return stop;
    }

    public WorklogEntry setStop(DateTime stop) {
        this.stop = stop;
        return this;
    }

    public Long getMinutes() {
        return minutes;
    }

    public WorklogEntry setMinutes(Long minutes) {
        this.minutes = minutes;
        return this;
    }

    public String getComment() {
        return comment;
    }

    public WorklogEntry setComment(String comment) {
        this.comment = comment;
        return this;
    }
}
