package com.dwalldorf.timetrack.backend.model;

import java.io.Serializable;
import org.joda.time.DateTime;

public class GraphConfig implements Serializable {

    private DateTime from;

    private DateTime to;

    public DateTime getFrom() {
        return from;
    }

    /**
     *
     * @param from
     * @return
     */
    public GraphConfig setFrom(DateTime from) {
        this.from = from;
        return this;
    }

    public DateTime getTo() {
        return to;
    }

    public GraphConfig setTo(DateTime to) {
        this.to = to;
        return this;
    }
}