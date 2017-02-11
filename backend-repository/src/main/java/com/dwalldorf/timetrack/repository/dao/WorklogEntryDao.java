package com.dwalldorf.timetrack.repository.dao;

import com.dwalldorf.timetrack.model.WorklogEntryModel;
import com.dwalldorf.timetrack.repository.document.WorklogEntryDocument;
import com.dwalldorf.timetrack.repository.repository.WorklogRepository;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import javax.inject.Inject;
import org.springframework.stereotype.Component;

@Component
public class WorklogEntryDao {

    private final WorklogRepository worklogRepository;

    @Inject
    public WorklogEntryDao(WorklogRepository worklogRepository) {
        this.worklogRepository = worklogRepository;
    }

    public List<WorklogEntryModel> save(List<WorklogEntryModel> worklogEntries) {
        List<WorklogEntryDocument> documents = toDocumentList(worklogEntries);
        documents = worklogRepository.save(documents);

        return toModelList(documents);
    }

    public List<WorklogEntryModel> findAll() {
        return toModelList(worklogRepository.findAll());
    }

    WorklogEntryDocument toDocument(WorklogEntryModel model) {
        return new WorklogEntryDocument()
                .setId(model.getId())
                .setUserId(model.getUserId())
                .setCustomer(model.getCustomer())
                .setProject(model.getProject())
                .setStart(model.getStart())
                .setStop(model.getStop())
                .setDuration(model.getDuration())
                .setComment(model.getComment());
    }

    List<WorklogEntryDocument> toDocumentList(List<WorklogEntryModel> models) {
        return models.stream()
                     .filter(Objects::nonNull)
                     .map(this::toDocument)
                     .collect(Collectors.toList());
    }

    WorklogEntryModel toModel(WorklogEntryDocument document) {
        return new WorklogEntryModel()
                .setId(document.getId())
                .setUserId(document.getUserId())
                .setCustomer(document.getCustomer())
                .setProject(document.getProject())
                .setStart(document.getStart())
                .setStop(document.getStop())
                .setDuration(document.getDuration())
                .setComment(document.getComment());
    }

    List<WorklogEntryModel> toModelList(List<WorklogEntryDocument> documents) {
        return documents.stream()
                        .filter(Objects::nonNull)
                        .map(this::toModel)
                        .collect(Collectors.toList());
    }
}