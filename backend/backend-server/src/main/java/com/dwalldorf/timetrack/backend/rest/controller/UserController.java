package com.dwalldorf.timetrack.backend.rest.controller;

import static org.springframework.http.HttpStatus.OK;

import com.dwalldorf.timetrack.backend.annotation.RequireLogin;
import com.dwalldorf.timetrack.backend.exception.InvalidInputException;
import com.dwalldorf.timetrack.backend.rest.dto.LoginDto;
import com.dwalldorf.timetrack.backend.service.UserService;
import com.dwalldorf.timetrack.model.UserModel;
import javax.inject.Inject;
import javax.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(UserController.BASE_URI)
public class UserController extends BaseController {

    static final String BASE_URI = "/users";
    static final String URI_ME = "/me";
    static final String URI_LOGIN = "/login";
    static final String URI_LOGOUT = "/logout";

    private final UserService userService;

    @Inject
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<UserModel> register(@RequestBody @Valid UserModel user) {
        user = userService.register(user);
        return new ResponseEntity<>(user, HttpStatus.CREATED);
    }

    @PostMapping(URI_LOGIN)
    public ResponseEntity<UserModel> login(@RequestBody LoginDto loginDto) throws InvalidInputException {
        UserModel user = userService.login(loginDto.getUsername(), loginDto.getPassword());
        if (user == null) {
            throw new InvalidInputException();
        }

        return new ResponseEntity<>(user, OK);
    }

    @GetMapping(URI_ME)
    @RequireLogin
    public UserModel getMe() {
        return this.getCurrentUser();
    }

    @CrossOrigin
    @PostMapping(URI_LOGOUT)
    @RequireLogin
    public ResponseEntity logout() {
        userService.logout();
        return new ResponseEntity(OK);
    }
}