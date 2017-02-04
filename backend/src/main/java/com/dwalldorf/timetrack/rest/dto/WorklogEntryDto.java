package com.dwalldorf.timetrack.rest.dto;

import com.dwalldorf.timetrack.document.WorklogEntry;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.io.Serializable;

public class WorklogEntryDto implements Serializable {

    private String id;

    private String customer;

    private String project;

    private String start;

    private String stop;

    private Integer duration;

    private String comment;

    public WorklogEntryDto(WorklogEntry worklogEntry) {
        this.id = worklogEntry.getId();
        this.customer = worklogEntry.getCustomer();
        this.project = worklogEntry.getProject();
        this.start = worklogEntry.getStart().toString();
        this.stop = worklogEntry.getStop().toString();
        this.duration = worklogEntry.getDuration();
        this.comment = worklogEntry.getComment();
    }

    @JsonProperty
    public String getId() {
        return id;
    }

    @JsonProperty
    public String getCustomer() {
        return customer;
    }

    @JsonProperty
    public String getProject() {
        return project;
    }

    @JsonProperty
    public String getStart() {
        return start;
    }

    @JsonProperty
    public String getStop() {
        return stop;
    }

    @JsonProperty
    public Integer getDuration() {
        return duration;
    }

    @JsonProperty
    public String getComment() {
        return comment;
    }
}
