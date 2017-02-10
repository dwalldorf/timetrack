package com.dwalldorf.timetrack.repository.backend;

import com.dwalldorf.timetrack.repository.backend.annotation.RequireAdminTest;
import com.dwalldorf.timetrack.repository.backend.annotation.RequireLoginTest;
import com.dwalldorf.timetrack.repository.backend.rest.controller.UserControllerTest;
import com.dwalldorf.timetrack.repository.service.CsvImportServiceTest;
import com.dwalldorf.timetrack.repository.service.UserServiceTest;
import com.dwalldorf.timetrack.repository.service.WorklogServiceTest;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        RequireAdminTest.class,
        RequireLoginTest.class,

        UserControllerTest.class,

        CsvImportServiceTest.class,
        UserServiceTest.class,
        WorklogServiceTest.class,
})
public class UnitTestSuite {
}