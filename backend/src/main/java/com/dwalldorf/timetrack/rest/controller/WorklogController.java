package com.dwalldorf.timetrack.rest.controller;

import com.dwalldorf.timetrack.document.WorklogEntry;
import com.dwalldorf.timetrack.service.WorklogService;
import java.util.List;
import javax.inject.Inject;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WorklogController {

    private final WorklogService worklogService;

    @Inject
    public WorklogController(WorklogService worklogService) {
        this.worklogService = worklogService;
    }

    @GetMapping("/worklog")
    public List<WorklogEntry> getWorklog() {
        return worklogService.findAll();
    }
}
