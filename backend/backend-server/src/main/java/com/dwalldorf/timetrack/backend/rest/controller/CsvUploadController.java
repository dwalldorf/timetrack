package com.dwalldorf.timetrack.backend.rest.controller;

import static org.springframework.http.HttpStatus.CREATED;

import com.dwalldorf.timetrack.backend.annotation.RequireLogin;
import com.dwalldorf.timetrack.backend.service.CsvImportService;
import com.dwalldorf.timetrack.backend.service.WorklogService;
import com.dwalldorf.timetrack.model.UserModel;
import com.dwalldorf.timetrack.model.WorklogEntryModel;
import java.util.List;
import javax.inject.Inject;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping(CsvUploadController.BASE_URI)
public class CsvUploadController extends BaseController {

    static final String BASE_URI = "/csv";

    private final CsvImportService csvImportService;

    private final WorklogService worklogService;

    @Inject
    public CsvUploadController(CsvImportService csvImportService, WorklogService worklogService) {
        this.csvImportService = csvImportService;
        this.worklogService = worklogService;
    }

    @RequireLogin
    @PostMapping
    public ResponseEntity<List<WorklogEntryModel>> uploadCsvFile(@RequestParam("file") MultipartFile file) {
        UserModel user = this.getCurrentUser();

        List<WorklogEntryModel> worklogEntries = csvImportService.getWorklogEntriesFromCsv(file, user);
        worklogEntries = worklogService.diffWithDatabase(worklogEntries);

        if (worklogEntries.size() > 0) {
            worklogEntries = worklogService.save(worklogEntries);
        }

        return new ResponseEntity<>(worklogEntries, CREATED);
    }
}