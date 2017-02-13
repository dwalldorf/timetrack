package com.dwalldorf.timetrack.backend;

import com.dwalldorf.timetrack.backend.rest.controller.CsvUploadControllerIT;
import com.dwalldorf.timetrack.backend.rest.controller.UserControllerIT;
import com.dwalldorf.timetrack.backend.rest.controller.VersionControllerIT;
import com.dwalldorf.timetrack.backend.rest.controller.WorklogControllerIT;
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