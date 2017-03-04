package com.dwalldorf.timetrack.backend.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.*;

import com.dwalldorf.timetrack.backend.BaseTest;
import com.dwalldorf.timetrack.backend.exception.IdentityConflictException;
import com.dwalldorf.timetrack.model.GraphMapList;
import com.dwalldorf.timetrack.model.UserModel;
import com.dwalldorf.timetrack.model.WorklogEntryModel;
import com.dwalldorf.timetrack.model.internal.GraphConfig;
import com.dwalldorf.timetrack.model.stub.UserStub;
import com.dwalldorf.timetrack.model.stub.WorklogStub;
import com.dwalldorf.timetrack.model.util.RandomUtil;
import com.dwalldorf.timetrack.repository.dao.WorklogEntryDao;
import java.util.Arrays;
import java.util.List;
import org.joda.time.DateTime;
import org.joda.time.Minutes;
import org.joda.time.Months;
import org.joda.time.Weeks;
import org.joda.time.format.DateTimeFormat;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

public class WorklogServiceTest extends BaseTest {

    private final WorklogStub worklogStub;

    private final UserStub userStub;

    private static final DateTime FROM_DATE = new DateTime().minusMonths(2);
    private static final DateTime TO_DATE = new DateTime();

    @Mock
    private WorklogEntryDao worklogEntryDao;

    private WorklogService service;

    public WorklogServiceTest() throws Exception {
        final RandomUtil randomUtil = new RandomUtil();

        worklogStub = new WorklogStub(randomUtil);
        userStub = new UserStub(randomUtil);
    }

    @Override
    protected void setUp() {
        this.service = new WorklogService(worklogEntryDao, DateTimeFormat.forPattern("yyyy-MM-dd"));
    }

    @Test
    public void testDiffWithDatabase_FiltersExistingEntries() throws Exception {
        UserModel mockUser = userStub.createUser();

        final String customer = "testCustomer";
        final String project = "testProject";

        final WorklogEntryModel dbEntry1 = worklogStub.createWorklogEntry("abc001", null, customer, project);
        final WorklogEntryModel dbEntry2 = worklogStub.createWorklogEntry("abc002", null, customer, project);
        final WorklogEntryModel dbEntry3 = worklogStub.createWorklogEntry("abc0023", null, customer, project);

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
        Mockito.when(worklogEntryDao.findByUser(eq(mockUser))).thenReturn(mockDbEntries);
        List<WorklogEntryModel> diffedWorklogEntries = service.diffWithDatabase(newEntries, mockUser);

        //then
        assertEquals(2, diffedWorklogEntries.size());
        assertEquals(newEntry2_New.getComment(), diffedWorklogEntries.get(0).getComment());
        assertEquals(newEntry3_New.getComment(), diffedWorklogEntries.get(1).getComment());
    }

    @Test
    public void testSaveList() throws Exception {
        List<WorklogEntryModel> list = Arrays.asList(
                worklogStub.createWorklogEntry(),
                worklogStub.createWorklogEntry()
        );
        service.save(list);

        verify(worklogEntryDao).save(eq(list));
    }

    @Test
    public void testSaveEntry_SetsUserId() {
        UserModel mockUser = userStub.createUser();
        WorklogEntryModel mockEntry = mock(WorklogEntryModel.class);

        service.save(mockEntry, mockUser);

        verify(mockEntry).setUserId(eq(mockUser.getId()));
    }

    @Test
    public void testSaveEntry_DoesNotSetDuration_IfStartNull() {
        UserModel mockUser = userStub.createUser();

        WorklogEntryModel mockEntry = mock(WorklogEntryModel.class);
        when(mockEntry.getStart()).thenReturn(null);
        when(mockEntry.getStop()).thenReturn(new DateTime());

        service.save(mockEntry, mockUser);

        verify(mockEntry, never()).setDuration(anyInt());
    }

    @Test
    public void testSaveEntry_DoesNotSetDuration_IfStopNull() {
        UserModel mockUser = userStub.createUser();

        WorklogEntryModel mockEntry = mock(WorklogEntryModel.class);
        when(mockEntry.getStart()).thenReturn(new DateTime());
        when(mockEntry.getStop()).thenReturn(null);

        service.save(mockEntry, mockUser);

        verify(mockEntry, never()).setDuration(anyInt());
    }

    @Test
    public void testSaveEntry_SetsDuration() {
        UserModel mockUser = userStub.createUser();

        DateTime start = new DateTime().minusHours(8);
        DateTime stop = new DateTime();
        int expectedDuration = Minutes.minutesBetween(start, stop).getMinutes();

        WorklogEntryModel mockEntry = mock(WorklogEntryModel.class);
        when(mockEntry.getStart()).thenReturn(start);
        when(mockEntry.getStop()).thenReturn(stop);

        service.save(mockEntry, mockUser);

        verify(mockEntry).setDuration(eq(expectedDuration));
    }

