/**
 * The "playCard" command allows a player to play a card that they have in their hand during the game.
 * <p>
 * When executed, this command is used in the context of a game to play a card from the current player's hand.
 * The command checks for specific card interactions, such as the Countess card's requirement to be discarded
 * when the player also has the King or Prince in their hand.
 */
package de.annalisa.loveletters.commands.impl;

import de.annalisa.loveletters.Game;
import de.annalisa.loveletters.Player;
import de.annalisa.loveletters.commands.Command;
import de.annalisa.loveletters.utils.StringHelper;

public class PlayCardCommand implements Command {
    @Override
    public String getCommand() {
        return "playCard";
    }

    @Override
    public String[] getUsages() {
        return new String[0];
    }

    /**
     * Get the description of the "playCard" command.
     *
     * @return A brief description of the "playCard" command and its purpose in the Love Letter game.
     */
    @Override
    public String getDescription() {
        return "With playCard you can play a card that you have on your hand. (In-game only)";
    }

    /**
     * Execute the "playCard" command to play a card from the current player's hand during the game.
     * The command checks for specific card interactions, such as the Countess card's requirement to be discarded
     * when the player also has the King or Prince in their hand.
     *
     * @param game   The current game instance where the command will be executed.
     * @param inGame A boolean indicating whether the command is executed during a game session.
     * @return {@code true} if the command execution indicates that the loop should ask for more input (invalid input).
     * Otherwise, {@code false} is returned to indicate that the loop should stop scanning for input.
     */
    @Override
    public boolean execute(Game game, boolean inGame) {
        if (!inGame) {
            System.out.println("❗ you have to start the game to play a card");
            return true;
        }
        Player player = game.getCurrentPlayer();
        int chosenCard;
        if (game.isSpecificCardOnHand(player, 7) && (game.isSpecificCardOnHand(player, 6) || game.isSpecificCardOnHand(player, 5))) {
            System.out.println("Oh no! Look at your cards!");
            System.out.println(StringHelper.printCardsBesideEachOther(player.getHand()));
            System.out.println("The Countess doesn't want to be near the king or the prince.. you have to discard her!");
            chosenCard = Game.getIndexOfCardInHand(player, 7);
        } else {
            chosenCard = game.chooseCardToPlay(player) - 1;
        }
        player.getHand().get(chosenCard).applyEffect(game);
        player.removeCardFromHand(chosenCard);
        player.updateScore();
        return false;
    }
}
