package com.dwalldorf.timetrack.backend.event;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.Marker;
import org.slf4j.MarkerFactory;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
public class PermissionFailureEventHandler {

    private static final Logger logger = LoggerFactory.getLogger(PermissionFailureEventHandler.class);
    private static final Marker marker = MarkerFactory.getMarker("permission_failure");

    @Async
    @EventListener(PermissionFailureEvent.class)
    public void onPermissionFailureEvent(final PermissionFailureEvent event) {
        logger.info(marker, event.getMessage());
    }
}