package com.dwalldorf.timetrack.backend;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.*;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.spi.LoggingEvent;
import ch.qos.logback.core.Appender;
import java.util.ArrayList;
import java.util.List;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;
import org.slf4j.LoggerFactory;

@RunWith(MockitoJUnitRunner.class)
public abstract class BaseTest {

    private static final org.slf4j.Logger logger = LoggerFactory.getLogger(BaseTest.class);

    private final Logger rootLogger = (Logger) LoggerFactory.getLogger(Logger.ROOT_LOGGER_NAME);
    private final Appender mockAppender = spy(Appender.class);

    private final ArgumentCaptor<LoggingEvent> loggingEventCaptor = ArgumentCaptor.forClass(LoggingEvent.class);

    @Before
    @SuppressWarnings("unchecked")
    public final void beforeSetUp() throws Exception {
        MockitoAnnotations.initMocks(this);

        when(mockAppender.getName()).thenReturn("MOCK_APPENDER");
        rootLogger.addAppender(mockAppender);

        this.setUp();
    }

    protected void setUp() {
    }

    protected final JoinPoint createJoinPointMock() {
        JoinPoint joinPointMock = Mockito.mock(JoinPoint.class);
        Signature signatureMock = Mockito.mock(Signature.class);

        when(signatureMock.toString()).thenReturn("SomeBean#someMethod");
        when(joinPointMock.getSignature()).thenReturn(signatureMock);

        return joinPointMock;
    }

    protected final void assertLogged(String message, Level logLevel) {
        assertLogged(message, logLevel, null);
    }

    @SuppressWarnings("unchecked")
    protected final void assertLogged(String message, Level logLevel, String markerName) {
        verify(mockAppender, atLeastOnce()).doAppend(loggingEventCaptor.capture());
        List<LoggingEvent> loggingEvents = getLoggingEvents(message);

        if (loggingEvents.size() == 0) {
            logger.error("Wanted: {}", message);
            logger.error("Found:");
            for (LoggingEvent loggingEvent : getLoggingEvents(null)) {
                logger.error(loggingEvent.getFormattedMessage());
            }
        }

        assertTrue(loggingEvents.size() >= 1);
        for (LoggingEvent loggingEvent : loggingEvents) {
            assertEquals(logLevel, loggingEvent.getLevel());

            if (markerName != null) {
                assertEquals(markerName, loggingEvent.getMarker().getName());
            }
        }
    }

    private List<LoggingEvent> getLoggingEvents(String message) {
        List<LoggingEvent> allLogMessages = loggingEventCaptor.getAllValues();
        if (message == null) {
            return allLogMessages;
        }

        List<LoggingEvent> result = new ArrayList<>();
        for (LoggingEvent loggingEvent : allLogMessages) {
            if (loggingEvent.getFormattedMessage().equals(message)) {
                result.add(loggingEvent);
            }
        }

        return result;
    }
}