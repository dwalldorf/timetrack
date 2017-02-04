package com.dwalldorf.timetrack.document;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.joda.time.DateTime;
import org.joda.time.Duration;
import org.junit.Test;

public class WorklogEntryTest {

    @Test
    public void testCopyConstructor() throws Exception {
        final String id = "abc001";
        final String customer = "testCustomer";
        final String project = "testProject";
        final DateTime start = new DateTime().minusHours(8);
        final DateTime stop = new DateTime();
        final Integer duration = (int) new Duration(start, stop).getStandardMinutes();
        final String comment = "some comment";

        final WorklogEntry original = new WorklogEntry()
                .setId(id)
                .setCustomer(customer)
                .setProject(project)
                .setStart(start)
                .setStop(stop)
                .setDuration(duration)
                .setComment(comment);

        final WorklogEntry copy = new WorklogEntry(original);

        assertEquals(id, copy.getId());
        assertEquals(customer, copy.getCustomer());
        assertEquals(project, copy.getProject());
        assertEquals(start, copy.getStart());
        assertEquals(stop, copy.getStop());
        assertEquals(duration, copy.getDuration());
        assertEquals(comment, copy.getComment());
    }

    @Test
    public void testEqualsLogically() throws Exception {
        final String id = "abc001";
        final String customer = "testCustomer";
        final String project = "testProject";
        final DateTime start = new DateTime().minusHours(8);
        final DateTime stop = new DateTime();
        final Integer duration = (int) new Duration(start, stop).getStandardMinutes();
        final String comment = "some comment";

        final WorklogEntry original = new WorklogEntry()
                .setId(id)
                .setCustomer(customer)
                .setProject(project)
                .setStart(start)
                .setStop(stop)
                .setDuration(duration)
                .setComment(comment);

        // copy is equal
        WorklogEntry copy = new WorklogEntry(original);
        assertTrue(copy.equalsLogically(original));

        // copy with other / without id is equal
        copy.setId("some other id");
        assertTrue(copy.equalsLogically(original));
        copy.setId(null);
        assertTrue(copy.equalsLogically(original));

        // copy with other / without comment is equal
        copy.setComment("some other comment");
        assertTrue(copy.equalsLogically(original));
        copy.setComment(null);
        assertTrue(copy.equalsLogically(original));

        // copy with other customer is NOT equal
        WorklogEntry copy2 = new WorklogEntry(copy);
        copy2.setCustomer("some other customer");
        assertFalse(copy2.equalsLogically(original));

        // copy with other project is NOT equal
        WorklogEntry copy3 = new WorklogEntry(copy);
        copy3.setProject("some other project");
        assertFalse(copy3.equalsLogically(original));

        // copy with other start is NOT equal
        WorklogEntry copy4 = new WorklogEntry(copy);
        copy4.setStart(new DateTime());
        assertFalse(copy4.equalsLogically(original));

        // copy with other stop is NOT equal
        WorklogEntry copy5 = new WorklogEntry(copy);
        copy5.setStop(copy5.getStop().minusMinutes(30));
        assertFalse(copy5.equalsLogically(original));

        // copy with other duration is NOT equal
        WorklogEntry copy6 = new WorklogEntry(copy);
        copy6.setDuration(123);
        assertFalse(copy6.equalsLogically(original));
    }
}