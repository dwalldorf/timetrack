package com.dwalldorf.timetrack.backend.rest.controller;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

import com.dwalldorf.timetrack.backend.BaseTest;
import com.dwalldorf.timetrack.backend.event.IdentityConflictEvent;
import com.dwalldorf.timetrack.backend.event.PermissionFailureEvent;
import com.dwalldorf.timetrack.backend.exception.IdentityConflictException;
import com.dwalldorf.timetrack.backend.exception.LoginRequiredException;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.context.ApplicationEventPublisher;

public class ErrorControllerTest extends BaseTest {

    private static final String TEST_MESSAGE = "some message";

    private ApplicationEventPublisher mockEventPublisher;
    private ErrorController controller;

    @Override
    protected void setUp() {
        this.mockEventPublisher = mock(ApplicationEventPublisher.class);
        this.controller = new ErrorController(mockEventPublisher);
    }

    @Test
    public void handleLoginRequireException() throws Exception {
        ArgumentCaptor<PermissionFailureEvent> eventCaptor = ArgumentCaptor.forClass(PermissionFailureEvent.class);

        controller.handleLoginRequiredException(new LoginRequiredException(TEST_MESSAGE));

        verify(mockEventPublisher).publishEvent(eventCaptor.capture());

        PermissionFailureEvent capturedEvent = eventCaptor.getValue();
        assertEquals(TEST_MESSAGE, capturedEvent.getMessage());
    }

    @Test
    public void handleIdentityConflictException() throws Exception {
        ArgumentCaptor<IdentityConflictEvent> eventCaptor = ArgumentCaptor.forClass(IdentityConflictEvent.class);

        controller.handleIdentityConflictException(new IdentityConflictException(TEST_MESSAGE));

        verify(mockEventPublisher).publishEvent(eventCaptor.capture());

        IdentityConflictEvent capturedEvent = eventCaptor.getValue();
        assertEquals(TEST_MESSAGE, capturedEvent.getMessage());
    }
}