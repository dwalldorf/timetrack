package com.dwalldorf.timetrack.tools;

import com.dwalldorf.timetrack.tools.commands.CreateTestDataCommand;
import com.dwalldorf.timetrack.tools.commands.RemoveTestDataCommand;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Application {

    private static final Logger logger = LoggerFactory.getLogger(Application.class);

    public static void main(String[] args) throws ParseException {
        Options options = new Options();
        HelpFormatter formatter = new HelpFormatter();

        options.addOption(Option.builder(CreateTestDataCommand.NAME)
                                .desc("Create a set of test data")
                                .hasArg()
                                .build())
               .addOption(Option.builder(RemoveTestDataCommand.NAME)
                                .desc("Wipe all test data")
                                .build())
               .addOption(Option.builder("profile")
                                .longOpt("--spring.profiles.active")
                                .build());

        formatter.printHelp("sample", options);

        SpringApplication.run(Application.class, args);
    }
}