package com.dwalldorf.timetrack.model.stub;

import static org.joda.time.DateTimeConstants.SATURDAY;
import static org.joda.time.DateTimeConstants.SUNDAY;

import com.dwalldorf.timetrack.model.WorklogEntryModel;
import com.dwalldorf.timetrack.model.util.RandomUtil;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;
import org.joda.time.DateTime;
import org.joda.time.Days;
import org.springframework.stereotype.Component;

@Component
public class WorklogStub {

    private final RandomUtil randomUtil;

    @Inject
    public WorklogStub(RandomUtil randomUtil) {
        this.randomUtil = randomUtil;
    }


    public WorklogEntryModel createWorklogEntry(String userId) {
        return createWorklogEntry(null, userId, null, null);
    }

    public WorklogEntryModel createWorklogEntry(String id, String userId, String customer, String project) {
        final int startDayMinus = randomUtil.randomInt(31);
        final int minutes = randomUtil.randomInt((7 * 60), (9 * 60));

        final DateTime start = new DateTime()
                .minusDays(startDayMinus)
                .withHourOfDay(9);
        final DateTime stop = new DateTime(start)
                .plusMinutes(minutes);

        return new WorklogEntryModel()
                .setId(id)
                .setUserId(userId)
                .setCustomer(customer)
                .setProject(project)
                .setStart(start)
                .setStop(stop)
                .setDuration(minutes);
    }

    public List<WorklogEntryModel> createWorklogEntrySeries(String userId, DateTime after, int entries) {
        ArrayList<WorklogEntryModel> retVal = new ArrayList<>();
        int days = Days.daysBetween(after, new DateTime()).getDays();

        String customer = "test_" + randomUtil.randomString(10);
        String project = randomUtil.randomString(20);

        for (; days > 0; days--) {
            if (retVal.size() == entries) {
                break;
            }

            DateTime start = new DateTime()
                    .minusDays(days)
                    .withHourOfDay(randomUtil.randomInt(7, 11));
            DateTime stop = new DateTime(start)
                    .plusHours(randomUtil.randomInt(7, 9));

            // skip weekends
            if (start.getDayOfWeek() == SATURDAY || start.getDayOfWeek() == SUNDAY) {
                continue;
            }

            // don't create entries in the future
            if (Days.daysBetween(start, new DateTime()).getDays() <= 0) {
                break;
            }

            WorklogEntryModel entry = createWorklogEntry(userId)
                    .setCustomer(customer)
                    .setProject(project)
                    .setStart(start)
                    .setStop(stop);
            retVal.add(entry);
        }

        return retVal;
    }
}