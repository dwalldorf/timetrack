package com.dwalldorf.timetrack.backend.service;

import com.dwalldorf.timetrack.model.WorklogEntryModel;
import com.dwalldorf.timetrack.repository.dao.WorklogEntryDao;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;
import org.springframework.stereotype.Service;

@Service
public class WorklogService {

    private final WorklogEntryDao worklogEntryDao;

    @Inject
    public WorklogService(WorklogEntryDao worklogEntryDao) {
        this.worklogEntryDao = worklogEntryDao;
    }

    public List<WorklogEntryModel> diffWithDatabase(List<WorklogEntryModel> worklogEntries) {
        ArrayList<WorklogEntryModel> retVal = new ArrayList<>();

        List<WorklogEntryModel> dbEntries = findAll();
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

    public List<WorklogEntryModel> findAll() {
        return worklogEntryDao.findAll();
    }
}