package com.dwalldorf.timetrack.repository.dao;

import static org.mockito.Mockito.*;

import com.dwalldorf.timetrack.repository.repository.WorklogRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class WorklogEntryDaoTest {

    private WorklogRepository worklogRepository;

    private WorklogEntryDao worklogEntryDao;

    @Before
    public void setUp() throws Exception {
        this.worklogRepository = mock(WorklogRepository.class);
        this.worklogEntryDao = new WorklogEntryDao(worklogRepository);
    }

    @Test
    public void toDocument_WithNull() {

    }

    @Test
    public void toDocument() {

    }

    @Test
    public void toDocumentList() {

    }

    @Test
    public void toModel_WithNull() {

    }

    @Test
    public void toModel() {

    }

    @Test
    public void toModelList() {

    }
}