    @Test
    public void testFindByUser() throws Exception {
        UserModel user = userStub.createUser();
        service.findByUser(user);

        verify(worklogEntryDao).findByUser(eq(user));
    }

    @Test
    public void testFindById() throws Exception {
        String id = "someId";
        service.findById(id);

        verify(worklogEntryDao).findById(eq(id));
    }

    @Test
    public void testDelete() throws Exception {
        WorklogEntryModel entry = worklogStub.createWorklogEntry();
        service.delete(entry);

        verify(worklogEntryDao).delete(eq(entry));
    }

    @Test(expected = IdentityConflictException.class)
    public void testAssureIdentity_ThrowsIdentityConflictException() {
        UserModel mockUser = userStub.createUser();
        WorklogEntryModel mockEntry = worklogStub.createWorklogEntry();

        service.assureIdentity(mockEntry, mockUser);
    }

    @Test
    public void testAssureIdentity_Success() {
        UserModel mockUser = userStub.createUser();
        WorklogEntryModel mockEntry = worklogStub.createWorklogEntry(mockUser.getId());

        service.assureIdentity(mockEntry, mockUser);
    }

    @Test
    public void testGetGraphMapList_Day() throws Exception {
        GraphConfig graphConfig = getGraphConfig(GraphConfig.Scale.DAY);
        UserModel mockUser = userStub.createUser();
        List<WorklogEntryModel> mockDaoResult = createWorklogEntrySeries(mockUser);

        when(worklogEntryDao.findByGraphConfig(eq(mockUser), eq(graphConfig)))
                .thenReturn(mockDaoResult);

        GraphMapList graphMapList = service.getGraphMapList(mockUser, graphConfig);

        assertEquals(mockDaoResult.size(), graphMapList.size());
    }

    @Test
    public void testGetGraphMapList_Week() throws Exception {
        GraphConfig graphConfig = getGraphConfig(GraphConfig.Scale.WEEK);
        UserModel mockUser = userStub.createUser();
        List<WorklogEntryModel> mockDaoResult = createWorklogEntrySeries(mockUser);

        when(worklogEntryDao.findByGraphConfig(eq(mockUser), eq(graphConfig)))
                .thenReturn(mockDaoResult);

        GraphMapList graphMapList = service.getGraphMapList(mockUser, graphConfig);

        // it can be the exact amount of weeks between both dates, or +1 because of the way we group things
        int expectedWeeks = Weeks.weeksBetween(FROM_DATE, TO_DATE).getWeeks();
        boolean correctAmountOfWeeks = false;

        if (graphMapList.size() == expectedWeeks || graphMapList.size() == expectedWeeks + 1) {
            correctAmountOfWeeks = true;
        }

        assertTrue(
                String.format("Expected %d or %d weeks", expectedWeeks, expectedWeeks + 1),
                correctAmountOfWeeks
        );
    }

    @Test
    public void testGetGraphMapList_Month() throws Exception {
        GraphConfig graphConfig = getGraphConfig(GraphConfig.Scale.MONTH);
        UserModel mockUser = userStub.createUser();
        List<WorklogEntryModel> mockDaoResult = createWorklogEntrySeries(mockUser);

        when(worklogEntryDao.findByGraphConfig(eq(mockUser), eq(graphConfig)))
                .thenReturn(mockDaoResult);

        GraphMapList graphMapList = service.getGraphMapList(mockUser, graphConfig);

        // it can be the exact amount of months between both dates, or +1 because of the way we group things
        int expectedMonths = Months.monthsBetween(FROM_DATE, TO_DATE).getMonths();
        boolean correctAmountOfMonths = false;

        if (graphMapList.size() == expectedMonths || graphMapList.size() == expectedMonths + 1) {
            correctAmountOfMonths = true;
        }

        assertTrue(
                String.format("Expected %d or %d months", expectedMonths, expectedMonths + 1),
                correctAmountOfMonths
        );
    }

    private GraphConfig getGraphConfig(GraphConfig.Scale scale) {
        return new GraphConfig()
                .setFrom(FROM_DATE)
                .setTo(TO_DATE)
                .setScale(scale);
    }

    private List<WorklogEntryModel> createWorklogEntrySeries(UserModel user) {
        return worklogStub.createWorklogEntrySeries(user.getId(), FROM_DATE, 9999);
    }
}