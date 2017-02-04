package com.dwalldorf.timetrack.service;

import com.dwalldorf.timetrack.document.WorklogEntry;
import com.dwalldorf.timetrack.repository.WorklogRepository;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;
import org.springframework.stereotype.Service;

@Service
public class WorklogService {

    private final WorklogRepository worklogRepository;

    @Inject
    public WorklogService(WorklogRepository worklogRepository) {
        this.worklogRepository = worklogRepository;
    }

    public List<WorklogEntry> diffWithDatabase(List<WorklogEntry> worklogEntries) {
        ArrayList<WorklogEntry> retVal = new ArrayList<>();

        List<WorklogEntry> dbEntries = findAll();
        worklogEntries.forEach(entry -> {
            boolean found = false;
            for (WorklogEntry dbEntry : dbEntries) {
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

    public List<WorklogEntry> save(List<WorklogEntry> worklogEntries) {
        return worklogRepository.save(worklogEntries);
    }

    public List<WorklogEntry> findAll() {
        return worklogRepository.findAll();
    }
}
