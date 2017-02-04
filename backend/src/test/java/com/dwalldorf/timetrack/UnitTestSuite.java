package com.dwalldorf.timetrack;

import com.dwalldorf.timetrack.document.WorklogEntryTest;
import com.dwalldorf.timetrack.service.WorklogServiceTest;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        WorklogEntryTest.class,

        WorklogServiceTest.class
})
public class UnitTestSuite {
}
