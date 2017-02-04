package com.dwalldorf.timetrack.annotation;

import com.dwalldorf.timetrack.BaseTest;
import com.dwalldorf.timetrack.service.CsvImportService;
import java.lang.reflect.Field;
import org.junit.Assert;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.util.ReflectionUtils;

public class LoggerInjectorTest extends BaseTest {

    @InjectMocks
    private LoggerInjector loggerInjector;

    @Mock
    private CsvImportService service;

    @Test
    public void testCreateLogger_SetsDeclaringClass() throws Exception {
        loggerInjector.postProcessAfterInitialization(service, service.getClass().getSimpleName());

        Field logger = ReflectionUtils.findField(service.getClass(), "logger");

        Assert.assertNotNull(logger);
        Assert.assertEquals(CsvImportService.class, logger.getDeclaringClass());
    }
}