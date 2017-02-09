package com.dwalldorf.timetrack.repository.backend.rest.controller;

import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Value;

public class VersionControllerIT extends BaseControllerIT {

    @Value("${app.version}")
    private String version;

    @Test
    public void testGetVersion_Success() throws Exception {
        doGet("/version")
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.version", is(version)));
    }
}