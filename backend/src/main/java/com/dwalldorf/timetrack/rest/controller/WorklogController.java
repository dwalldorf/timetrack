package com.dwalldorf.timetrack.rest.controller;

import com.dwalldorf.timetrack.document.WorklogEntry;
import com.dwalldorf.timetrack.rest.dto.ListDto;
import com.dwalldorf.timetrack.rest.dto.WorklogEntryDto;
import com.dwalldorf.timetrack.service.WorklogService;
import java.util.ArrayList;
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
    public ListDto<WorklogEntryDto> getWorklog() {
        List<WorklogEntry> worklogEntries = worklogService.findAll();
        return toDtoList(worklogEntries);
    }

    private ListDto<WorklogEntryDto> toDtoList(List<WorklogEntry> documents) {
        ArrayList<WorklogEntryDto> dtos = new ArrayList<>();
        documents.forEach(document -> {
            dtos.add(new WorklogEntryDto(document));
        });

        return new ListDto<>(dtos);
    }
}
