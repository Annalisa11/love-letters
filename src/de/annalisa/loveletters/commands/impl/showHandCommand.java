package de.annalisa.loveletters.commands.impl;

import de.annalisa.loveletters.commands.Command;

public class showHandCommand implements Command {
    @Override
    public String getCommand() {
        return "showCard";
    }

    @Override
    public String[] getUsages() {
        return new String[0];
    }

    @Override
    public String getDescription() {
        return null;
    }

    @Override
    public void execute() {

    }
}
