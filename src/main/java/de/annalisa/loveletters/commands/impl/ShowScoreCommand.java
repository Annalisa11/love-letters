package de.annalisa.loveletters.commands.impl;

import de.annalisa.loveletters.Game;
import de.annalisa.loveletters.commands.Command;

import java.util.stream.Collectors;

public class ShowScoreCommand implements Command {

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
        return "With showScore you can view the number of tokens of all players. (In-game only)";
    }

    @Override
    public boolean execute(Game game, boolean inGame) {
        if(!inGame){
            System.out.println("❗ you have start the game to view all players' scores.");
            return true;
        }
        System.out.println("TOKENS: \n" + game.getPlayers().stream().map(player -> "\uD83D\uDC9D " + player.getName() + " (" + player.getLoveToken() + ")").collect(Collectors.joining("\n")));
        return true;
    }


}
