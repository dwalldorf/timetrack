package com.dwalldorf.timetrack.tools.commands;

import com.dwalldorf.timetrack.tools.Application;
import java.util.Arrays;
import java.util.stream.Collectors;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.ParseException;
import org.springframework.boot.CommandLineRunner;

public abstract class AbstractCommand implements CommandLineRunner {

    private static final CommandLineParser parser = new DefaultParser();

    private CommandLine cmd;

    @Override
    public void run(String... args) throws Exception {
        CommandLine cmd = getCmd(args);

        if (invoked(getCmdName())) {
            this.run();
        }
    }

    private CommandLine getCmd(String... args) throws ParseException {
        args = Arrays.stream(args)
                     .filter(str -> (!str.startsWith("--spring") &&
                             !str.startsWith("--debug"))
                     )
                     .collect(Collectors.toList())
                     .toArray(new String[0]);

        this.cmd = parser.parse(Application.OPTIONS, args);
        return this.cmd;
    }

    private boolean invoked(String cmdName) {
        return cmd.hasOption(cmdName);
    }

    final Integer getOptionValueInt(String name, Integer defaultValue) {
        String optionValueString = cmd.getOptionValue(name, defaultValue.toString());
        return Integer.valueOf(optionValueString);
    }

    protected abstract void run();

    protected abstract String getCmdName();
}