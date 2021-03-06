package com.dwalldorf.timetrack.backend.service;

import com.dwalldorf.timetrack.backend.exception.CsvParsingException;
import com.dwalldorf.timetrack.model.UserModel;
import com.dwalldorf.timetrack.model.WorklogEntryModel;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class CsvImportService {

    private static final Logger logger = LoggerFactory.getLogger(CsvImportService.class);

    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss");
    private static final String SEPARATOR = ",";

    public List<WorklogEntryModel> getWorklogEntriesFromCsv(MultipartFile file, UserModel user) {
        List<WorklogEntryModel> retVal = new ArrayList<>();

        try {
            List<List<String>> csvList = readFile(file);
            retVal = getWorklogEntries(csvList, user);
        } catch (CsvParsingException e) {
            logger.error(e.getMessage(), e);
        }
        return retVal;
    }

    private List<WorklogEntryModel> getWorklogEntries(List<List<String>> csvList, UserModel user) {
        List<WorklogEntryModel> retVal = new ArrayList<>();
        csvList.forEach(entry -> {
            DateTime start = null;
            DateTime stop = null;
            String comment = null;

            try {
                start = getDateTimeFromString(entry.get(3));
                stop = getDateTimeFromString(entry.get(4));
                comment = getCleanString(entry.get(6));
            } catch (IllegalArgumentException e) {
                logger.error(e.getMessage(), e);
            } catch (ArrayIndexOutOfBoundsException e) {
                // ignore
            }

            final String customer = getCleanString(entry.get(0));
            final String project = getCleanString(entry.get(1));
            final String task = getCleanString(entry.get(1));
            final Integer duration = getIntegerFromString(entry.get(5));

            if (customer != null && start != null && stop != null) {
                WorklogEntryModel worklogEntry = new WorklogEntryModel()
                        .setUserId(user.getId())
                        .setCustomer(customer)
                        .setProject(project)
                        .setTask(task)
                        .setStart(start)
                        .setStop(stop)
                        .setDuration(duration)
                        .setComment(comment);

                retVal.add(worklogEntry);
            } else {
                logger.error("Could not parse line");
                logger.error("Add more information");
            }
        });

        logger.info("Successfully parsed {} csv lines", retVal.size());
        return retVal;
    }

    private String getCleanString(String str) {
        if (str == null) {
            return null;
        }
        return str.replace("\"", "")
                  .trim();
    }

    private Integer getIntegerFromString(String str) {
        str = getCleanString(str);
        if (str == null || str.isEmpty()) {
            return null;
        }
        return Integer.valueOf(str);
    }

    private DateTime getDateTimeFromString(String str) {
        if (str == null) {
            return null;
        }
        str = getCleanString(str);
        return DATE_TIME_FORMATTER.parseDateTime(str);
    }

    private List<List<String>> readFile(MultipartFile sourceFile) throws CsvParsingException {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(sourceFile.getInputStream()))) {
            return reader.lines()
                         .skip(1)
                         .map(line -> Arrays.asList(line.split(SEPARATOR)))
                         .collect(Collectors.toList());
        } catch (IOException e) {
            throw new CsvParsingException(e.getMessage(), e);
        }
    }
}