package com.dwalldorf.timetrack.repository.document;

import java.io.Serializable;
import org.joda.time.DateTime;
import org.mongodb.morphia.annotations.Id;
import org.mongodb.morphia.annotations.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Not to be used outside of backend-repository
 */
@Document(collection = WorklogDayMetricDocument.COLLECTION_NAME)
public class WorklogDayMetricDocument implements Serializable {

    static final String COLLECTION_NAME = "metric_worklog_day";

    @Id
    private String id;

    @Indexed
    private String userId;

    @Indexed
    private DateTime day;

    private Integer duration;

    public static String getCollectionName() {
        return COLLECTION_NAME;
    }

    public String getId() {
        return id;
    }

    public WorklogDayMetricDocument setId(String id) {
        this.id = id;
        return this;
    }

    public String getUserId() {
        return userId;
    }

    public WorklogDayMetricDocument setUserId(String userId) {
        this.userId = userId;
        return this;
    }

    public DateTime getDay() {
        return day;
    }

    public WorklogDayMetricDocument setDay(DateTime day) {
        this.day = day;
        return this;
    }

    public Integer getDuration() {
        return duration;
    }

    public WorklogDayMetricDocument setDuration(Integer duration) {
        this.duration = duration;
        return this;
    }
}