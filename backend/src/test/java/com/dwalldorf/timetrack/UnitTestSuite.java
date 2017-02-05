package com.dwalldorf.timetrack;

import com.dwalldorf.timetrack.annotation.RequireAdminTest;
import com.dwalldorf.timetrack.annotation.RequireLoginTest;
import com.dwalldorf.timetrack.document.WorklogEntryTest;
import com.dwalldorf.timetrack.rest.controller.UserControllerTest;
import com.dwalldorf.timetrack.service.CsvImportServiceTest;
import com.dwalldorf.timetrack.service.PasswordServiceTest;
import com.dwalldorf.timetrack.service.UserServiceTest;
import com.dwalldorf.timetrack.service.WorklogServiceTest;
import com.dwalldorf.timetrack.util.RandomUtilTest;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        RequireAdminTest.class,
        RequireLoginTest.class,

        WorklogEntryTest.class,

        UserControllerTest.class,

        CsvImportServiceTest.class,
        PasswordServiceTest.class,
        UserServiceTest.class,
        WorklogServiceTest.class,

        RandomUtilTest.class
})
public class UnitTestSuite {
}