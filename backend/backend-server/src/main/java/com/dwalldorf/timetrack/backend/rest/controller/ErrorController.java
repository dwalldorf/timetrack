package com.dwalldorf.timetrack.backend.rest.controller;

import static org.springframework.http.HttpStatus.NOT_FOUND;

import com.dwalldorf.timetrack.backend.event.PermissionFailureEvent;
import com.dwalldorf.timetrack.backend.exception.AdminRequiredException;
import com.dwalldorf.timetrack.backend.exception.LoginRequiredException;
import com.dwalldorf.timetrack.backend.service.UserService;
import com.dwalldorf.timetrack.model.UserModel;
import javax.inject.Inject;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class ErrorController {

    private final ApplicationEventPublisher eventPublisher;

    private final UserService userService;

    @Inject
    public ErrorController(ApplicationEventPublisher eventPublisher, UserService userService) {
        this.eventPublisher = eventPublisher;
        this.userService = userService;
    }

    @ExceptionHandler(LoginRequiredException.class)
    @ResponseStatus(NOT_FOUND)
    public void handleLoginRequireException(LoginRequiredException e) {
        eventPublisher.publishEvent(PermissionFailureEvent.failureEvent(e.getMessage()));
    }

    @ExceptionHandler(AdminRequiredException.class)
    @ResponseStatus(NOT_FOUND)
    public void handleAdminRequiredException(AdminRequiredException e) {
        final String errorMessage = e.getMessage();
        final UserModel currentUser = userService.getCurrentUser();

        if (currentUser != null) {
            eventPublisher.publishEvent(PermissionFailureEvent.failureEvent(currentUser, errorMessage));
        } else {
            eventPublisher.publishEvent(PermissionFailureEvent.failureEvent(errorMessage));
        }
    }
}