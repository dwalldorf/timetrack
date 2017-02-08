package com.dwalldorf.timetrack.model.stub;

import com.dwalldorf.timetrack.model.util.RandomUtil;
import com.dwalldorf.timetrack.model.WorklogEntryModel;
import javax.inject.Inject;
import org.joda.time.DateTime;
import org.springframework.stereotype.Component;

@Component
public class WorklogStub {

    private final RandomUtil randomUtil;

    @Inject
    public WorklogStub(RandomUtil randomUtil) {
        this.randomUtil = randomUtil;
    }

    public WorklogEntryModel createWorklogEntry(String customer, String project) {
        return createWorklogEntry(customer, project, null);
    }

    public WorklogEntryModel createWorklogEntry(String customer, String project, String id) {
        final int startDayMinus = randomUtil.randomInt(31);
        final int minutes = randomUtil.randomInt((7 * 60), (9 * 60));

        final DateTime start = new DateTime()
                .minusDays(startDayMinus)
                .withHourOfDay(9);
        final DateTime stop = new DateTime(start)
                .plusMinutes(minutes);

        return new WorklogEntryModel()
                .setId(id)
                .setCustomer(customer)
                .setProject(project)
                .setStart(start)
                .setStop(stop)
                .setDuration(minutes);
    }
}