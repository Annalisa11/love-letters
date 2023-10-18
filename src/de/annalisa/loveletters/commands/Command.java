package de.annalisa.loveletters.commands;

public interface Command {
    String getCommand();

    String[] getUsages();

    String getDescription();

    void execute();
}
