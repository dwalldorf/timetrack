package com.dwalldorf.timetrack.backend.rest.controller;

import static com.dwalldorf.timetrack.backend.rest.controller.UserController.BASE_URI;
import static com.dwalldorf.timetrack.backend.rest.controller.UserController.URI_LOGIN;
import static com.dwalldorf.timetrack.backend.rest.controller.UserController.URI_LOGOUT;
import static com.dwalldorf.timetrack.backend.rest.controller.UserController.URI_ME;
import static org.hamcrest.core.Is.is;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.dwalldorf.timetrack.backend.rest.dto.LoginDto;
import com.dwalldorf.timetrack.model.UserModel;
import org.junit.Test;

public class UserControllerIT extends BaseControllerIT {

    @Test
    public void testRegister_NoUsername() throws Exception {
        UserModel user = new UserModel()
                .setEmail("name@host.tld")
                .setPassword("password");

        doPost(BASE_URI, user)
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testRegister_NoEmail() throws Exception {
        UserModel user = new UserModel()
                .setUsername("testUser")
                .setPassword("password");

        doPost(BASE_URI, user)
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testRegister_NoPassword() throws Exception {
        UserModel user = new UserModel()
                .setUsername("testUser")
                .setEmail("name@host.tld");

        doPost(BASE_URI, user)
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testRegister_Success() throws Exception {
        UserModel user = new UserModel()
                .setUsername("testUser")
                .setEmail("name@host.tld")
                .setPassword("password");

        doPost(BASE_URI, user)
                .andExpect(status().isCreated());
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
    public void testLogin_NoUsername() throws Exception {
        LoginDto loginDto = new LoginDto()
                .setUsername("")
                .setPassword("password");

        doPost(BASE_URI + URI_LOGIN, loginDto)
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testLogin_NoPassword() throws Exception {
        LoginDto loginDto = new LoginDto()
                .setUsername("username")
                .setPassword("");

        doPost(BASE_URI + URI_LOGIN, loginDto)
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testLogin_Success() throws Exception {
        final String username = "username";
        final String password = "password";
        LoginDto loginDto = new LoginDto()
                .setUsername(username)
                .setPassword(password);
        when(userService.login(eq(username), eq(password))).thenReturn(new UserModel());

        doPost(BASE_URI + URI_LOGIN, loginDto)
                .andExpect(status().isOk());
    }

    @Test
    public void testLogin_AlreadyLoggedIn_Success() throws Exception {
        mockLoggedIn();

        final String username = "username";
        final String password = "password";
        LoginDto loginDto = new LoginDto()
                .setUsername(username)
                .setPassword(password);
        when(userService.login(eq(username), eq(password))).thenReturn(new UserModel());

        doPost(BASE_URI + URI_LOGIN, loginDto)
                .andExpect(status().isOk());
    }

    @Test
    public void testGetMe_NotLoggedIn() throws Exception {
        doGet(BASE_URI + URI_ME)
                .andExpect(status().isUnauthorized());
    }

    @Test
    public void testGetMe_Success() throws Exception {
        UserModel mockUser = mockLoggedIn();

        doGet(BASE_URI + URI_ME)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(mockUser.getId())))
                .andExpect(jsonPath("$.username", is(mockUser.getUsername())))
                .andExpect(jsonPath("$.email", is(mockUser.getEmail())))
                .andExpect(jsonPath("$.confirmedEmail", is(mockUser.isConfirmedEmail())));
    }

    @Test
    public void testUpdate_NotLoggedIn() throws Exception {
        UserModel updateUser = new UserModel()
                .setId("someId")
                .setUsername("username");

        doPut(BASE_URI, updateUser)
                .andExpect(status().isUnauthorized());
    }

    @Test
    public void testUpdate_Success() throws Exception {
        UserModel mockUser = mockLoggedIn();
        mockUser.setWorkingHoursWeek(40F);

        doPut(BASE_URI, mockUser)
                .andExpect(status().isOk());
    }

    @Test
    public void testLogout_NotLoggedIn() throws Exception {
        doPost(BASE_URI + URI_LOGOUT)
                .andExpect(status().isUnauthorized());
    }

    @Test
    public void testLogout_Success() throws Exception {
        mockLoggedIn();
        doPost(BASE_URI + URI_LOGOUT)
                .andExpect(status().isOk());
    }
}