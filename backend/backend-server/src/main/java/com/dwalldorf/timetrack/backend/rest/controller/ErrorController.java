package com.dwalldorf.timetrack.backend.rest.controller;

import static org.springframework.http.HttpStatus.CONFLICT;
import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.http.HttpStatus.UNAUTHORIZED;

import com.dwalldorf.timetrack.backend.event.IdentityConflictEvent;
import com.dwalldorf.timetrack.backend.event.PermissionFailureEvent;
import com.dwalldorf.timetrack.backend.exception.IdentityConflictException;
import com.dwalldorf.timetrack.backend.exception.LoginRequiredException;
import com.dwalldorf.timetrack.backend.exception.NotFoundException;
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
    public void handleLoginRequireException(final LoginRequiredException e) {
        eventPublisher.publishEvent(new PermissionFailureEvent(e.getMessage()));
    }

    @ExceptionHandler(IdentityConflictException.class)
    @ResponseStatus(CONFLICT)
    public void handleIdentityConflictException(final IdentityConflictException e) {
        eventPublisher.publishEvent(new IdentityConflictEvent(e.getMessage()));
    }

    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(NOT_FOUND)
    public void handleNotFoundException() { }
}