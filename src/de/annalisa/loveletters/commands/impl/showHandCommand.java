package de.annalisa.loveletters.commands.impl;

import de.annalisa.loveletters.Game;
import de.annalisa.loveletters.Player;
import de.annalisa.loveletters.commands.Command;

public class showHandCommand implements Command {
    @Override
    public String getCommand() {
        return "showHand";
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
        Player player = game.currentPlayer;
        System.out.println("You have these cards on your hand:");
        System.out.println(player.getHand());
        return true;
    }


}
