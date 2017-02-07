package com.dwalldorf.timetrack.rest.controller;

import static com.dwalldorf.timetrack.rest.controller.UserController.BASE_URI;
import static com.dwalldorf.timetrack.rest.controller.UserController.URI_LOGIN;
import static com.dwalldorf.timetrack.rest.controller.UserController.URI_LOGOUT;
import static com.dwalldorf.timetrack.rest.controller.UserController.URI_ME;
import static org.hamcrest.CoreMatchers.is;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.dwalldorf.timetrack.document.User;
import com.dwalldorf.timetrack.document.UserProperties;
import com.dwalldorf.timetrack.rest.dto.LoginDto;
import com.dwalldorf.timetrack.rest.dto.UserDto;
import com.dwalldorf.timetrack.service.PasswordService;
import com.dwalldorf.timetrack.service.UserService;
import com.dwalldorf.timetrack.stub.UserStub;
import com.dwalldorf.timetrack.util.RandomUtil;
import org.junit.Test;
import org.springframework.boot.test.mock.mockito.MockBean;

public class UserControllerIT extends BaseControllerIT {

    private final UserStub userStub;

    @MockBean
    private UserService userService;

    public UserControllerIT() throws Exception {
        userStub = new UserStub(new RandomUtil(), new PasswordService());
    }

    @Test
    public void testRegister_Success() throws Exception {
        UserDto userDto = new UserDto()
                .setUsername("testUser")
                .setEmail("name@host.tld")
                .setPassword("password");

        doPost(BASE_URI, userDto)
                .andExpect(status().isCreated());
    }

    @Test
    public void testRegister_NoUsername() throws Exception {
        UserDto userDto = new UserDto()
                .setEmail("name@host.tld")
                .setPassword("password");

        doPost(BASE_URI, userDto)
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testRegister_NoEmail() throws Exception {
        UserDto userDto = new UserDto()
                .setUsername("testUser")
                .setPassword("password");

        doPost(BASE_URI, userDto)
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testRegister_NoPassword() throws Exception {
        UserDto userDto = new UserDto()
                .setUsername("testUser")
                .setEmail("name@host.tld");

        doPost(BASE_URI, userDto)
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testLogin_Success() throws Exception {
        final String username = "username";
        final String password = "password";
        LoginDto loginDto = new LoginDto()
                .setUsername(username)
                .setPassword(password);
        when(userService.login(eq(username), eq(password))).thenReturn(userStub.createUser());

        doPost(BASE_URI + URI_LOGIN, loginDto)
                .andExpect(status().isOk());
    }

    @Test
    public void testLogin_Failure() throws Exception {
        LoginDto loginDto = new LoginDto()
                .setUsername("username")
                .setPassword("password");
        when(userService.login(anyString(), anyString())).thenReturn(null);

        doPost(BASE_URI + URI_LOGIN, loginDto)
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testGetMe_NotLoggedIn() throws Exception {
        when(userService.getCurrentUser()).thenReturn(null);

        doGet(BASE_URI + URI_ME)
                .andExpect(status().isNotFound());
    }

    @Test
    public void testGetMe_Success() throws Exception {
        User mockUser = userStub.createUser();
        UserProperties userProperties = mockUser.getUserProperties();
        when(userService.getCurrentUser()).thenReturn(mockUser);

        doGet(BASE_URI + URI_ME)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(mockUser.getId())))
                .andExpect(jsonPath("$.username", is(userProperties.getUsername())))
                .andExpect(jsonPath("$.email", is(userProperties.getEmail())))
                .andExpect(jsonPath("$.confirmedEmail", is(userProperties.isConfirmedEmail())));
    }

    @Test
    public void testLogout_NotLoggedIn() throws Exception {
        doPost(BASE_URI + URI_LOGOUT)
                .andExpect(status().isNotFound());
    }

    @Test
    public void testLogout_Success() throws Exception {
        User mockUser = userStub.createUser();
        when(userService.getCurrentUser()).thenReturn(mockUser);

        doPost(BASE_URI + URI_LOGOUT)
                .andExpect(status().isOk());
    }
}