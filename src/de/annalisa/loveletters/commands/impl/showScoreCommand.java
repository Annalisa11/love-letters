package de.annalisa.loveletters.commands.impl;

import de.annalisa.loveletters.Game;
import de.annalisa.loveletters.Player;
import de.annalisa.loveletters.commands.Command;

public class showScoreCommand implements Command {
    @Override
    public String getCommand() {
        return "showScore";
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
        System.out.println("SCORE: " + player.getScore());
        return true;
    }


}
