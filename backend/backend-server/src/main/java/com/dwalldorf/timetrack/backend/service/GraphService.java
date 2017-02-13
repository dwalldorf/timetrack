package com.dwalldorf.timetrack.backend.service;

import com.dwalldorf.timetrack.backend.exception.InvalidInputException;
import com.dwalldorf.timetrack.backend.model.GraphConfig;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.springframework.stereotype.Service;

@Service
public class GraphService {

    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormat.forPattern("yyyy-MM-dd");

    public GraphConfig fromParameters(String fromStr, String toStr) throws InvalidInputException {
        if (fromStr == null) {
            throw new InvalidInputException("fromStr must not be null");
        }
        if (toStr == null) {
            throw new InvalidInputException("toStr must not be null");
        }

        DateTime from;
        DateTime to;

        if (toStr.equals("today")) {
            toStr = DATE_FORMATTER.print(new DateTime());
        }
        try {
            from = DATE_FORMATTER.parseDateTime(fromStr);
            to = DATE_FORMATTER.parseDateTime(toStr);
        } catch (IllegalArgumentException e) {
            throw new InvalidInputException(e.getMessage(), e);
        }

        if (!from.isBefore(to)) {
            throw new InvalidInputException("'from' must be before 'to'");
        }

        return new GraphConfig()
                .setFrom(from)
                .setTo(to);
    }
}