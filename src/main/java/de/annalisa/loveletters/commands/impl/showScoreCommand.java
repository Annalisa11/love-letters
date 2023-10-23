package de.annalisa.loveletters.commands.impl;

import de.annalisa.loveletters.Game;
import de.annalisa.loveletters.Player;
import de.annalisa.loveletters.commands.Command;

import java.util.stream.Collectors;

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
        return "With showScore you can view the number of tokens of all players. Only valid when playing the game.";
    }

    @Override
    public boolean execute(Game game, boolean inGame) {
        if(!inGame){
            System.out.println("you have to start the game to see your score");
            return true;
        }
        System.out.println("TOKENS: " + game.getPlayers().stream().map(player -> player.getName() + " (" + player.getLoveToken() + ")").collect(Collectors.joining("   ")));
        return true;
    }


}
