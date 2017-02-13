package com.dwalldorf.timetrack.backend.service;

import static org.junit.Assert.assertEquals;

import com.dwalldorf.timetrack.backend.BaseTest;
import com.dwalldorf.timetrack.backend.exception.InvalidInputException;
import com.dwalldorf.timetrack.backend.model.GraphConfig;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.junit.Test;

public class GraphServiceTest extends BaseTest {

    private static final String STR_DATE_FROM = "2016-12-31";
    private static final String STR_DATE_TO = "2017-01-01";
    private static final String STR_INVALID_DATE_1 = "invalid date.";
    private static final String STR_INVALID_DATE_2 = "2017-01-01T12:15:45.000+01:00";
    private static final String STR_EMPTY = "";
    private static final String STR_TODAY = "today";

    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormat.forPattern("yyyy-MM-dd");

    private static final DateTime expectedFrom = DATE_FORMATTER.parseDateTime(STR_DATE_FROM);
    private static final DateTime expectedTo = DATE_FORMATTER.parseDateTime(STR_DATE_TO);

    private GraphService graphService;

    @Override
    protected void setUp() {
        this.graphService = new GraphService();
    }

    @Test(expected = InvalidInputException.class)
    public void testFromParameters_StartNull() {
        graphService.fromParameters(null, STR_DATE_TO);
    }

    @Test(expected = InvalidInputException.class)
    public void testFromParameters_StartEmpty() {
        graphService.fromParameters(STR_EMPTY, STR_DATE_TO);
    }

    @Test(expected = InvalidInputException.class)
    public void testFromParameters_EndNull() {
        graphService.fromParameters(STR_DATE_FROM, null);
    }

    @Test(expected = InvalidInputException.class)
    public void testFromParameters_EndEmpty() {
        graphService.fromParameters(STR_DATE_FROM, STR_EMPTY);
    }

    @Test(expected = InvalidInputException.class)
    public void testFromParameters_InvalidString1() {
        graphService.fromParameters(STR_DATE_FROM, STR_INVALID_DATE_1);
    }

    @Test(expected = InvalidInputException.class)
    public void testFromParameters_InvalidString2() {
        graphService.fromParameters(STR_DATE_FROM, STR_INVALID_DATE_2);
    }

    @Test(expected = InvalidInputException.class)
    public void testFromParameters_EndAfterStart() {
        graphService.fromParameters(STR_DATE_TO, STR_DATE_FROM);
    }

    @Test
    public void testFromParameters() {
        final DateTime expectedFrom = DATE_FORMATTER.parseDateTime(STR_DATE_FROM);
        final DateTime expectedTo = DATE_FORMATTER.parseDateTime(STR_DATE_TO);

        GraphConfig graphConfig = graphService.fromParameters(STR_DATE_FROM, STR_DATE_TO);

        assertEquals(expectedFrom, graphConfig.getFrom());
        assertEquals(expectedTo, graphConfig.getTo());
    }

    @Test(expected = InvalidInputException.class)
    public void testFromParameters_From_TodayString() {
        graphService.fromParameters(STR_TODAY, STR_DATE_TO);
    }

    @Test
    public void testFromParameters_To_TodayString() {
        final DateTime expectedFrom = DATE_FORMATTER.parseDateTime(STR_DATE_FROM);
        final DateTime expectedTo = DATE_FORMATTER.parseDateTime(DATE_FORMATTER.print(new DateTime()));

        GraphConfig graphConfig = graphService.fromParameters(STR_DATE_FROM, STR_TODAY);

        assertEquals(expectedFrom, graphConfig.getFrom());
        assertEquals(expectedTo, graphConfig.getTo());
    }
}