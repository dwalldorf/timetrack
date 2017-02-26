package com.dwalldorf.timetrack.backend.event;

import ch.qos.logback.classic.Level;
import com.dwalldorf.timetrack.backend.BaseTest;
import com.dwalldorf.timetrack.model.UserModel;
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
        PermissionFailureEvent event = new PermissionFailureEvent(null, eventMessage);
        eventHandler.onPermissionFailureEvent(event);

        assertLogged(eventMessage, Level.INFO, expectedMarkerName);
    }

    @Test
    public void testOnPermissionFailureEvent_LoggedIn() throws Exception {
        String userId = "58ad775daf1e9c9d16444a96";
        String username = "test_user";
        UserModel mockUser = new UserModel()
                .setId(userId)
                .setUsername(username);
        String expectedMessage = String.format("%s - caused by: '%s' with userId %s", eventMessage, username, userId);

        PermissionFailureEvent event = new PermissionFailureEvent(mockUser, eventMessage);
        eventHandler.onPermissionFailureEvent(event);

        assertLogged(expectedMessage, Level.INFO, expectedMarkerName);
    }
}