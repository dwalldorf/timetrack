package com.dwalldorf.timetrack.tools.commands;

import org.apache.commons.cli.CommandLine;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class RemoveTestDataCommand extends AbstractCommand {

    public static final String CMD_NAME = "removeTestData";

    private static final Logger logger = LoggerFactory.getLogger(RemoveTestDataCommand.class);

    @Override
    public void run(CommandLine cmd) {
        if (!invoked(CMD_NAME)) {
            return;
        }

        logger.info("Wiping test data.");
    }
}