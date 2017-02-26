package com.dwalldorf.timetrack.tools;

import static com.dwalldorf.timetrack.tools.commands.CreateTestDataCommand.CMD_USER_COUNT_OPT_DEFAULT;
import static com.dwalldorf.timetrack.tools.commands.CreateTestDataCommand.CMD_USER_COUNT_OPT_NAME;
import static com.dwalldorf.timetrack.tools.commands.CreateTestDataCommand.CMD_WORKLOG_COUNT_OPT_DEFAULT;

import com.dwalldorf.timetrack.tools.commands.CreateTestDataCommand;
import com.dwalldorf.timetrack.tools.commands.RemoveTestDataCommand;
import java.util.Arrays;
import java.util.List;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.OptionGroup;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Application {

    public static final Options OPTIONS = new Options();

    static {
        OptionGroup optionGroup = new OptionGroup();

        optionGroup
                .addOption(Option.builder("t")
                                 .desc("Create a set of test data")
                                 .longOpt(CreateTestDataCommand.CMD_NAME)
                                 .build())
                .addOption(Option.builder("r")
                                 .desc("Wipe all test data")
                                 .longOpt(RemoveTestDataCommand.CMD_NAME)
                                 .build());

        OPTIONS.addOptionGroup(optionGroup)
               .addOption(Option.builder("u")
                                .desc("Amount of test users to create. Default: " + CMD_USER_COUNT_OPT_DEFAULT)
                                .longOpt(CMD_USER_COUNT_OPT_NAME)
                                .hasArg()
                                .type(Integer.class)
                                .build())
               .addOption(Option.builder("w")
                                .desc("Amount of worklog entries to create max per user. Default: " + CMD_WORKLOG_COUNT_OPT_DEFAULT)
                                .longOpt(CreateTestDataCommand.CMD_WORKLOG_COUNT_OPT_NAME)
                                .hasArg()
                                .type(Integer.class)
                                .build());
    }

    public static void main(String[] args) throws ParseException {
        List<String> argList = Arrays.asList(args);
        if (argList.size() == 0 || argList.contains("--help") || argList.contains("-h")) {
            HelpFormatter formatter = new HelpFormatter();
            formatter.printHelp("sample", OPTIONS);
            return;
        }

        SpringApplication.run(Application.class, args);
    }
}