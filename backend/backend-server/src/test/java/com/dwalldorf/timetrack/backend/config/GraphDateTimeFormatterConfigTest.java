package com.dwalldorf.timetrack.backend.config;

import static org.junit.Assert.assertTrue;

import com.dwalldorf.timetrack.backend.BaseTest;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormatter;
import org.junit.Test;

public class GraphDateTimeFormatterConfigTest extends BaseTest {

    private GraphDateTimeFormatterConfig graphDateTimeFormatterConfig;

    @Override
    protected void setUp() {
        this.graphDateTimeFormatterConfig = new GraphDateTimeFormatterConfig();
    }

    @Test
    public void dateTimeFormatter() throws Exception {
        final String expectedPattern = "^\\d{4}-\\d{2}-\\d{2}$";

        DateTimeFormatter dateTimeFormatter = graphDateTimeFormatterConfig.dateTimeFormatter();
        String printedDateTime = dateTimeFormatter.print(new DateTime());

        assertTrue(printedDateTime.matches(expectedPattern));
    }
}