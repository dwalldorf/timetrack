package com.dwalldorf.timetrack.backend.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import com.dwalldorf.timetrack.backend.BaseTest;
import com.dwalldorf.timetrack.model.UserModel;
import com.dwalldorf.timetrack.model.WorklogEntryModel;
import com.dwalldorf.timetrack.model.stub.UserStub;
import com.dwalldorf.timetrack.model.util.RandomUtil;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import org.junit.Test;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

public class CsvImportServiceTest extends BaseTest {

    private UserStub userStub;

    private CsvImportService importService;

    @Override
    protected void setUp() {
        this.userStub = new UserStub(new RandomUtil());
        this.importService = new CsvImportService();
    }

    @Test
    public void testGetWorklogEntriesFromCsv() throws Exception {
        MultipartFile mockFile = getDataCsv();
        UserModel mockUser = userStub.createUser();

        String expectedCustomer = "customer";
        String expectedProject = "some project";
        String expectedComment = "first entry";

        List<WorklogEntryModel> worklogEntriesFromCsv = importService.getWorklogEntriesFromCsv(mockFile, mockUser);

        assertEquals(36, worklogEntriesFromCsv.size());

        WorklogEntryModel entry1 = worklogEntriesFromCsv.get(0);
        assertEquals(expectedCustomer, entry1.getCustomer());
        assertEquals(expectedProject, entry1.getProject());
        assertNotNull(entry1.getStart());
        assertNotNull(entry1.getStop());
        assertEquals(expectedComment, entry1.getComment());
    }

    private MultipartFile getDataCsv() throws IOException {
        URL resource = getClass().getClassLoader().getResource("data.csv");
        FileInputStream fileInputStream = new FileInputStream(resource.getPath());
        return new MockMultipartFile("file", fileInputStream);
    }
}