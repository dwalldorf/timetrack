package com.dwalldorf.timetrack.repository.backend;

import com.dwalldorf.timetrack.repository.backend.rest.controller.CsvUploadControllerIT;
import com.dwalldorf.timetrack.repository.backend.rest.controller.UserControllerIT;
import com.dwalldorf.timetrack.repository.backend.rest.controller.VersionControllerIT;
import com.dwalldorf.timetrack.repository.backend.rest.controller.WorklogControllerIT;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        CsvUploadControllerIT.class,
        UserControllerIT.class,
        VersionControllerIT.class,
        WorklogControllerIT.class
})
public class IntegrationTestSuite {
}