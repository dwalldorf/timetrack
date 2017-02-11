package com.dwalldorf.timetrack.backend.event;

import com.dwalldorf.timetrack.model.UserModel;
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
        UserModel user = event.getUser();
        if (user != null) {
            logPermissionFailure(
                    "{} - caused by: '{}' with userId {}",
                    event.getMessage(),
                    user.getUsername(),
                    user.getId()
            );
        } else {
            logPermissionFailure(event.getMessage());
        }
    }

    private void logPermissionFailure(final String format, final Object... arguments) {
        logger.info(marker, format, arguments);
    }
}