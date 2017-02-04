package com.dwalldorf.timetrack;

import com.dwalldorf.timetrack.rest.controller.VersionControllerIT;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        VersionControllerIT.class
})
public class IntegrationTestSuite {
}
