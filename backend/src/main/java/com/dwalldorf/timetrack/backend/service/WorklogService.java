package com.dwalldorf.timetrack.backend.service;

import com.dwalldorf.timetrack.repository.dao.WorklogEntryDao;
import com.dwalldorf.timetrack.model.WorklogEntryModel;
import com.dwalldorf.timetrack.repository.WorklogRepository;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;
import org.springframework.stereotype.Service;

@Service
public class WorklogService {

    private final WorklogRepository worklogRepository;

    private final WorklogEntryDao worklogEntryDao;

    @Inject
    public WorklogService(WorklogRepository worklogRepository, WorklogEntryDao worklogEntryDao) {
        this.worklogRepository = worklogRepository;
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