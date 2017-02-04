package com.dwalldorf.timetrack.service;

import static org.junit.Assert.assertEquals;

import com.dwalldorf.timetrack.BaseTest;
import com.dwalldorf.timetrack.document.WorklogEntry;
import com.dwalldorf.timetrack.repository.WorklogRepository;
import com.dwalldorf.timetrack.util.RandomUtil;
import java.util.Arrays;
import java.util.List;
import org.joda.time.DateTime;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

public class WorklogServiceTest extends BaseTest {

    @Mock
    private WorklogRepository worklogRepository;

    private static final RandomUtil randomUtil = new RandomUtil();

    private WorklogService service;

    @Override
    protected void setUp() {
        this.service = new WorklogService(worklogRepository);
    }

    @Test
    public void testDiffWithDatabase_FiltersExistingEntries() throws Exception {
        // prepare
        final String customer = "testCustomer";
        final String project = "testProject";

        final WorklogEntry dbEntry1 = createWorklogEntry(customer, project, "abc001");
        final WorklogEntry dbEntry2 = createWorklogEntry(customer, project, "abc002");
        final WorklogEntry dbEntry3 = createWorklogEntry(customer, project, "abc003");

        // duplicate entry
        final WorklogEntry newEntry1_Duplicate = new WorklogEntry(dbEntry1)
                .setId(null)
                .setComment("Should be filtered");

        // new entry
        final WorklogEntry newEntry2_New = createWorklogEntry(customer, project)
                .setComment("Should not be filtered");

        // duplicate of dbEntry2 but with modified stop - should be treated as new entry
        WorklogEntry newEntry3_New = new WorklogEntry(dbEntry2);
        newEntry3_New.setId(null)
                     .setStart(new DateTime(newEntry3_New.getStop()).minusMinutes(5))
                     .setMinutes(newEntry3_New.getMinutes() - 5);

        final List<WorklogEntry> mockDbEntries = Arrays.asList(dbEntry1, dbEntry2, dbEntry3);
        final List<WorklogEntry> newEntries = Arrays.asList(newEntry1_Duplicate, newEntry2_New, newEntry3_New);

        // when
        Mockito.when(worklogRepository.findAll()).thenReturn(mockDbEntries);
        List<WorklogEntry> diffedWorklogEntries = service.diffWithDatabase(newEntries);

        //then
        assertEquals(2, diffedWorklogEntries.size());
        assertEquals(newEntry2_New.getComment(), diffedWorklogEntries.get(0).getComment());
        assertEquals(newEntry3_New.getComment(), diffedWorklogEntries.get(1).getComment());
    }

    private WorklogEntry createWorklogEntry(final String customer, final String project) {
        return createWorklogEntry(customer, project, null);
    }

    private WorklogEntry createWorklogEntry(final String customer, final String project, final String id) {
        final int startDayMinus = randomUtil.randomInt(31);
        final int minutes = randomUtil.randomInt((7 * 60), (9 * 60));

        final DateTime start = new DateTime()
                .minusDays(startDayMinus)
                .withHourOfDay(9);
        final DateTime stop = new DateTime(start)
                .plusMinutes(minutes);

        return new WorklogEntry()
                .setId(id)
                .setCustomer(customer)
                .setProject(project)
                .setStart(start)
                .setStop(stop)
                .setMinutes(minutes);
    }
}