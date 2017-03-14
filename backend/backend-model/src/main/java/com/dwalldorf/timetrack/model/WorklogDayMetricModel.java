package com.dwalldorf.timetrack.model;

import org.joda.time.DateTime;

public class WorklogDayMetricModel extends AbstractModel {

    private String id;

    private String userId;

    private DateTime day;

    private Integer duration;

    public String getId() {
        return id;
    }

    public WorklogDayMetricModel setId(String id) {
        this.id = id;
        return this;
    }

    public String getUserId() {
        return userId;
    }

    public WorklogDayMetricModel setUserId(String userId) {
        this.userId = userId;
        return this;
    }

    public DateTime getDay() {
        return day;
    }

    public WorklogDayMetricModel setDay(DateTime day) {
        this.day = day;
        return this;
    }

    public Integer getDuration() {
        return duration;
    }

    public WorklogDayMetricModel setDuration(Integer duration) {
        this.duration = duration;
        return this;
    }
}