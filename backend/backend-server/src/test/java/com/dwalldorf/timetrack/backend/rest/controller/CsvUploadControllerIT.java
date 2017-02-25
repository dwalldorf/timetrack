package com.dwalldorf.timetrack.backend.rest.controller;

import static org.junit.Assert.assertEquals;
import static org.mockito.AdditionalAnswers.returnsFirstArg;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.dwalldorf.timetrack.backend.service.WorklogService;
import com.dwalldorf.timetrack.model.WorklogEntryModel;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

public class CsvUploadControllerIT extends BaseControllerIT {

    @MockBean
    private WorklogService worklogService;

    @Captor
    private ArgumentCaptor<List<WorklogEntryModel>> worklogEntryModelCaptor;

    @Test
    public void testUploadCsvFile_NotLoggedIn() throws Exception {
        doMultipartFilePost(CsvUploadController.BASE_URI, getDataCsv())
                .andExpect(status().isUnauthorized());
    }

    @Test
    public void testUploadCsvFile_Success() throws Exception {
        mockLoggedIn();
        when(worklogService.diffWithDatabase(any())).then(returnsFirstArg());

        doMultipartFilePost(CsvUploadController.BASE_URI, getDataCsv())
                .andExpect(status().isCreated());

        verify(worklogService).diffWithDatabase(anyListOf(WorklogEntryModel.class));
        verify(worklogService).save(worklogEntryModelCaptor.capture());

        List<WorklogEntryModel> savedEntries = worklogEntryModelCaptor.getValue();
        assertEquals(36, savedEntries.size());
    }

    private MultipartFile getDataCsv() throws IOException {
        URL resource = getClass().getClassLoader().getResource("data.csv");
        FileInputStream fileInputStream = new FileInputStream(resource.getPath());
        return new MockMultipartFile("file", fileInputStream);
    }
}