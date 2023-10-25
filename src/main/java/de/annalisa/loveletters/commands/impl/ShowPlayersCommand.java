package de.annalisa.loveletters.commands.impl;

import de.annalisa.loveletters.Game;
import de.annalisa.loveletters.Player;
import de.annalisa.loveletters.commands.Command;

public class ShowPlayersCommand implements Command {
    @Override
    public String getCommand() {
        return "showPlayers";
    }

    @Override
    public String[] getUsages() {
        return new String[0];
    }

    @Override
    public String getDescription() {
        return "With showPlayers you can see every player's names and if they are still active or have been knocked out in the current round. (In-game only)";
    }

    @Override
    public boolean execute(Game game, boolean inGame) {
        if(!inGame){
            System.out.println("❗ you have start the game to see the players.");
            return true;
        }

        for(Player player: game.getPlayers()){
            System.out.println(((game.getActivePlayers().contains(player) ? "\uD83C\uDF1F " : "\uD83D\uDC94 " ) + player.getName() + " " + (game.getActivePlayers().contains(player) ? "(active)" : "(knocked out)")));
        }
        return true;
    }
}
