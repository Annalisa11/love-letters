/**
 * The "showHand" command allows a player to view the cards they have in their hand without taking any other actions.
 * <p>
 * When executed, this command is used within the context of a game to display the cards in the current player's hand.
 * It provides an overview of the player's hand without requiring them to play a card or perform any other actions.
 */
package de.annalisa.loveletters.commands.impl;

import de.annalisa.loveletters.Game;
import de.annalisa.loveletters.Player;
import de.annalisa.loveletters.commands.Command;
import de.annalisa.loveletters.utils.StringHelper;

public class ShowHandCommand implements Command {
    @Override
    public String getCommand() {
        return "showHand";
    }

    @Override
    public String[] getUsages() {
        return new String[0];
    }

    /**
     * Get the description of the "showHand" command.
     *
     * @return A brief description of the "showHand" command and its purpose in the Love Letter game.
     */
    @Override
    public String getDescription() {
        return "With showHand you can see which cards you have on your hand without having to do play one or do anything else. (In-game only)";
    }

    /**
     * Execute the "showHand" command to display the cards in the current player's hand during the game.
     * It provides a convenient way for the player to view their hand without taking any other actions.
     *
     * @param game   The current game instance where the command will be executed.
     * @param inGame A boolean indicating whether the command is executed during a game session.
     * @return {@code true} to indicate that the loop should ask for more input (because of invalid input), or {@code false}
     * to stop scanning for more input.
     */
    @Override
    public boolean execute(Game game, boolean inGame) {
        if (!inGame) {
            System.out.println("❗ You have to start the game to view your cards.");
            return true;
        }
        Player player = game.getCurrentPlayer();
        System.out.println("You have these cards on your hand:");
        System.out.println(StringHelper.printCardsBesideEachOther(player.getHand()));
        return true;
    }
}
