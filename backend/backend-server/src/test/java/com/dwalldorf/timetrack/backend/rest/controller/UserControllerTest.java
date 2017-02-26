package com.dwalldorf.timetrack.backend.rest.controller;

import static org.mockito.Mockito.*;

import com.dwalldorf.timetrack.backend.BaseTest;
import com.dwalldorf.timetrack.backend.exception.InvalidInputException;
import com.dwalldorf.timetrack.backend.rest.dto.LoginDto;
import com.dwalldorf.timetrack.backend.service.UserService;
import com.dwalldorf.timetrack.repository.dao.UserDao;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.util.ReflectionTestUtils;

public class UserControllerTest extends BaseTest {

    @Mock
    private UserService userService;

    @InjectMocks
    private UserController userController;

    @Test(expected = InvalidInputException.class)
    public void testLogin_ThrowsInvalidInputException() {
        LoginDto loginDto = new LoginDto();
        loginDto.setUsername("");
        loginDto.setPassword("");
        when(userService.login(anyString(), anyString())).thenReturn(null);

        userController.login(loginDto);
    }

    @Test(expected = InvalidInputException.class)
    public void testLogin_NoUsername() throws Exception {
        ReflectionTestUtils.setField(userService, "userDao", Mockito.mock(UserDao.class));

        LoginDto loginDto = new LoginDto();
        loginDto.setPassword("password");

        when(userService.login(any(), any())).thenCallRealMethod();

        userController.login(loginDto);
    }

    @Test(expected = InvalidInputException.class)
    public void testLogin_NoPassword() throws Exception {
        ReflectionTestUtils.setField(userService, "userDao", Mockito.mock(UserDao.class));

        LoginDto loginDto = new LoginDto();
        loginDto.setUsername("username");

        when(userService.login(any(), any())).thenCallRealMethod();

        userController.login(loginDto);
    }
}
