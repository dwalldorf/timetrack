package com.dwalldorf.timetrack.backend.rest.controller;

import com.dwalldorf.timetrack.backend.service.UserService;
import com.dwalldorf.timetrack.model.UserModel;
import javax.inject.Inject;

abstract class BaseController {

    private UserService userService;

    @Inject
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    protected UserModel getCurrentUser() {
        return userService.getCurrentUser();
    }
}