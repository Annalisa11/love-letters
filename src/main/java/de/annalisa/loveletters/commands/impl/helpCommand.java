package de.annalisa.loveletters.commands.impl;

import de.annalisa.loveletters.Game;
import de.annalisa.loveletters.commands.Command;

import java.util.List;

public class helpCommand implements Command {
    @Override
    public String getCommand() {
        return "help";
    }

    @Override
    public String[] getUsages() {
        return new String[0];
    }

    @Override
    public String getDescription() {
        return "With help you can view all the commands there are and how or when you can use them.";
    }

    @Override
    public boolean execute(Game game) {
        System.out.println("You can use these commands:");
        List<Command> commands = game.getCommandManager().getCommands();
        for(Command command : commands){
            System.out.println("\\" + command.getCommand() + "   ->   " + command.getDescription());
        }
        return true;
    }
}
