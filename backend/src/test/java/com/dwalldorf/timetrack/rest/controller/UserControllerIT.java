package com.dwalldorf.timetrack.rest.controller;

import static com.dwalldorf.timetrack.rest.controller.UserController.BASE_URI;
import static com.dwalldorf.timetrack.rest.controller.UserController.URI_LOGOUT;
import static com.dwalldorf.timetrack.rest.controller.UserController.URI_ME;
import static org.hamcrest.CoreMatchers.is;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.dwalldorf.timetrack.document.User;
import com.dwalldorf.timetrack.document.UserProperties;
import com.dwalldorf.timetrack.document.UserSettings;
import com.dwalldorf.timetrack.rest.dto.UserDto;
import com.dwalldorf.timetrack.service.UserService;
import org.joda.time.DateTime;
import org.junit.Test;
import org.springframework.boot.test.mock.mockito.MockBean;

public class UserControllerIT extends BaseControllerIT {

    @MockBean
    private UserService userService;

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
    public void testGetMe_NotLoggedIn() throws Exception {
        when(userService.getCurrentUser()).thenReturn(null);

        doGet(BASE_URI + URI_ME)
                .andExpect(status().isNotFound());
    }

    @Test
    public void testGetMe_Success() throws Exception {
        User mockUser = createUser();
        when(userService.getCurrentUser()).thenReturn(mockUser);

        doGet(BASE_URI + URI_ME)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(mockUser.getId())))
                .andExpect(jsonPath("$.username", is(mockUser.getUserProperties().getUsername())))
                .andExpect(jsonPath("$.email", is(mockUser.getUserProperties().getEmail())))
                .andExpect(jsonPath("$.confirmedEmail", is(true)));
    }

    @Test
    public void testLogout_NotLoggedIn() throws Exception {
        doPost(BASE_URI + URI_LOGOUT)
                .andExpect(status().isNotFound());
    }

    @Test
    public void testLogout_Success() throws Exception {
        User mockUser = createUser();
        when(userService.getCurrentUser()).thenReturn(mockUser);

        doPost(BASE_URI + URI_LOGOUT)
                .andExpect(status().isOk());
    }

    private User createUser() {
        return createUser(false);
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