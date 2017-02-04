package com.dwalldorf.timetrack;

import com.dwalldorf.timetrack.annotation.LoggerInjectorTest;
import com.dwalldorf.timetrack.document.WorklogEntryTest;
import com.dwalldorf.timetrack.service.CsvImportServiceTest;
import com.dwalldorf.timetrack.service.WorklogServiceTest;
import com.dwalldorf.timetrack.util.RandomUtilTest;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        LoggerInjectorTest.class,

        WorklogEntryTest.class,

        CsvImportServiceTest.class,
        WorklogServiceTest.class,

        RandomUtilTest.class
})
public class UnitTestSuite {
}
