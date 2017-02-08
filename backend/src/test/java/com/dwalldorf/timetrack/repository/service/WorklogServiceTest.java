package com.dwalldorf.timetrack.repository.service;

import static org.junit.Assert.assertEquals;

import com.dwalldorf.timetrack.repository.backend.BaseTest;
import com.dwalldorf.timetrack.repository.dao.WorklogEntryDao;
import com.dwalldorf.timetrack.model.util.RandomUtil;
import com.dwalldorf.timetrack.model.WorklogEntryModel;
import com.dwalldorf.timetrack.model.stub.WorklogStub;
import com.dwalldorf.timetrack.repository.WorklogRepository;
import com.dwalldorf.timetrack.backend.service.WorklogService;
import java.util.Arrays;
import java.util.List;
import org.joda.time.DateTime;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

public class WorklogServiceTest extends BaseTest {

    private final WorklogStub worklogStub;

    @Mock
    private WorklogRepository worklogRepository;

    @Mock
    private WorklogEntryDao worklogEntryDao;

    private WorklogService service;

    public WorklogServiceTest() throws Exception {
        worklogStub = new WorklogStub(new RandomUtil());
    }

    @Override
    protected void setUp() {
        this.service = new WorklogService(worklogRepository, worklogEntryDao);
    }

    @Test
    public void testDiffWithDatabase_FiltersExistingEntries() throws Exception {
        // prepare
        final String customer = "testCustomer";
        final String project = "testProject";

        final WorklogEntryModel dbEntry1 = worklogStub.createWorklogEntry(customer, project, "abc001");
        final WorklogEntryModel dbEntry2 = worklogStub.createWorklogEntry(customer, project, "abc002");
        final WorklogEntryModel dbEntry3 = worklogStub.createWorklogEntry(customer, project, "abc003");

        // duplicate entry
        final WorklogEntryModel newEntry1_Duplicate = new WorklogEntryModel(dbEntry1)
                .setId(null)
                .setComment("Should be filtered");

        // new entry
        final WorklogEntryModel newEntry2_New = new WorklogEntryModel()
                .setCustomer(customer)
                .setProject(project)
                .setComment("Should not be filtered");

        // duplicate of dbEntry2 but with modified stop - should be treated as new entry
        WorklogEntryModel newEntry3_New = new WorklogEntryModel(dbEntry2);
        newEntry3_New.setId(null)
                     .setStart(new DateTime(newEntry3_New.getStop()).minusMinutes(5));

        final List<WorklogEntryModel> mockDbEntries = Arrays.asList(dbEntry1, dbEntry2, dbEntry3);
        final List<WorklogEntryModel> newEntries = Arrays.asList(newEntry1_Duplicate, newEntry2_New, newEntry3_New);

        // when
        Mockito.when(worklogEntryDao.findAll()).thenReturn(mockDbEntries);
        List<WorklogEntryModel> diffedWorklogEntries = service.diffWithDatabase(newEntries);

        //then
        assertEquals(2, diffedWorklogEntries.size());
        assertEquals(newEntry2_New.getComment(), diffedWorklogEntries.get(0).getComment());
        assertEquals(newEntry3_New.getComment(), diffedWorklogEntries.get(1).getComment());
    }
}