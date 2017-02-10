package com.dwalldorf.timetrack.tools.commands;

import com.dwalldorf.timetrack.model.stub.UserStub;
import javax.inject.Inject;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class CreateTestDataCommand extends AbstractCommand {

    public static final String NAME = "testData";

    private static final Logger logger = LoggerFactory.getLogger(CreateTestDataCommand.class);

    private static final Options options;

    static {
        options = new Options();
        options.addOption(Option.builder("uc")
                                .argName("userCount")
                                .hasArg()
                                .desc("Amount of users to create")
                                .build())
               .addOption(Option.builder("wl")
                                .argName("worklog")
                                .hasArg()
                                .desc("Create worklog entries for users")
                                .build()
               );
    }

    private final UserStub userStub;

    @Inject
    public CreateTestDataCommand(UserStub userStub) {
        this.userStub = userStub;
    }

    @Override
    public void run(CommandLine cmd) {
        logger.info("CreateTestDataCommand");
    }

    @Override
    protected Options getOptions() {
        return options;
    }
}