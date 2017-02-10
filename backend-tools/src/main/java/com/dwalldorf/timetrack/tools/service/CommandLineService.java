package com.dwalldorf.timetrack.tools.service;

import org.springframework.stereotype.Service;

@Service
public class CommandLineService {

    private Boolean foundCommand = false;

    public Boolean getFoundCommand() {
        return foundCommand;
    }

    public void setFoundCommand(Boolean foundCommand) {
        this.foundCommand = foundCommand;
    }
}