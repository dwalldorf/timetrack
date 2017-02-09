package com.dwalldorf.timetrack.repository.backend.rest.controller;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.dwalldorf.timetrack.backend.rest.controller.WorklogController;
import com.dwalldorf.timetrack.model.WorklogEntryModel;
import com.dwalldorf.timetrack.backend.service.WorklogService;
import java.util.Collections;
import org.joda.time.DateTime;
import org.junit.Test;
import org.springframework.boot.test.mock.mockito.MockBean;

public class WorklogControllerIT extends BaseControllerIT {

    @MockBean
    private WorklogService worklogServiceMock;

    @Test
    public void testGetWorklog_Returns200() throws Exception {
        doGet(WorklogController.BASE_URI)
                .andExpect(status().isOk());
    }

    @Test
    public void testGetWorklog_ReturnsList() throws Exception {
        WorklogEntryModel worklogEntry = new WorklogEntryModel()
                .setId("abc001")
                .setCustomer("customer")
                .setProject("project")
                .setStart(new DateTime().minusHours(8))
                .setStop(new DateTime())
                .setDuration((8 * 60));
        when(worklogServiceMock.findAll()).thenReturn(Collections.singletonList(worklogEntry));

        doGet(WorklogController.BASE_URI)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.count", is(1)))
                .andExpect(jsonPath("$.items[0].id", is(worklogEntry.getId())))
                .andExpect(jsonPath("$.items[0].customer", is(worklogEntry.getCustomer())))
                .andExpect(jsonPath("$.items[0].project", is(worklogEntry.getProject())))
                .andExpect(jsonPath("$.items[0].duration", is(worklogEntry.getDuration())));
    }
}