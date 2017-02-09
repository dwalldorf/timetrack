package com.dwalldorf.timetrack.backend.rest.controller;

import com.dwalldorf.timetrack.model.WorklogEntryModel;
import com.dwalldorf.timetrack.backend.rest.dto.ListDto;
import com.dwalldorf.timetrack.backend.service.WorklogService;
import java.util.List;
import javax.inject.Inject;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(WorklogController.BASE_URI)
public class WorklogController {

    public static final String BASE_URI = "/worklog";

    private final WorklogService worklogService;

    @Inject
    public WorklogController(WorklogService worklogService) {
        this.worklogService = worklogService;
    }

    @GetMapping
    public ListDto<WorklogEntryModel> getWorklog() {
        List<WorklogEntryModel> worklogEntries = worklogService.findAll();
        return new ListDto<>(worklogEntries);
    }
}