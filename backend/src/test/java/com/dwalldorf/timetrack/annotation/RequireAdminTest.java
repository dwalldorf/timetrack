package com.dwalldorf.timetrack.annotation;

import static org.mockito.Mockito.*;

import com.dwalldorf.timetrack.BaseTest;
import com.dwalldorf.timetrack.document.User;
import com.dwalldorf.timetrack.document.UserProperties;
import com.dwalldorf.timetrack.document.UserSettings;
import com.dwalldorf.timetrack.exception.AdminRequiredException;
import com.dwalldorf.timetrack.exception.LoginRequiredException;
import com.dwalldorf.timetrack.service.UserService;
import org.joda.time.DateTime;
import org.junit.Test;
import org.mockito.Mock;

public class RequireAdminTest extends BaseTest {

    @Mock
    private UserService userService;

    private RequireRoleInvocationHandler roleInvocationHandler;

    @Override
    protected void setUp() {
        this.roleInvocationHandler = new RequireRoleInvocationHandler(userService);
    }

    @Test(expected = LoginRequiredException.class)
    public void testCheckAdminBefore_LoginRequiredException() throws Exception {
        when(userService.getCurrentUser()).thenReturn(null);
        roleInvocationHandler.checkAdminBefore(createJoinPointMock());
    }

    @Test(expected = AdminRequiredException.class)
    public void testCheckAdminBefore_AdminRequiredException() throws Exception {
        when(userService.getCurrentUser()).thenReturn(createUser(false));
        roleInvocationHandler.checkAdminBefore(createJoinPointMock());
    }

    @Test
    public void testCheckAdminBefore_Success() throws Exception {
        when(userService.getCurrentUser()).thenReturn(createUser(true));
        roleInvocationHandler.checkAdminBefore(createJoinPointMock());
    }

    private User createUser(boolean admin) {
        return new User()
                .setId("someId")
                .setUserProperties(
                        new UserProperties()
                                .setUsername("testUser")
                                .setRegistration(new DateTime().minusDays(3))
                                .setEmail("name@host.tld")
                                .setConfirmedEmail(true)
                                .setUserSettings(
                                        new UserSettings()
                                                .setAdmin(admin)
                                )
                );
    }
}
