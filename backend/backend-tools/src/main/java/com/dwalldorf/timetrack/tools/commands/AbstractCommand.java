package com.dwalldorf.timetrack.tools.commands;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.springframework.boot.CommandLineRunner;

public abstract class AbstractCommand implements CommandLineRunner {

    private static final CommandLineParser parser = new DefaultParser();

    private CommandLine cmd;

    @Override
    public void run(String... args) throws Exception {
        if (invoked(getCmdName())) {
            this.run();
        }
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