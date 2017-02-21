package com.dwalldorf.timetrack.backend.rest.controller;

import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.http.HttpStatus.UNAUTHORIZED;

import com.dwalldorf.timetrack.backend.event.PermissionFailureEvent;
import com.dwalldorf.timetrack.backend.exception.AdminRequiredException;
import com.dwalldorf.timetrack.backend.exception.LoginRequiredException;
import com.dwalldorf.timetrack.model.UserModel;
import javax.inject.Inject;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class ErrorController extends BaseController {

    private final ApplicationEventPublisher eventPublisher;


    @Inject
    public ErrorController(ApplicationEventPublisher eventPublisher) {
        this.eventPublisher = eventPublisher;
    }

    @ExceptionHandler(LoginRequiredException.class)
    @ResponseStatus(UNAUTHORIZED)
    public void handleLoginRequireException(LoginRequiredException e) {
        eventPublisher.publishEvent(PermissionFailureEvent.failureEvent(e.getMessage()));
    }

    @ExceptionHandler(AdminRequiredException.class)
    @ResponseStatus(NOT_FOUND)
    public void handleAdminRequiredException(AdminRequiredException e) {
        final String errorMessage = e.getMessage();
        final UserModel currentUser = this.getCurrentUser();

        if (currentUser != null) {
            eventPublisher.publishEvent(PermissionFailureEvent.failureEvent(currentUser, errorMessage));
        } else {
            eventPublisher.publishEvent(PermissionFailureEvent.failureEvent(errorMessage));
        }
    }
}