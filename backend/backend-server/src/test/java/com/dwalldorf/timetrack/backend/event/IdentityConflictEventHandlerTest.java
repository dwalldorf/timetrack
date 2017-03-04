package com.dwalldorf.timetrack.backend.event;

import ch.qos.logback.classic.Level;
import com.dwalldorf.timetrack.backend.BaseTest;
import org.junit.Test;

public class IdentityConflictEventHandlerTest extends BaseTest {

    private IdentityConflictEventHandler eventHandler;

    private static final String eventMessage = "User with id '58acb5a779d53f90fd4b8ce7' tried to modify worklog entry with id '58b9a714c5a8996f68cb601e' which belongs to a different user";
    private static final String expectedMarkerName = "identity_conflict";

    @Override
    protected void setUp() {
        this.eventHandler = new IdentityConflictEventHandler();
    }

    @Test
    public void testOnIdentityConflictEvent() throws Exception {
        eventHandler.onIdentityConflictEvent(new IdentityConflictEvent(eventMessage));
        assertLogged(eventMessage, Level.INFO, expectedMarkerName);
    }
}