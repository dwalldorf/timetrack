package com.dwalldorf.timetrack.backend.service;

import com.dwalldorf.timetrack.backend.exception.InvalidInputException;
import com.dwalldorf.timetrack.model.internal.GraphConfig;
import javax.inject.Inject;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormatter;
import org.springframework.stereotype.Service;

@Service
public class GraphService {

    private final DateTimeFormatter dateTimeFormatter;

    @Inject
    public GraphService(DateTimeFormatter formatter) {
        this.dateTimeFormatter = formatter;
    }

    public GraphConfig fromParameters(String fromStr, String toStr, String scaleStr) throws InvalidInputException {
        if (fromStr == null) {
            throw new InvalidInputException("fromStr must not be null");
        }
        if (toStr == null) {
            throw new InvalidInputException("toStr must not be null");
        }

        DateTime from;
        DateTime to;

        if (toStr.equals("today")) {
            toStr = dateTimeFormatter.print(new DateTime());
        }
        try {
            from = dateTimeFormatter.parseDateTime(fromStr);
            to = dateTimeFormatter.parseDateTime(toStr);
        } catch (IllegalArgumentException e) {
            throw new InvalidInputException(e.getMessage(), e);
        }

        if (!from.isBefore(to)) {
            throw new InvalidInputException("'from' must be before 'to'");
        }

        scaleStr = scaleStr.trim().toUpperCase();
        GraphConfig.Scale scale;
        try {
            scale = GraphConfig.Scale.valueOf(scaleStr);
        } catch (IllegalArgumentException e) {
            throw new InvalidInputException("invalid scale: " + scaleStr);
        }

        return new GraphConfig()
                .setFrom(from)
                .setTo(to)
                .setScale(scale);
    }
}