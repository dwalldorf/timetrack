package com.dwalldorf.timetrack.tools.commands;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.springframework.boot.CommandLineRunner;

public abstract class AbstractCommand implements CommandLineRunner {

    protected final CommandLineParser parser = new DefaultParser();

    @Override
    public void run(String... args) throws Exception {
        CommandLine cmd = getCmd(args);
        this.run(cmd);
    }

    private CommandLine getCmd(String... args) throws ParseException {
        return parser.parse(this.getOptions(), args, true);
    }

    protected abstract Options getOptions();

    protected abstract void run(CommandLine cmd);
}