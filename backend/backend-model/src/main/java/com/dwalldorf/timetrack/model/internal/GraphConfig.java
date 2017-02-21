package com.dwalldorf.timetrack.model.internal;

import java.io.Serializable;
import org.joda.time.DateTime;

public class GraphConfig implements Serializable {

    public enum Scale {
        DAY,
        WEEK,
        MONTH
    }

    private DateTime from;

    private DateTime to;

    private Scale scale;

    public DateTime getFrom() {
        return from;
    }

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

    public Scale getScale() {
        return scale;
    }

    public GraphConfig setScale(Scale scale) {
        this.scale = scale;
        return this;
    }
}