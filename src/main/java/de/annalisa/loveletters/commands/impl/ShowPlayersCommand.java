/**
 * The "showPlayers" command allows a player to view the names of all players, their active status (whether they have been knocked out or are still active in the current round), and their immunity status.
 * <p>
 * When executed, this command is used within the context of a game to display information about all the players.
 * It provides an overview of each player's name, active status, and immunity status.
 */
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

    /**
     * Get the description of the "showPlayers" command.
     *
     * @return A brief description of the "showPlayers" command and its purpose in the Love Letter game.
     */
    @Override
    public String getDescription() {
        return "With showPlayers you can see every player's names and if they are still active or have been knocked out in the current round. (In-game only)";
    }

    /**
     * Execute the "showPlayers" command to display information about all the players in the current game.
     * The command provides a list of player names, their active or knocked out status, and their immunity status.
     *
     * @param game   The current game instance where the command will be executed.
     * @param inGame A boolean indicating whether the command is executed during a game session.
     * @return {@code true} to indicate that the loop should keep asking for more input.
     */
    @Override
    public boolean execute(Game game, boolean inGame) {
        if (!inGame) {
            System.out.println("❗ you have start the game to see the players.");
            return true;
        }
        List<Player> players = game.getPlayers();
        List<Player> activePlayers = game.getActivePlayers();

        for (Player player : players) {
            System.out.println(((activePlayers.contains(player) ? "\uD83C\uDF1F " : "\uD83D\uDC94 ") + player.getName() + " " + (activePlayers.contains(player) ? "(active)" : "(knocked out)") + (player.isImmune() ? "  [immune] \uD83D\uDD12" : "")));
        }
        return true;
    }
}
