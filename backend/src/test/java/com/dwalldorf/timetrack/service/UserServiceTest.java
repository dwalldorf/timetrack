package com.dwalldorf.timetrack.service;

import static com.dwalldorf.timetrack.event.ActionEvent.Action.LOGIN;
import static com.dwalldorf.timetrack.event.ActionEvent.Action.LOGOUT;
import static com.dwalldorf.timetrack.event.ActionEvent.Result.FAILURE;
import static com.dwalldorf.timetrack.event.ActionEvent.Result.SUCCESS;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.*;

import com.dwalldorf.timetrack.BaseTest;
import com.dwalldorf.timetrack.document.User;
import com.dwalldorf.timetrack.document.UserProperties;
import com.dwalldorf.timetrack.event.UserAuthenticationEvent;
import com.dwalldorf.timetrack.exception.InvalidInputException;
import com.dwalldorf.timetrack.repository.UserRepository;
import com.dwalldorf.timetrack.util.RandomUtil;
import javax.servlet.http.HttpSession;
import org.joda.time.DateTime;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.springframework.context.ApplicationEventPublisher;
import com.dwalldorf.timetrack.stub.UserStub;

public class UserServiceTest extends BaseTest {

    private final static String ID = "someId";
    private final static String USERNAME = "testUser";
    private final static String EMAIL = "max@mustermann.org";
    private final static DateTime REGISTRATION = new DateTime();
    private final static String PASSWORD = "notSoSecurePassword123";
    private final static byte[] SALT = "salt".getBytes();
    private final static byte[] HASHED_PASSWORD = "hashedPassword".getBytes();

    private final UserStub userStub;

    @Mock
    private ApplicationEventPublisher eventPublisher;

    @Mock
    private UserRepository userRepository;

    @Mock
    private HttpSession httpSession;

    @Spy
    private PasswordService passwordService;

    private UserService userService;

    public UserServiceTest() throws Exception {
        userStub = new UserStub(new RandomUtil(), new PasswordService());
    }

    @Override
    protected void setUp() {
        this.userService = new UserService(userRepository, eventPublisher, httpSession, passwordService);
    }

    @Test
    public void testGetSecureUserCopy() {
        User user = userStub.createUser(ID, USERNAME, EMAIL, REGISTRATION);

        User secureUserCopy = userService.getSecureUserCopy(user);

        assertEquals(ID, secureUserCopy.getId());
        assertEquals(USERNAME, secureUserCopy.getUserProperties().getUsername());
        assertEquals(EMAIL, secureUserCopy.getUserProperties().getEmail());
        assertEquals(REGISTRATION, secureUserCopy.getUserProperties().getRegistration());

        assertNull(secureUserCopy.getUserProperties().getPassword());
        assertNull(secureUserCopy.getUserProperties().getSalt());
        assertNull(secureUserCopy.getUserProperties().getHashedPassword());
    }

    @Test(expected = InvalidInputException.class)
    public void testRegister_UsernameExists() throws Exception {
        final String username = "username";
        User user = new User();
        user.getUserProperties().setUsername(username);

        when(userRepository.findByUserProperties_Username(eq(username))).thenReturn(new User());

        userService.register(user);
    }

    @Test(expected = InvalidInputException.class)
    public void testRegister_EmailExists() throws Exception {
        final String email = "test@example.com";
        User user = new User();
        user.getUserProperties().setEmail(email);

        when(userRepository.findByUserProperties_Username(anyString())).thenReturn(new User());

        userService.register(user);
    }

    @Test
    public void testRegister_SetsRegistrationDate() throws Exception {
        User user = userStub.createUser();
        user.getUserProperties().setRegistration(null);
        assertNull(user.getUserProperties().getRegistration());

        when(userRepository.save(any(User.class))).thenReturn(user);
        User registeredUser = userService.register(user);

        assertNotNull(registeredUser.getUserProperties().getRegistration());
    }

    @Test
    public void testRegister_HashesCorrectly() {
        User user = userStub.createUser();
        when(userRepository.save(any(User.class))).thenReturn(user);
        when(passwordService.createSalt()).thenReturn(SALT);

        userService.register(user);

        Mockito.verify(passwordService).createSalt();
        Mockito.verify(passwordService).hash(any(), eq(SALT));
    }

    @Test
    public void testRegister_ReturnSecureUserCopy() {
        User user = userStub.createUser();
        when(userRepository.save(any(User.class))).thenReturn(user);
        when(passwordService.createSalt()).thenReturn(SALT);

        User registeredUser = userService.register(user);

        assertNull(registeredUser.getUserProperties().getPassword());
        assertNull(registeredUser.getUserProperties().getSalt());
        assertNull(registeredUser.getUserProperties().getHashedPassword());
    }

    @Test
    public void testLogin_ReturnsNullIfUserNotFound() {
        when(userRepository.findByUserProperties_Username(eq(USERNAME))).thenReturn(null);
        User retVal = userService.login(USERNAME, PASSWORD);

        assertNull(retVal);
    }

    @Test
    public void testLogin_ReturnsNullIfUserFoundButWrongPassword() {
        when(userRepository.findByUserProperties_Username(eq(USERNAME))).thenReturn(userStub.createUser());

        User retVal = userService.login(USERNAME, "wrongPassword");
        assertNull(retVal);
    }

    @Test
    public void testLogin_WrongPassword_PublishesLoginFailureEvent() throws Exception {
        when(userRepository.findByUserProperties_Username(eq(USERNAME))).thenReturn(userStub.createUser());
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
        User mockUser = new User()
                .setId("someId")
                .setUserProperties(
                        new UserProperties()
                                .setUsername(username)
                );
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