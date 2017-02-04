package com.dwalldorf.timetrack.rest.controller;

import com.dwalldorf.timetrack.document.WorklogEntry;
import com.dwalldorf.timetrack.service.CsvImportService;
import com.dwalldorf.timetrack.service.WorklogService;
import java.util.List;
import javax.inject.Inject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
public class CsvUploadController {

    private final CsvImportService csvImportService;

    private final WorklogService worklogService;


    @Inject
    public CsvUploadController(CsvImportService csvImportService, WorklogService worklogService) {
        this.csvImportService = csvImportService;
        this.worklogService = worklogService;
    }

    @PostMapping("/csv")
    public ResponseEntity<List<WorklogEntry>> uploadCsvFile(@RequestParam("file") MultipartFile file) {
        List<WorklogEntry> worklogEntries = csvImportService.getWorklogEntriesFromCsv(file);
        worklogEntries = worklogService.diffWithDatabase(worklogEntries);
        worklogEntries = worklogService.save(worklogEntries);

        return new ResponseEntity<>(worklogEntries, HttpStatus.CREATED);
    }
}
