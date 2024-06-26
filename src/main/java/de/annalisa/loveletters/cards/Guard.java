/**
 * The Guard card in the Love Letter game.
 * Guard is a card with a value of 1 and the following effect: "Name a non-Guard card and choose another player.
 * If that player has that card, he or she is out of the round."
 */
package de.annalisa.loveletters.cards;

import de.annalisa.loveletters.Game;
import de.annalisa.loveletters.Player;
import de.annalisa.loveletters.utils.InputHelper;

import java.util.List;

public class Guard extends Card {
    public Guard() {
        super("Guard", 1, "Name a non-Guard card and choose another player. If that player has that card, he or she is out of the round.");
    }

    /**
     * Applies the effect of the Guard card, allowing the player to name a card and potentially knock out another player.
     * <p>
     * If the chosen player the named card, they are eliminated from the round.
     * If the chosen player does not have the named card, the Guard has no effect.
     *
     * @param game The Love Letter game in progress.
     */
    @Override
    public void applyEffect(Game game) {
        Player currentPlayer = game.getCurrentPlayer();

        List<Player> otherPlayers = game.getOtherPlayersExcludingCurrent(currentPlayer.getName());
        int cardNumber = InputHelper.validateInputNumbers(new Integer[]{2, 3, 4, 5, 6, 7, 8}, "Choose a card number from 2 - 8.\nPriest (2), Baron (3), Handmaid (4), Prince (5), King (6), Countess (7), Princess (8)");
        int playerNumber = game.choosePlayerForEffect(otherPlayers, "Choose a player to potentially knock out: ");

        Player chosenPlayer;
        if (playerNumber == -1) {
            return;
        } else {
            chosenPlayer = otherPlayers.get(playerNumber - 1);
        }

        if (chosenPlayer.getHand().stream().anyMatch(card -> card.getCloseness() == cardNumber)) {
            System.out.println("You successfully knocked out " + chosenPlayer.getName() + "!");
            game.knockOutPlayer(chosenPlayer, currentPlayer);
        } else {
            System.out.println("Unfortunately for you, your chosen player doesn't have your chosen card on their hand. Your Guard has no effect.");
        }
    }
}
