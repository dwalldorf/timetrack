package com.dwalldorf.timetrack.backend.rest.controller;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import org.junit.Test;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

public class CsvUploadControllerIT extends BaseControllerIT {

    @Test
    public void testUploadCsvFile_NotLoggedIn() throws Exception {
        MultipartFile dataCsv = getDataCsv();

        doMultipartFilePost(CsvUploadController.BASE_URI, getDataCsv())
                .andExpect(status().isUnauthorized());
    }

    private MultipartFile getDataCsv() throws IOException {
        URL resource = getClass().getClassLoader().getResource("data.csv");
        FileInputStream fileInputStream = new FileInputStream(resource.getPath());
        return new MockMultipartFile("file", fileInputStream);
    }
}