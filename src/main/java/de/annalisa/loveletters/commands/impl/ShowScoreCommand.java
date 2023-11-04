/**
 * The "showScore" command allows a player to view the number of love tokens (scores) of all players in the game.
 *
 * When executed, this command is used within the context of a game to display the love tokens (scores) of all players.
 * It provides an overview of each player's name and their current score in the form of love tokens.
 */
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

    /**
     * Get the description of the "showScore" command.
     *
     * @return A brief description of the "showScore" command and its purpose in the Love Letter game.
     */
    @Override
    public String getDescription() {
        return "With 'showScore,' you can view the number of love tokens (scores) of all players. (In-game only)";
    }

    /**
     * Execute the "showScore" command to display the number of love tokens (scores) of all players in the current game.
     * The command provides a list of player names and their corresponding love token (score) counts.
     *
     * @param game   The current game instance where the command will be executed.
     * @param inGame A boolean indicating whether the command is executed during a game session.
     * @return {@code true} to indicate that the loop should keep asking for more input.
     */
    @Override
    public boolean execute(Game game, boolean inGame) {
        if (!inGame) {
            System.println("❗ You have to start the game to view all players' scores.");
            return true;
        }
        System.out.println("TOKENS:\n" + game.getPlayers().stream()
                .map(player -> "\uD83D\uDC9D " + player.getName() + " (" + player.getLoveToken() + ")")
                .collect(Collectors.joining("\n")));
        return true;
    }
}
