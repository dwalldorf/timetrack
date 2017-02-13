package com.dwalldorf.timetrack.backend.annotation;

import static org.mockito.Mockito.*;

import com.dwalldorf.timetrack.backend.BaseTest;
import com.dwalldorf.timetrack.backend.exception.LoginRequiredException;
import com.dwalldorf.timetrack.backend.service.UserService;
import com.dwalldorf.timetrack.model.UserModel;
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
        when(userService.getCurrentUser()).thenReturn(new UserModel());
        roleInvocationHandler.checkLoginBefore(createJoinPointMock());
    }
}