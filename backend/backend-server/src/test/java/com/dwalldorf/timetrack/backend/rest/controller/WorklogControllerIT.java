package com.dwalldorf.timetrack.backend.rest.controller;

import static com.dwalldorf.timetrack.backend.rest.controller.WorklogController.BASE_URI;
import static com.dwalldorf.timetrack.backend.rest.controller.WorklogController.GRAPH_URI;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.dwalldorf.timetrack.backend.service.WorklogService;
import com.dwalldorf.timetrack.model.UserModel;
import com.dwalldorf.timetrack.model.WorklogEntryModel;
import com.dwalldorf.timetrack.model.internal.GraphConfig;
import java.util.Collections;
import javax.inject.Inject;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormatter;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.boot.test.mock.mockito.MockBean;

public class WorklogControllerIT extends BaseControllerIT {

    private static final String fromStr = "2017-01-01";
    private static final String toStr = "2017-01-31";
    private static final String scale = "week";

    @MockBean
    private WorklogService worklogServiceMock;

    @Inject
    private DateTimeFormatter graphDateTimeFormatter;

    @Test
    public void testGetWorklog_NotLoggedIn() throws Exception {
        doGet(BASE_URI)
                .andExpect(status().isUnauthorized());
    }

    @Test
    public void testGetWorklog_ReturnsList() throws Exception {
        UserModel mockUser = mockLoggedIn();

        WorklogEntryModel worklogEntry = new WorklogEntryModel()
                .setId("abc001")
                .setCustomer("customer")
                .setProject("project")
                .setStart(new DateTime().minusHours(8))
                .setStop(new DateTime())
                .setDuration((8 * 60));
        when(worklogServiceMock.findByUser(eq(mockUser))).thenReturn(Collections.singletonList(worklogEntry));

        doGet(BASE_URI)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.count", is(1)))
                .andExpect(jsonPath("$.items[0].id", is(worklogEntry.getId())))
                .andExpect(jsonPath("$.items[0].customer", is(worklogEntry.getCustomer())))
                .andExpect(jsonPath("$.items[0].project", is(worklogEntry.getProject())))
                .andExpect(jsonPath("$.items[0].duration", is(worklogEntry.getDuration())));
    }

    @Test
    public void testGetGraphData_NotLoggedIn() throws Exception {
        doGet(BASE_URI + GRAPH_URI + getGraphDataQueryParams())
                .andExpect(status().isUnauthorized());
    }

    @Test
    public void testGetGraphData_Success() throws Exception {
        UserModel mockUser = mockLoggedIn();
        ArgumentCaptor<GraphConfig> graphConfigCaptor = ArgumentCaptor.forClass(GraphConfig.class);
        doGet(BASE_URI + GRAPH_URI + getGraphDataQueryParams())
                .andExpect(status().isOk());

        verify(worklogServiceMock).getGraphMapList(eq(mockUser), graphConfigCaptor.capture());

        GraphConfig graphConfig = graphConfigCaptor.getValue();
        assertEquals(fromStr, graphDateTimeFormatter.print(graphConfig.getFrom()));
        assertEquals(toStr, graphDateTimeFormatter.print(graphConfig.getTo()));
        assertEquals(scale, graphConfig.getScale().name().toLowerCase());
    }

    private String getGraphDataQueryParams() {
        return String.format("?from=%s&to=%s&scale=%s",
                fromStr,
                toStr,
                scale
        );
    }
}