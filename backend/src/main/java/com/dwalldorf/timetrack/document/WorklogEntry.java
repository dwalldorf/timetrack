package com.dwalldorf.timetrack.document;

import java.io.Serializable;
import org.joda.time.DateTime;
import org.mongodb.morphia.annotations.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "worklogs")
public class WorklogEntry implements Serializable {

    @Id
    private String id;

    private String customer;

    private String project;

    private String task;

    private DateTime start;

    private DateTime stop;

    private Integer duration;

    private String comment;

    public WorklogEntry() {
    }

    public WorklogEntry(WorklogEntry worklogEntry) {
        this.id = worklogEntry.getId();
        this.customer = worklogEntry.getCustomer();
        this.project = worklogEntry.getProject();
        this.task = worklogEntry.getTask();
        this.start = worklogEntry.getStart();
        this.stop = worklogEntry.getStop();
        this.duration = worklogEntry.getDuration();
        this.comment = worklogEntry.getComment();
    }

    public boolean equalsLogically(WorklogEntry entry) {
        return (entry.getCustomer().equals(this.getCustomer()) &&
                entry.getProject().equals(this.getProject()) &&
                entry.getStart().equals(this.getStart()) &&
                entry.getStop().equals(this.getStop()) &&
                entry.getDuration().equals(this.getDuration())
        );
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

    public Integer getDuration() {
        return duration;
    }

    public WorklogEntry setDuration(Integer duration) {
        this.duration = duration;
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