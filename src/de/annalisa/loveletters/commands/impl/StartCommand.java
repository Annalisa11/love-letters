package de.annalisa.loveletters.commands.impl;

import de.annalisa.loveletters.Game;
import de.annalisa.loveletters.commands.Command;

public class StartCommand implements Command {

    @Override
    public String getCommand() {
        return "start";
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
    public boolean execute(Game game) {
        throw new UnsupportedOperationException();

    }
}
