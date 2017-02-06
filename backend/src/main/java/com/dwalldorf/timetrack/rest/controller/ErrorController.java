package com.dwalldorf.timetrack.rest.controller;

import static org.springframework.http.HttpStatus.NOT_FOUND;

import com.dwalldorf.timetrack.document.User;
import com.dwalldorf.timetrack.event.PermissionFailureEvent;
import com.dwalldorf.timetrack.exception.AdminRequiredException;
import com.dwalldorf.timetrack.exception.LoginRequiredException;
import com.dwalldorf.timetrack.service.UserService;
import javax.inject.Inject;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class ErrorController {

    private final static String EMPTY_RESULT = "no";

    private final ApplicationEventPublisher eventPublisher;

    private final UserService userService;

    @Inject
    public ErrorController(ApplicationEventPublisher eventPublisher, UserService userService) {
        this.eventPublisher = eventPublisher;
        this.userService = userService;
    }

    @ExceptionHandler(LoginRequiredException.class)
    @ResponseStatus(NOT_FOUND)
    public ResponseEntity handleLoginRequireException(LoginRequiredException e) {
        eventPublisher.publishEvent(PermissionFailureEvent.failureEvent(e.getMessage()));
        return new ResponseEntity(NOT_FOUND);
    }

    @ExceptionHandler(AdminRequiredException.class)
    @ResponseStatus(NOT_FOUND)
    public String handleAdminRequiredException(AdminRequiredException e) {
        final String errorMessage = e.getMessage();
        final User user = userService.getCurrentUser();

        if (user != null) {
            eventPublisher.publishEvent(PermissionFailureEvent.failureEvent(user, errorMessage));
        } else {
            eventPublisher.publishEvent(PermissionFailureEvent.failureEvent(errorMessage));
        }
        return EMPTY_RESULT;
    }
}