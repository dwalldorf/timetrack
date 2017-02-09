package com.dwalldorf.timetrack.model;

import java.io.Serializable;
import org.joda.time.DateTime;

public class WorklogEntryModel implements Serializable {

    private String id;

    private String customer;

    private String project;

    private String task;

    private DateTime start;

    private DateTime stop;

    private Integer duration;

    private String comment;

    public WorklogEntryModel() {
    }

    public WorklogEntryModel(WorklogEntryModel worklogEntry) {
        this.id = worklogEntry.getId();
        this.customer = worklogEntry.getCustomer();
        this.project = worklogEntry.getProject();
        this.task = worklogEntry.getTask();
        this.start = worklogEntry.getStart();
        this.stop = worklogEntry.getStop();
        this.duration = worklogEntry.getDuration();
        this.comment = worklogEntry.getComment();
    }

    public boolean equalsLogically(WorklogEntryModel entry) {
        return (String.valueOf(this.getCustomer()).equals(String.valueOf(entry.getCustomer())) &&
                String.valueOf(this.getProject()).equals(String.valueOf(entry.getProject())) &&
                String.valueOf(this.getStart()).equals(String.valueOf(entry.getStart())) &&
                String.valueOf(this.getStop()).equals(String.valueOf(entry.getStop())) &&
                String.valueOf(this.getDuration()).equals(String.valueOf(entry.getDuration()))
        );
    }

    public String getId() {
        return id;
    }

    public WorklogEntryModel setId(String id) {
        this.id = id;
        return this;
    }

    public String getCustomer() {
        return customer;
    }

    public WorklogEntryModel setCustomer(String customer) {
        this.customer = customer;
        return this;
    }

    public String getProject() {
        return project;
    }

    public WorklogEntryModel setProject(String project) {
        this.project = project;
        return this;
    }

    public String getTask() {
        return task;
    }

    public WorklogEntryModel setTask(String task) {
        this.task = task;
        return this;
    }

    public DateTime getStart() {
        return start;
    }

    public WorklogEntryModel setStart(DateTime start) {
        this.start = start;
        return this;
    }

    public DateTime getStop() {
        return stop;
    }

    public WorklogEntryModel setStop(DateTime stop) {
        this.stop = stop;
        return this;
    }

    public Integer getDuration() {
        return duration;
    }

    public WorklogEntryModel setDuration(Integer duration) {
        this.duration = duration;
        return this;
    }

    public String getComment() {
        return comment;
    }

    public WorklogEntryModel setComment(String comment) {
        this.comment = comment;
        return this;
    }
}