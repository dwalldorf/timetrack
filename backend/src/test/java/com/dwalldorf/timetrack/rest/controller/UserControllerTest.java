package com.dwalldorf.timetrack.rest.controller;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.*;

import com.dwalldorf.timetrack.BaseTest;
import com.dwalldorf.timetrack.exception.InvalidInputException;
import com.dwalldorf.timetrack.repository.UserRepository;
import com.dwalldorf.timetrack.rest.dto.LoginDto;
import com.dwalldorf.timetrack.service.UserService;
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
        ReflectionTestUtils.setField(userService, "userRepository", Mockito.mock(UserRepository.class));

        LoginDto loginDto = new LoginDto();
        loginDto.setPassword("password");

        when(userService.login(any(), any())).thenCallRealMethod();

        userController.login(loginDto);
    }

    @Test(expected = InvalidInputException.class)
    public void testLogin_NoPassword() throws Exception {
        ReflectionTestUtils.setField(userService, "userRepository", Mockito.mock(UserRepository.class));

        LoginDto loginDto = new LoginDto();
        loginDto.setUsername("username");

        when(userService.login(any(), any())).thenCallRealMethod();

        userController.login(loginDto);
    }
}
