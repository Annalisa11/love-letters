package de.annalisa.loveletters.commands.impl;

import de.annalisa.loveletters.Game;
import de.annalisa.loveletters.Player;
import de.annalisa.loveletters.commands.Command;

import java.util.List;

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
        List<Player> players = game.getPlayers();
        List<Player> activePlayers = game.getActivePlayers();

        for(Player player: players){
            System.out.println(((activePlayers.contains(player) ? "\uD83C\uDF1F " : "\uD83D\uDC94 " ) + player.getName() + " " + (activePlayers.contains(player) ? "(active)" : "(knocked out)") + (player.isImmune() ? "  [immune] \uD83D\uDD12" : "")));
        }
        return true;
    }
}
