package com.dwalldorf.timetrack.tools.commands;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.Options;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class RemoveTestDataCommand extends AbstractCommand {

    public static final String NAME = "removeTestData";

    private static final Logger logger = LoggerFactory.getLogger(RemoveTestDataCommand.class);

    private static final Options options = new Options();

    @Override
    public void run(CommandLine cmd) {
        logger.info("RemoveTestDataCommand");

//        if (NAME.equals(args[0])) {
//            System.out.println();
//            System.out.println();
//            System.out.println("RemoveTestDataCommand");
//            System.out.println();
//            System.out.println();
//        }
    }

    @Override
    protected Options getOptions() {
        return options;
    }
}
