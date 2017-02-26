package com.dwalldorf.timetrack.backend.event;

import ch.qos.logback.classic.Level;
import com.dwalldorf.timetrack.backend.BaseTest;
import org.junit.Test;

public class PermissionFailureEventHandlerTest extends BaseTest {

    private PermissionFailureEventHandler eventHandler;

    private static final String eventMessage = "UserController.getMe() called without login";
    private static final String expectedMarkerName = "permission_failure";

    @Override
    protected void setUp() {
        this.eventHandler = new PermissionFailureEventHandler();
    }

    @Test
    public void testOnPermissionFailureEvent_NotLoggedIn() throws Exception {
        PermissionFailureEvent event = PermissionFailureEvent.failureEvent(eventMessage);
        eventHandler.onPermissionFailureEvent(event);

        assertLogged(eventMessage, Level.INFO, expectedMarkerName);
    }
}