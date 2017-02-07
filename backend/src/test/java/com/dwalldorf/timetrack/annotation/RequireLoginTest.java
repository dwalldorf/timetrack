package com.dwalldorf.timetrack.annotation;

import static org.mockito.Mockito.*;

import com.dwalldorf.timetrack.BaseTest;
import com.dwalldorf.timetrack.exception.LoginRequiredException;
import com.dwalldorf.timetrack.service.PasswordService;
import com.dwalldorf.timetrack.service.UserService;
import com.dwalldorf.timetrack.util.RandomUtil;
import org.junit.Test;
import org.mockito.Mock;
import com.dwalldorf.timetrack.stub.UserStub;

public class RequireLoginTest extends BaseTest {

    private final UserStub userStub;

    @Mock
    private UserService userService;

    private RequireRoleInvocationHandler roleInvocationHandler;

    public RequireLoginTest() throws Exception {
        userStub = new UserStub(new RandomUtil(), new PasswordService());
    }

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
        when(userService.getCurrentUser()).thenReturn(userStub.createUser());
        roleInvocationHandler.checkLoginBefore(createJoinPointMock());
    }
}