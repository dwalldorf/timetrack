package com.dwalldorf.timetrack.backend.service;

import static com.dwalldorf.timetrack.backend.event.ActionEvent.Action.LOGIN;
import static com.dwalldorf.timetrack.backend.event.ActionEvent.Action.LOGOUT;
import static com.dwalldorf.timetrack.backend.event.ActionEvent.Result.FAILURE;
import static com.dwalldorf.timetrack.backend.event.ActionEvent.Result.SUCCESS;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.*;

import com.dwalldorf.timetrack.backend.BaseTest;
import com.dwalldorf.timetrack.backend.event.UserAuthenticationEvent;
import com.dwalldorf.timetrack.backend.exception.InvalidInputException;
import com.dwalldorf.timetrack.model.UserModel;
import com.dwalldorf.timetrack.model.stub.UserStub;
import com.dwalldorf.timetrack.model.util.RandomUtil;
import com.dwalldorf.timetrack.repository.dao.UserDao;
import com.dwalldorf.timetrack.repository.exception.BadPasswordException;
import javax.servlet.http.HttpSession;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.context.ApplicationEventPublisher;

public class UserServiceTest extends BaseTest {

    private final static String USERNAME = "testUser";
    private final static String PASSWORD = "notSoSecurePassword123";

    private static final UserStub userStub = new UserStub(new RandomUtil());

    private ApplicationEventPublisher mockEventPublisher;
    private UserDao mockUserDao;
    private HttpSession mockHttpSession;

    private UserService userService;

    @Override
    protected void setUp() {
        this.mockEventPublisher = mock(ApplicationEventPublisher.class);
        this.mockUserDao = mock(UserDao.class);
        this.mockHttpSession = mock(HttpSession.class);

        this.userService = new UserService(mockUserDao, mockEventPublisher, mockHttpSession);
    }

    @Test(expected = InvalidInputException.class)
    public void testRegister_UsernameExists() throws Exception {
        UserModel user = userStub.createUser();
        when(mockUserDao.findByUsername(eq(user.getUsername()))).thenReturn(user);

        userService.register(user);
    }

    @Test(expected = InvalidInputException.class)
    public void testRegister_EmailExists() throws Exception {
        UserModel user = userStub.createUser();
        when(mockUserDao.findByUsername(user.getUsername())).thenReturn(user);

        userService.register(user);
    }

    @Test
    public void testRegister_SetsRegistrationDate() throws Exception {
        UserModel registerUser = userStub.createUser();
        when(mockUserDao.register(eq(registerUser))).thenReturn(registerUser);

        UserModel registeredUser = userService.register(registerUser);

        assertNotNull(registeredUser.getRegistration());
    }

    @Test
    public void testLogin_ReturnsNullIfUserNotFound() {
        when(mockUserDao.findByUsername(eq(USERNAME))).thenReturn(null);
        UserModel retVal = userService.login(USERNAME, PASSWORD);

        assertNull(retVal);
    }

    @Test
    public void testLogin_ReturnsNullIfUserFoundButWrongPassword() {
        when(mockUserDao.findByUsername(eq(USERNAME))).thenThrow(new BadPasswordException(USERNAME));

        UserModel retVal = userService.login(USERNAME, "wrongPassword");
        assertNull(retVal);
    }

    @Test
    public void testLogin_WrongPassword_PublishesLoginFailureEvent() throws Exception {
        when(mockUserDao.findByUsername(eq(USERNAME))).thenReturn(userStub.createUser());
        when(mockUserDao.login(eq(USERNAME), anyString())).thenThrow(new BadPasswordException(USERNAME));

        userService.login(USERNAME, "wrongPassword");

        ArgumentCaptor<UserAuthenticationEvent> captor = ArgumentCaptor.forClass(UserAuthenticationEvent.class);
        verify(mockEventPublisher).publishEvent(captor.capture());

        UserAuthenticationEvent event = captor.getValue();
        assertEquals(LOGIN, event.getAction());
        assertEquals(FAILURE, event.getResult());
        assertTrue(event.getMessage().contains("wrong password"));
    }

    @Test
    public void testLogin_AlreadyLoggedIn_Publishes() throws Exception {
        UserModel mockUser = userStub.createUser();

        when(mockHttpSession.getAttribute("user")).thenReturn(mockUser);
        ArgumentCaptor<UserAuthenticationEvent> captor = ArgumentCaptor.forClass(UserAuthenticationEvent.class);

        userService.login(mockUser.getUsername(), "somePassword");
        verify(mockEventPublisher).publishEvent(captor.capture());

        UserAuthenticationEvent event = captor.getValue();
        assertEquals(LOGIN, event.getAction());
        assertEquals(FAILURE, event.getResult());
        assertTrue(event.getMessage().contains("already logged in"));
    }

    @Test
    public void testLogout_PublishesLogoutEvent() throws Exception {
        ArgumentCaptor<UserAuthenticationEvent> captor = ArgumentCaptor.forClass(UserAuthenticationEvent.class);
        userService.logout();

        verify(mockHttpSession).invalidate();
        verify(mockEventPublisher).publishEvent(captor.capture());

        UserAuthenticationEvent event = captor.getValue();
        assertEquals(LOGOUT, event.getAction());
        assertEquals(SUCCESS, event.getResult());
    }
}