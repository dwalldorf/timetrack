package com.dwalldorf.timetrack.repository.backend.rest.controller;

import com.dwalldorf.timetrack.model.WorklogEntryModel;
import com.dwalldorf.timetrack.repository.service.CsvImportService;
import com.dwalldorf.timetrack.repository.service.WorklogService;
import java.util.List;
import javax.inject.Inject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping(CsvUploadController.BASE_URI)
public class CsvUploadController {

    private final CsvImportService csvImportService;

    private final WorklogService worklogService;

    public static final String BASE_URI = "/csv";

    @Inject
    public CsvUploadController(CsvImportService csvImportService, WorklogService worklogService) {
        this.csvImportService = csvImportService;
        this.worklogService = worklogService;
    }

    @PostMapping
    public ResponseEntity<List<WorklogEntryModel>> uploadCsvFile(@RequestParam("file") MultipartFile file) {
        List<WorklogEntryModel> worklogEntries = csvImportService.getWorklogEntriesFromCsv(file);
        worklogEntries = worklogService.diffWithDatabase(worklogEntries);
        worklogEntries = worklogService.save(worklogEntries);

        return new ResponseEntity<>(worklogEntries, HttpStatus.CREATED);
    }
}
