package com.dwalldorf.timetrack.model;

import static com.dwalldorf.timetrack.model.ObjectOrigin.USER;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.joda.time.DateTime;
import org.joda.time.Duration;
import org.junit.Test;

public class WorklogEntryModelTest {

    @Test
    public void testCopyConstructor() throws Exception {
        final String id = "abc001";
        final String customer = "testCustomer";
        final String project = "testProject";
        final DateTime start = new DateTime().minusHours(8);
        final DateTime stop = new DateTime();
        final Integer duration = (int) new Duration(start, stop).getStandardMinutes();
        final String comment = "some comment";

        final WorklogEntryModel original = new WorklogEntryModel()
                .setId(id)
                .setCustomer(customer)
                .setProject(project)
                .setStart(start)
                .setStop(stop)
                .setDuration(duration)
                .setComment(comment)
                .setOrigin(USER);

        final WorklogEntryModel copy = new WorklogEntryModel(original);

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

        final WorklogEntryModel original = new WorklogEntryModel()
                .setId(id)
                .setCustomer(customer)
                .setProject(project)
                .setStart(start)
                .setStop(stop)
                .setDuration(duration)
                .setComment(comment);

        // copy is equal
        WorklogEntryModel copy = new WorklogEntryModel(original);
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
        WorklogEntryModel copy2 = new WorklogEntryModel(copy);
        copy2.setCustomer("some other customer");
        assertFalse(copy2.equalsLogically(original));

        // copy with other project is NOT equal
        WorklogEntryModel copy3 = new WorklogEntryModel(copy);
        copy3.setProject("some other project");
        assertFalse(copy3.equalsLogically(original));

        // copy with other start is NOT equal
        WorklogEntryModel copy4 = new WorklogEntryModel(copy);
        copy4.setStart(new DateTime());
        assertFalse(copy4.equalsLogically(original));

        // copy with other stop is NOT equal
        WorklogEntryModel copy5 = new WorklogEntryModel(copy);
        copy5.setStop(copy5.getStop().minusMinutes(30));
        assertFalse(copy5.equalsLogically(original));

        // copy with other duration is NOT equal
        WorklogEntryModel copy6 = new WorklogEntryModel(copy);
        copy6.setDuration(123);
        assertFalse(copy6.equalsLogically(original));
    }
}