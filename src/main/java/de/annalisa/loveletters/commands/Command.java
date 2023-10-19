package de.annalisa.loveletters.commands;

import de.annalisa.loveletters.Game;

public interface Command {
    String getCommand();

    String[] getUsages();

    String getDescription();

    boolean execute(Game game);
}
