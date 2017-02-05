package com.dwalldorf.timetrack.rest.controller;

import com.dwalldorf.timetrack.document.User;
import com.dwalldorf.timetrack.exception.InvalidInputException;
import com.dwalldorf.timetrack.rest.dto.LoginDto;
import com.dwalldorf.timetrack.rest.dto.UserDto;
import com.dwalldorf.timetrack.service.UserService;
import javax.inject.Inject;
import javax.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(UserController.BASE_URI)
public class UserController {

    public static final String BASE_URI = "/users";
    public static final String URI_LOGIN = "/login";

    private final UserService userService;

    @Inject
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<UserDto> register(@RequestBody @Valid UserDto userDto) {
        User user = UserDto.toUser(userDto);

        userDto = UserDto.fromUser(userService.register(user));
        return new ResponseEntity<>(userDto, HttpStatus.CREATED);
    }

    @PostMapping(URI_LOGIN)
    public ResponseEntity<UserDto> login(@RequestBody LoginDto loginDto) throws InvalidInputException {
        User loginUser = userService.login(loginDto.getUsername(), loginDto.getPassword());
        if (loginUser == null) {
            throw new InvalidInputException();
        }

        return new ResponseEntity<>(UserDto.fromUser(loginUser), HttpStatus.OK);
    }
}