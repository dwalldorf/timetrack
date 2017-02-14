package com.dwalldorf.timetrack.backend.rest.controller;

import com.dwalldorf.timetrack.backend.annotation.RequireLogin;
import com.dwalldorf.timetrack.backend.rest.dto.ListDto;
import com.dwalldorf.timetrack.backend.service.GraphService;
import com.dwalldorf.timetrack.backend.service.UserService;
import com.dwalldorf.timetrack.backend.service.WorklogService;
import com.dwalldorf.timetrack.model.GraphData;
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
public class WorklogController {

    public static final String BASE_URI = "/worklog";

    private final GraphService graphService;

    private final WorklogService worklogService;

    private final UserService userService;

    @Inject
    public WorklogController(GraphService graphService, WorklogService worklogService, UserService userService) {
        this.graphService = graphService;
        this.worklogService = worklogService;
        this.userService = userService;
    }

    @GetMapping
    public ListDto<WorklogEntryModel> getWorklog() {
        List<WorklogEntryModel> worklogEntries = worklogService.findAll();
        return new ListDto<>(worklogEntries);
    }

    @RequireLogin
    @GetMapping("/graph_data")
    public GraphData getGraphData(
            @RequestParam(value = "from") String from,
            @RequestParam(value = "to") String to,
            @RequestParam(value = "scale", required = false, defaultValue = "day") String scale) {

        UserModel currentUser = userService.getCurrentUser();
        GraphConfig graphConfig = graphService.fromParameters(from, to, scale);
        return worklogService.getGraphData(currentUser, graphConfig);
    }
}