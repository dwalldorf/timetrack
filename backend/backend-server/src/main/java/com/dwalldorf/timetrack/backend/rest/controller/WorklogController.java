package com.dwalldorf.timetrack.backend.rest.controller;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

import com.dwalldorf.timetrack.backend.annotation.RequireLogin;
import com.dwalldorf.timetrack.backend.exception.NotFoundException;
import com.dwalldorf.timetrack.backend.rest.dto.ListDto;
import com.dwalldorf.timetrack.backend.service.GraphService;
import com.dwalldorf.timetrack.backend.service.WorklogService;
import com.dwalldorf.timetrack.model.GraphMapList;
import com.dwalldorf.timetrack.model.UserModel;
import com.dwalldorf.timetrack.model.WorklogEntryModel;
import com.dwalldorf.timetrack.model.internal.GraphConfig;
import java.util.List;
import javax.inject.Inject;
import javax.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(WorklogController.BASE_URI)
public class WorklogController extends BaseController {

    static final String BASE_URI = "/worklogs";
    static final String CUSTOMERS_URI = "/customers";
    static final String PROJECTS_URI = "/projects";
    static final String GRAPH_URI = "/graph_data";

    private final GraphService graphService;

    private final WorklogService worklogService;

    @Inject
    public WorklogController(
            GraphService graphService,
            WorklogService worklogService) {
        this.graphService = graphService;
        this.worklogService = worklogService;
    }

    @RequireLogin
    @PostMapping
    public ResponseEntity<WorklogEntryModel> createWorklogEntry(@RequestBody @Valid WorklogEntryModel entry) {
        entry = worklogService.save(entry, getCurrentUser());
        return new ResponseEntity<>(entry, CREATED);
    }

    @RequireLogin
    @PutMapping("/{id}")
    public ResponseEntity<WorklogEntryModel> updateWorklogEntry(
            @PathVariable String id,
            @RequestBody @Valid WorklogEntryModel entry) {

        UserModel user = getCurrentUser();
        WorklogEntryModel persistedEntry = getEntryOrFail(id);

        worklogService.assureIdentity(persistedEntry, user);

        entry = worklogService.save(entry, user);
        return new ResponseEntity<>(entry, OK);
    }

    @RequireLogin
    @GetMapping
    public ListDto<WorklogEntryModel> getWorklog() {
        List<WorklogEntryModel> worklogEntries = worklogService.findByUser(getCurrentUser());
        return new ListDto<>(worklogEntries);
    }

    @RequireLogin
    @GetMapping("/{id}")
    public WorklogEntryModel getWorklogEntry(@PathVariable String id) {
        return worklogService.findById(id);
    }

    @RequireLogin
    @DeleteMapping("/{id}")
    public ResponseEntity deleteWorklogEntry(@PathVariable String id) {
        UserModel user = getCurrentUser();
        WorklogEntryModel entry = getEntryOrFail(id);
        worklogService.assureIdentity(entry, user);

        worklogService.delete(entry);
        return new ResponseEntity(OK);
    }

    @RequireLogin
    @GetMapping(CUSTOMERS_URI)
    public List<String> getUserCustomers(@RequestParam(name = "search", required = false) String search) {
        return worklogService.getUserCustomers(getCurrentUser(), search);
    }

    @RequireLogin
    @GetMapping(PROJECTS_URI)
    public List<String> getUserProjects(@RequestParam(name = "search", required = false) String search) {
        return worklogService.getUserProjects(getCurrentUser(), search);
    }

    @RequireLogin
    @GetMapping(GRAPH_URI)
    public GraphMapList getGraphData(
            @RequestParam(value = "from") String from,
            @RequestParam(value = "to") String to,
            @RequestParam(value = "scale", required = false, defaultValue = "day") String scale) {

        GraphConfig graphConfig = graphService.fromParameters(from, to, scale);
        return worklogService.getGraphMapList(getCurrentUser(), graphConfig);
    }

    private WorklogEntryModel getEntryOrFail(final String id) {
        WorklogEntryModel persistedEntry = worklogService.findById(id);

        if (persistedEntry == null) {
            throw new NotFoundException(String.format("Worklog entry with id '%s' does not exist", id));
        }
        return persistedEntry;
    }
}