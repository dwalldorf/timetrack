package com.dwalldorf.timetrack.backend.rest.controller;

import com.dwalldorf.timetrack.backend.annotation.RequireLogin;
import com.dwalldorf.timetrack.backend.rest.dto.ListDto;
import com.dwalldorf.timetrack.backend.service.GraphService;
import com.dwalldorf.timetrack.backend.service.WorklogService;
import com.dwalldorf.timetrack.model.GraphMapList;
import com.dwalldorf.timetrack.model.UserModel;
import com.dwalldorf.timetrack.model.WorklogEntryModel;
import com.dwalldorf.timetrack.model.internal.GraphConfig;
import java.util.List;
import javax.inject.Inject;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(WorklogController.BASE_URI)
public class WorklogController extends BaseController {

    static final String BASE_URI = "/worklog";

    static final String GRAPH_URI = "/graph_data";

    private final GraphService graphService;

    private final WorklogService worklogService;

    @Inject
    public WorklogController(GraphService graphService, WorklogService worklogService) {
        this.graphService = graphService;
        this.worklogService = worklogService;
    }

    @RequireLogin
    @GetMapping
    public ListDto<WorklogEntryModel> getWorklog() {
        List<WorklogEntryModel> worklogEntries = worklogService.findAll();
        return new ListDto<>(worklogEntries);
    }

    @RequireLogin
    @GetMapping(GRAPH_URI)
    public GraphMapList getGraphData(
            @RequestParam(value = "from") String from,
            @RequestParam(value = "to") String to,
            @RequestParam(value = "scale", required = false, defaultValue = "day") String scale) {

        UserModel currentUser = this.getCurrentUser();
        GraphConfig graphConfig = graphService.fromParameters(from, to, scale);

        return worklogService.getGraphMapList(currentUser, graphConfig);
    }
}