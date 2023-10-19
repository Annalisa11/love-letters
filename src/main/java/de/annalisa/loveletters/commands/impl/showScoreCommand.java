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
        return "With showScore you can view your personal score, i.e. the sum of your card's closeness values. Only valid when playing the game.";
    }

    @Override
    public boolean execute(Game game) {
        Player player = game.currentPlayer;
        System.out.println("SCORE: " + player.getScore());
        return true;
    }


}
