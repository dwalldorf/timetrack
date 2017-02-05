package com.dwalldorf.timetrack.rest.controller;

import com.dwalldorf.timetrack.document.User;
import com.dwalldorf.timetrack.event.PermissionFailureEvent;
import com.dwalldorf.timetrack.exception.AdminRequiredException;
import com.dwalldorf.timetrack.exception.LoginRequiredException;
import com.dwalldorf.timetrack.service.UserService;
import javax.inject.Inject;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class ErrorController {

    private final static String NOT_FOUND = "NOT FOUND";

    private final ApplicationEventPublisher eventPublisher;

    private final UserService userService;

    @Inject
    public ErrorController(ApplicationEventPublisher eventPublisher, UserService userService) {
        this.eventPublisher = eventPublisher;
        this.userService = userService;
    }

    @ExceptionHandler(LoginRequiredException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String handleLoginRequireException(LoginRequiredException e) {
        eventPublisher.publishEvent(PermissionFailureEvent.failureEvent(e.getMessage()));

        return NOT_FOUND;
    }

    @ExceptionHandler(AdminRequiredException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String handleAdminRequireException(AdminRequiredException e) {
        final String errorMessage = e.getMessage();
        final User currentUser = userService.getCurrentUser();

        if (currentUser != null) {
            eventPublisher.publishEvent(PermissionFailureEvent.failureEvent(currentUser, e.getMessage()));
        } else {
            eventPublisher.publishEvent(PermissionFailureEvent.failureEvent(e.getMessage()));
        }

        return NOT_FOUND;
    }
}
