package com.dwalldorf.timetrack;

import com.dwalldorf.timetrack.rest.controller.CsvUploadControllerIT;
import com.dwalldorf.timetrack.rest.controller.VersionControllerIT;
import com.dwalldorf.timetrack.rest.controller.WorklogControllerIT;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        CsvUploadControllerIT.class,
        VersionControllerIT.class,
        WorklogControllerIT.class
})
public class IntegrationTestSuite {
}
