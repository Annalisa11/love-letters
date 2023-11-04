/**
 * The Countess card in the Love Letter game.
 * Countess is a card with a value of 7 and a special rule: "If you have this card and the King or Prince in your hand,
 * you must discard this card."
 * <p>
 * When played, the Countess card does not have a direct effect in the game other than being discarded. If the player
 * also holds the King or Prince card, the Countess card must be discarded. This happens in the playCardCommand.
 */
package de.annalisa.loveletters.cards;

import de.annalisa.loveletters.Game;

public class Countess extends Card {
    public Countess() {
        super("Countess", 7, "If you have this card and the King or Prince in your hand, you must discard this card.");
    }

    /**
     * Applies the effect of the Countess card.
     *
     * @param game The Love Letter game in progress.
     */
    @Override
    public void applyEffect(Game game) {
        System.out.println("You have discarded the countess...");
    }
}
