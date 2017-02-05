package com.dwalldorf.timetrack.annotation;

import static org.mockito.Mockito.*;

import com.dwalldorf.timetrack.BaseTest;
import com.dwalldorf.timetrack.document.User;
import com.dwalldorf.timetrack.document.UserProperties;
import com.dwalldorf.timetrack.document.UserSettings;
import com.dwalldorf.timetrack.exception.LoginRequiredException;
import com.dwalldorf.timetrack.service.UserService;
import org.joda.time.DateTime;
import org.junit.Test;
import org.mockito.Mock;

public class RequireLoginTest extends BaseTest {

    @Mock
    private UserService userService;

    private RequireRoleInvocationHandler roleInvocationHandler;

    @Override
    protected void setUp() {
        this.roleInvocationHandler = new RequireRoleInvocationHandler(userService);
    }

    @Test(expected = LoginRequiredException.class)
    public void testCheckLoginBefore_LoginRequiredException() throws Exception {
        when(userService.getCurrentUser()).thenReturn(null);
        roleInvocationHandler.checkLoginBefore(createJoinPointMock());
    }

    @Test
    public void testCheckLoginBefore_Success() throws Exception {
        when(userService.getCurrentUser()).thenReturn(createUser());
        roleInvocationHandler.checkLoginBefore(createJoinPointMock());
    }

    private User createUser() {
        return new User()
                .setId("someId")
                .setUserProperties(
                        new UserProperties()
                                .setUsername("testUser")
                                .setRegistration(new DateTime().minusDays(3))
                                .setEmail("name@host.tld")
                                .setConfirmedEmail(true)
                                .setUserSettings(new UserSettings())
                );
    }
}
