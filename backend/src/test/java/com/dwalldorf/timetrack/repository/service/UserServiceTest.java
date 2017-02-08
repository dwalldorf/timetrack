package com.dwalldorf.timetrack.repository.service;

import static com.dwalldorf.timetrack.backend.event.ActionEvent.Action.LOGIN;
import static com.dwalldorf.timetrack.backend.event.ActionEvent.Action.LOGOUT;
import static com.dwalldorf.timetrack.backend.event.ActionEvent.Result.FAILURE;
import static com.dwalldorf.timetrack.backend.event.ActionEvent.Result.SUCCESS;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.*;

import com.dwalldorf.timetrack.model.UserModel;
import com.dwalldorf.timetrack.repository.backend.BaseTest;
import com.dwalldorf.timetrack.backend.event.UserAuthenticationEvent;
import com.dwalldorf.timetrack.repository.dao.UserDao;
import com.dwalldorf.timetrack.repository.exception.BadPasswordException;
import com.dwalldorf.timetrack.backend.exception.InvalidInputException;
import com.dwalldorf.timetrack.backend.service.UserService;
import javax.servlet.http.HttpSession;
import org.joda.time.DateTime;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.springframework.context.ApplicationEventPublisher;

public class UserServiceTest extends BaseTest {

    private final static String ID = "someId";
    private final static String USERNAME = "testUser";
    private final static String EMAIL = "max@mustermann.org";
    private final static DateTime REGISTRATION = new DateTime();
    private final static String PASSWORD = "notSoSecurePassword123";

    @Mock
    private ApplicationEventPublisher eventPublisher;

    @Mock
    private UserDao userDao;

    @Mock
    private HttpSession httpSession;

    private UserService userService;

    @Override
    protected void setUp() {
        this.userService = new UserService(userDao, eventPublisher, httpSession);
    }

    @Test(expected = InvalidInputException.class)
    public void testRegister_UsernameExists() throws Exception {
        final String username = "username";
        UserModel user = new UserModel()
                .setUsername(username);

        when(userDao.findByUsername(eq(username))).thenReturn(new UserModel());

        userService.register(user);
    }

    @Test(expected = InvalidInputException.class)
    public void testRegister_EmailExists() throws Exception {
        final String email = "test@example.com";
        UserModel user = new UserModel()
                .setEmail(email);

        when(userDao.findByUsername(anyString())).thenReturn(new UserModel());

        userService.register(user);
    }

    @Test
    public void testRegister_SetsRegistrationDate() throws Exception {
        UserModel registerUser = new UserModel();
        when(userDao.register(any(UserModel.class))).thenReturn(registerUser);
        UserModel registeredUser = userService.register(registerUser);

        assertNotNull(registeredUser.getRegistration());
    }

    @Test
    public void testLogin_ReturnsNullIfUserNotFound() {
        when(userDao.findByUsername(eq(USERNAME))).thenReturn(null);
        UserModel retVal = userService.login(USERNAME, PASSWORD);

        assertNull(retVal);
    }

    @Test
    public void testLogin_ReturnsNullIfUserFoundButWrongPassword() {
        when(userDao.findByUsername(eq(USERNAME))).thenThrow(new BadPasswordException(USERNAME));

        UserModel retVal = userService.login(USERNAME, "wrongPassword");
        assertNull(retVal);
    }

    @Test
    public void testLogin_WrongPassword_PublishesLoginFailureEvent() throws Exception {
        when(userDao.findByUsername(eq(USERNAME))).thenReturn(new UserModel());
        when(userDao.login(eq(USERNAME), anyString())).thenThrow(new BadPasswordException(USERNAME));

        userService.login(USERNAME, "wrongPassword");

        ArgumentCaptor<UserAuthenticationEvent> captor = ArgumentCaptor.forClass(UserAuthenticationEvent.class);
        verify(eventPublisher).publishEvent(captor.capture());

        UserAuthenticationEvent event = captor.getValue();
        assertEquals(LOGIN, event.getAction());
        assertEquals(FAILURE, event.getResult());
        assertTrue(event.getMessage().contains("wrong password"));
    }

    @Test
    public void testLogin_AlreadyLoggedIn_Publishes() throws Exception {
        final String username = "someUsername";
        UserModel mockUser = new UserModel()
                .setId("someId")
                .setUsername(username);

        when(httpSession.getAttribute("user")).thenReturn(mockUser);
        ArgumentCaptor<UserAuthenticationEvent> captor = ArgumentCaptor.forClass(UserAuthenticationEvent.class);

        userService.login(username, "somePassword");
        verify(eventPublisher).publishEvent(captor.capture());

        UserAuthenticationEvent event = captor.getValue();
        assertEquals(LOGIN, event.getAction());
        assertEquals(FAILURE, event.getResult());
        assertTrue(event.getMessage().contains("already logged in"));
    }

    @Test
    public void testLogout_PublishesLogoutEvent() throws Exception {
        ArgumentCaptor<UserAuthenticationEvent> captor = ArgumentCaptor.forClass(UserAuthenticationEvent.class);
        userService.logout();

        verify(httpSession).invalidate();
        verify(eventPublisher).publishEvent(captor.capture());

        UserAuthenticationEvent event = captor.getValue();
        assertEquals(LOGOUT, event.getAction());
        assertEquals(SUCCESS, event.getResult());
    }
}