package com.dwalldorf.timetrack.backend.annotation;

import static org.mockito.Mockito.*;

import com.dwalldorf.timetrack.backend.annotation.RequireRoleInvocationHandler;
import com.dwalldorf.timetrack.backend.BaseTest;
import com.dwalldorf.timetrack.backend.exception.AdminRequiredException;
import com.dwalldorf.timetrack.backend.exception.LoginRequiredException;
import com.dwalldorf.timetrack.model.UserModel;
import com.dwalldorf.timetrack.backend.service.UserService;
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
        when(userService.getCurrentUser()).thenReturn(new UserModel().setAdmin(false));
        roleInvocationHandler.checkAdminBefore(createJoinPointMock());
    }

    @Test
    public void testCheckAdminBefore_Success() throws Exception {
        when(userService.getCurrentUser()).thenReturn(new UserModel().setAdmin(true));
        roleInvocationHandler.checkAdminBefore(createJoinPointMock());
    }
}