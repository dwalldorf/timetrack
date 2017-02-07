package com.dwalldorf.timetrack.annotation;

import static org.mockito.Mockito.*;

import com.dwalldorf.timetrack.BaseTest;
import com.dwalldorf.timetrack.exception.AdminRequiredException;
import com.dwalldorf.timetrack.exception.LoginRequiredException;
import com.dwalldorf.timetrack.service.PasswordService;
import com.dwalldorf.timetrack.service.UserService;
import com.dwalldorf.timetrack.util.RandomUtil;
import org.junit.Test;
import org.mockito.Mock;
import com.dwalldorf.timetrack.stub.UserStub;

public class RequireAdminTest extends BaseTest {

    private final UserStub userStub;

    @Mock
    private UserService userService;

    private RequireRoleInvocationHandler roleInvocationHandler;

    public RequireAdminTest() throws Exception {
        userStub = new UserStub(new RandomUtil(), new PasswordService());
    }

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
        when(userService.getCurrentUser()).thenReturn(userStub.createUser(false));
        roleInvocationHandler.checkAdminBefore(createJoinPointMock());
    }

    @Test
    public void testCheckAdminBefore_Success() throws Exception {
        when(userService.getCurrentUser()).thenReturn(userStub.createUser(true));
        roleInvocationHandler.checkAdminBefore(createJoinPointMock());
    }
}