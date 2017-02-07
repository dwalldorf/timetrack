package com.dwalldorf.timetrack.stub;

import com.dwalldorf.timetrack.document.WorklogEntry;
import com.dwalldorf.timetrack.util.RandomUtil;
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

    public WorklogEntry createWorklogEntry(String customer, String project) {
        return createWorklogEntry(customer, project, null);
    }

    public WorklogEntry createWorklogEntry(String customer, String project, String id) {
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
                .setDuration(minutes);
    }
}