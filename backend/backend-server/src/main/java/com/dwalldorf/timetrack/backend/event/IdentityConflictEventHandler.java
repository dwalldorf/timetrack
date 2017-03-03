package com.dwalldorf.timetrack.backend.event;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.Marker;
import org.slf4j.MarkerFactory;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
public class IdentityConflictEventHandler {

    private static final Logger logger = LoggerFactory.getLogger(IdentityConflictEventHandler.class);
    private static final Marker marker = MarkerFactory.getMarker("identity_conflict");

    @Async
    @EventListener(IdentityConflictEvent.class)
    public void onPermissionFailureEvent(final IdentityConflictEvent event) {
        logger.info(marker, event.getMessage());
    }
}