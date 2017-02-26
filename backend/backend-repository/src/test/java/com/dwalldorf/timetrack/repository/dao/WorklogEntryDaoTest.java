package com.dwalldorf.timetrack.repository.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.*;

import com.dwalldorf.timetrack.model.WorklogEntryModel;
import com.dwalldorf.timetrack.repository.document.WorklogEntryDocument;
import com.dwalldorf.timetrack.repository.repository.WorklogRepository;
import java.util.Arrays;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class WorklogEntryDaoTest {

    private static final String id = "someId";
    private static final String userId = "someUserId";
    private static final String customer = "customer";
    private static final String project = "project";
    private static final String task = "task";

    private WorklogEntryDao worklogEntryDao;

    @Before
    public void setUp() throws Exception {
        WorklogRepository worklogRepository = mock(WorklogRepository.class);
        this.worklogEntryDao = new WorklogEntryDao(worklogRepository);
    }

    @Test
    public void toDocument_WithNull() {
        WorklogEntryDocument document = worklogEntryDao.toDocument(null);
        assertNull(document);
    }

    @Test
    public void toDocument() {
        WorklogEntryDocument document = worklogEntryDao.toDocument(getModel());

        assertEquals(id, document.getId());
        assertEquals(userId, document.getUserId());
        assertEquals(customer, document.getCustomer());
        assertEquals(project, document.getProject());
        assertEquals(task, document.getTask());
    }

    @Test
    public void toDocumentList_WithNull() {
        List<WorklogEntryModel> models = Arrays.asList(null, null, getModel());
        List<WorklogEntryDocument> documents = worklogEntryDao.toDocumentList(models);

        assertEquals(1, documents.size());
    }

    @Test
    public void toDocumentList() {
        List<WorklogEntryModel> models = Arrays.asList(getModel(), new WorklogEntryModel(), new WorklogEntryModel());
        List<WorklogEntryDocument> documents = worklogEntryDao.toDocumentList(models);

        assertEquals(models.size(), documents.size());

        WorklogEntryDocument document1 = documents.get(0);
        assertEquals(id, document1.getId());
        assertEquals(userId, document1.getUserId());
        assertEquals(customer, document1.getCustomer());
        assertEquals(project, document1.getProject());
        assertEquals(task, document1.getTask());

        assertNull(documents.get(1).getId());
        assertNull(documents.get(2).getId());
    }

    @Test
    public void toModel_WithNull() {
        WorklogEntryModel model = worklogEntryDao.toModel(null);
        assertNull(model);
    }

    @Test
    public void toModel() {
        WorklogEntryModel model = worklogEntryDao.toModel(getDocument());

        assertEquals(id, model.getId());
        assertEquals(userId, model.getUserId());
        assertEquals(customer, model.getCustomer());
        assertEquals(project, model.getProject());
        assertEquals(task, model.getTask());
    }

    @Test
    public void toModelList_WithNull() {
        List<WorklogEntryDocument> documents = Arrays.asList(getDocument(), null, null);
        List<WorklogEntryModel> models = worklogEntryDao.toModelList(documents);

        assertEquals(1, models.size());
        assertEquals(id, models.get(0).getId());
        assertEquals(userId, models.get(0).getUserId());
    }

    @Test
    public void toModelList() {
        List<WorklogEntryDocument> documents = Arrays.asList(getDocument(), new WorklogEntryDocument());
        List<WorklogEntryModel> models = worklogEntryDao.toModelList(documents);

        assertEquals(documents.size(), models.size());

        WorklogEntryModel model1 = models.get(0);
        assertEquals(id, model1.getId());
        assertEquals(userId, model1.getUserId());
    }

    private WorklogEntryModel getModel() {
        return new WorklogEntryModel()
                .setId(id)
                .setUserId(userId)
                .setCustomer(customer)
                .setProject(project)
                .setTask(task);
    }

    private WorklogEntryDocument getDocument() {
        return new WorklogEntryDocument()
                .setId(id)
                .setUserId(userId)
                .setCustomer(customer)
                .setProject(project)
                .setTask(task);
    }
}