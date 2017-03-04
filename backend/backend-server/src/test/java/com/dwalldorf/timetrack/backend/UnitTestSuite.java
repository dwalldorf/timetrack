package com.dwalldorf.timetrack.backend;

import com.dwalldorf.timetrack.backend.annotation.RequireLoginTest;
import com.dwalldorf.timetrack.backend.config.GraphDateTimeFormatterConfigTest;
import com.dwalldorf.timetrack.backend.config.SessionConfigTest;
import com.dwalldorf.timetrack.backend.event.IdentityConflictEventHandlerTest;
import com.dwalldorf.timetrack.backend.event.PermissionFailureEventHandlerTest;
import com.dwalldorf.timetrack.backend.event.UserAuthenticationEventHandlerTest;
import com.dwalldorf.timetrack.backend.rest.controller.ErrorControllerTest;
import com.dwalldorf.timetrack.backend.rest.dto.ListDtoTest;
import com.dwalldorf.timetrack.backend.service.CsvImportServiceTest;
import com.dwalldorf.timetrack.backend.service.GraphServiceTest;
import com.dwalldorf.timetrack.backend.service.UserServiceTest;
import com.dwalldorf.timetrack.backend.service.WorklogServiceTest;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        RequireLoginTest.class,

        GraphDateTimeFormatterConfigTest.class,
        SessionConfigTest.class,

        IdentityConflictEventHandlerTest.class,
        PermissionFailureEventHandlerTest.class,
        UserAuthenticationEventHandlerTest.class,

        ErrorControllerTest.class,

        ListDtoTest.class,

        CsvImportServiceTest.class,
        GraphServiceTest.class,
        UserServiceTest.class,
        WorklogServiceTest.class,
})
public class UnitTestSuite {
}