package com.dwalldorf.timetrack.backend.service;

import com.dwalldorf.timetrack.backend.exception.IdentityConflictException;
import com.dwalldorf.timetrack.model.GraphMap;
import com.dwalldorf.timetrack.model.GraphMapList;
import com.dwalldorf.timetrack.model.UserModel;
import com.dwalldorf.timetrack.model.WorklogEntryModel;
import com.dwalldorf.timetrack.model.internal.GraphConfig;
import com.dwalldorf.timetrack.repository.dao.WorklogEntryDao;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.function.Function;
import javax.inject.Inject;
import org.joda.time.DateTime;
import org.joda.time.Minutes;
import org.joda.time.format.DateTimeFormatter;
import org.springframework.stereotype.Service;

@Service
public class WorklogService {

    private final static DecimalFormat DOUBLE_DIGIT_FORMAT = new DecimalFormat("00");

    private final WorklogEntryDao worklogEntryDao;

    private final DateTimeFormatter graphDateTimeFormatter;

    @Inject
    public WorklogService(WorklogEntryDao worklogEntryDao, DateTimeFormatter graphDateTimeFormatter) {
        this.worklogEntryDao = worklogEntryDao;
        this.graphDateTimeFormatter = graphDateTimeFormatter;
    }

    public List<WorklogEntryModel> diffWithDatabase(List<WorklogEntryModel> worklogEntries, UserModel user) {
        ArrayList<WorklogEntryModel> retVal = new ArrayList<>();

        List<WorklogEntryModel> dbEntries = worklogEntryDao.findByUser(user);
        worklogEntries.forEach(entry -> {
            boolean found = false;
            for (WorklogEntryModel dbEntry : dbEntries) {
                if (dbEntry.equalsLogically(entry)) {
                    found = true;
                    break;
                }
            }

            if (!found) {
                retVal.add(entry);
            }
        });
        return retVal;
    }

    public List<WorklogEntryModel> save(List<WorklogEntryModel> worklogEntries) {
        return worklogEntryDao.save(worklogEntries);
    }

    /**
     * Sets userId of <code>entry</code> if <code>user</code> is not null.
     * Calculates duration between start and stop and updates <code>entry</code> if start and stop are not null.
     *
     * @param entry the entry to save
     * @param user  the user to assign <code>entry</code> to
     * @return updated {@link WorklogEntryModel}
     */
    public WorklogEntryModel save(WorklogEntryModel entry, UserModel user) {
        entry.setUserId(user.getId());

        if (entry.getStart() != null && entry.getStop() != null) {
            Minutes minutes = Minutes.minutesBetween(entry.getStart(), entry.getStop());
            entry.setDuration(minutes.getMinutes());
        }

        return worklogEntryDao.save(entry);
    }

    public List<WorklogEntryModel> findByUser(UserModel user) {
        return worklogEntryDao.findByUser(user);
    }

    public WorklogEntryModel findById(String id) {
        return worklogEntryDao.findById(id);
    }

    public void delete(WorklogEntryModel entry) {
        worklogEntryDao.delete(entry);
    }

    public List<String> getUserCustomers(UserModel user, String search) {
        return worklogEntryDao.getUserCustomers(user, search);
    }

    public List<String> getUserProjects(UserModel user, String searchStr) {
        return worklogEntryDao.getUserProjects(user, searchStr);
    }

    public void assureIdentity(WorklogEntryModel entry, UserModel user) throws IdentityConflictException {
        if (!entry.getUserId().equals(user.getId())) {
            throw new IdentityConflictException(
                    "User with id '%s' tried to modify worklog entry with id '%s' which belongs to a different user",
                    user.getId(),
                    entry.getId()
            );
        }
    }

    public GraphMapList getGraphMapList(UserModel user, GraphConfig graphConfig) {
        List<WorklogEntryModel> entries = worklogEntryDao.findByGraphConfig(user, graphConfig);
        Function<WorklogEntryModel, String> labelFunction;

        switch (graphConfig.getScale()) {
            case DAY:
                labelFunction = model -> graphDateTimeFormatter.print(model.getStart());
                return getDailyGraphMapList(labelFunction, entries);
            case WEEK:
                labelFunction = model -> {
                    DateTime start = model.getStart();
                    return String.format(
                            "%s-%s",
                            start.getWeekyear(),
                            DOUBLE_DIGIT_FORMAT.format(start.getWeekOfWeekyear())
                    );
                };
                return getGroupedGraphMapList(labelFunction, entries);
            case MONTH:
                labelFunction = model -> String.format(
                        "%s-%s",
                        model.getStart().getYear(),
                        DOUBLE_DIGIT_FORMAT.format(model.getStart().getMonthOfYear())
                );
                return getGroupedGraphMapList(labelFunction, entries);
            default:
                return null;
        }
    }

    private GraphMapList getDailyGraphMapList(Function<WorklogEntryModel, String> dateLabelFunction,
                                              List<WorklogEntryModel> entries) {
        GraphMapList result = new GraphMapList();
        entries.forEach(e -> {
            String dateLabel = dateLabelFunction.apply(e);

            GraphMap graphMap = getGraphMap(dateLabel, e.getDuration());
            graphMap.set("customer", e.getCustomer())
                    .set("project", e.getProject())
                    .set("comment", e.getComment());

            result.add(graphMap);
        });
        return result;
    }

    private GraphMapList getGroupedGraphMapList(Function<WorklogEntryModel, String> dateLabelFunction,
                                                List<WorklogEntryModel> entries) {

        Map<String, Integer> groupedDuration = new TreeMap<>();
        entries.forEach(e -> {
            String currentWeekYear = dateLabelFunction.apply(e);

            Integer currentDuration = groupedDuration.get(currentWeekYear);
            if (currentDuration == null) {
                currentDuration = 0;
            }
            currentDuration += e.getDuration();
            groupedDuration.put(currentWeekYear, currentDuration);
        });

        GraphMapList result = new GraphMapList();
        groupedDuration.forEach((k, v) -> result.add(getGraphMap(k, v)));

        return result;
    }

    private GraphMap getGraphMap(String dateLabel, Integer value) {
        return new GraphMap()
                .set("date", dateLabel)
                .set("duration", value);
    }
}