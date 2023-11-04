/**
 * The Princess card in the Love Letter game.
 * Princess is a card with a value of 8 and the following effect: "If you discard this card, you are out of the round."
 */
package de.annalisa.loveletters.cards;

import de.annalisa.loveletters.Game;
import de.annalisa.loveletters.Player;

public class Princess extends Card {
    public Princess() {
        super("Princess", 8, "If you discard this card, you are out of the round");
    }

    /**
     * Applies the effect of the Princess card.
     * <p>
     * If the player discards this card or is forced to discard it, they get knocked out.
     *
     * @param game The Love Letter game in progress.
     */
    @Override
    public void applyEffect(Game game) {
        Player currentPlayer = game.getCurrentPlayer();
        System.out.println("You discarded the Princess? HOW DARE YOU! \nYou have been knocked out!");
        game.knockOutPlayer(currentPlayer, currentPlayer);
    }
}
