package com.dwalldorf.timetrack.backend.event;

import static ch.qos.logback.classic.Level.INFO;
import static org.mockito.Mockito.*;

import ch.qos.logback.classic.Level;
import com.dwalldorf.timetrack.backend.BaseTest;
import com.dwalldorf.timetrack.model.UserModel;
import com.dwalldorf.timetrack.repository.dao.UserDao;
import org.joda.time.DateTime;
import org.junit.Test;

public class UserAuthenticationEventHandlerTest extends BaseTest {

    private static final String USER_ID = "58ad775daf1e9c9d16444a96";
    private static final String USER_NAME = "test_user";
    private static final String expectedMarkerName = "user_auth";
    private static final UserModel mockUser = mock(UserModel.class);

    private UserAuthenticationEventHandler eventHandler;

    @Override
    protected void setUp() {
        UserDao mockUserDao = mock(UserDao.class);
        this.eventHandler = new UserAuthenticationEventHandler(mockUserDao);

        when(mockUser.getId()).thenReturn(USER_ID);
        when(mockUser.getUsername()).thenReturn(USER_NAME);
    }

    @Test
    public void testOnUserAuthenticationEvent_Register_Success_LogSuccess() throws Exception {
        UserAuthenticationEvent event = UserAuthenticationEvent.registrationSuccessEvent(mockUser);
        String expectedMessage = String.format("Successful registration: '%s'", event.getUsername());

        eventHandler.onUserAuthenticationEvent(event);

        assertLogged(expectedMessage, Level.INFO, expectedMarkerName);
    }

    @Test
    public void testOnUserAuthenticationEvent_Register_Failure_LogFailure() throws Exception {
        String msg = "username or email already in use";
        UserAuthenticationEvent event = UserAuthenticationEvent.registrationFailedEvent(mockUser.getUsername(), msg);
        String expectedMessage = String.format(
                "Failure during registration with username: '%s', message: %s",
                USER_NAME,
                msg
        );

        eventHandler.onUserAuthenticationEvent(event);

        assertLogged(expectedMessage, Level.INFO, expectedMarkerName);
    }

    @Test
    public void testOnUserAuthenticationEvent_Login_Success_LogSuccess() throws Exception {
        UserAuthenticationEvent event = UserAuthenticationEvent.loginSuccessEvent(mockUser);
        String expectedMessage = String.format("Successful login: '%s'", USER_NAME);

        eventHandler.onUserAuthenticationEvent(event);

        assertLogged(expectedMessage, INFO, expectedMarkerName);
    }

    @Test
    public void testOnUserAuthenticationEvent_Login_Success_SetFirstLogin() throws Exception {
        UserAuthenticationEvent event = UserAuthenticationEvent.loginSuccessEvent(mockUser);
        String expectedMessage = String.format("First login: '%s'", USER_NAME);

        eventHandler.onUserAuthenticationEvent(event);

        verify(mockUser).setFirstLogin(any(DateTime.class));
        assertLogged(expectedMessage, INFO, expectedMarkerName);
    }

    @Test
    public void testOnUserAuthenticationEvent_Login_Success_DoNot_SetFirstLogin() throws Exception {
        when(mockUser.getFirstLogin()).thenReturn(new DateTime());
        UserAuthenticationEvent event = UserAuthenticationEvent.loginSuccessEvent(mockUser);

        eventHandler.onUserAuthenticationEvent(event);

        verify(mockUser, never()).setFirstLogin(any(DateTime.class));
    }

    @Test
    public void testOnUserAuthenticationEvent_Login_Success_SetLastLogin() throws Exception {
        UserAuthenticationEvent event = UserAuthenticationEvent.loginSuccessEvent(mockUser);
        eventHandler.onUserAuthenticationEvent(event);

        verify(mockUser).setLastLogin(any(DateTime.class));
    }
}