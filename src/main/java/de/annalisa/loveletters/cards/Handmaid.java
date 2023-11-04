/**
 * The Handmaid card in the Love Letter game.
 * Handmaid is a card with a value of 4 and the following effect: "Until your next turn, ignore all effects from other players' cards."
 */
package de.annalisa.loveletters.cards;

import de.annalisa.loveletters.Game;

public class Handmaid extends Card {
    public Handmaid() {
        super("Handmaid", 4, "Until your next turn, ignore all effects from other players' cards.");
    }

    /**
     * Applies the effect of the Handmaid card, granting the player immunity from other players' card effects until their next turn.
     * <p>
     * When played, the Handmaid card grants the current player immunity from the effects of other players' cards until their next turn.
     * This immunity protects the player from being targeted or affected by other card effects during this period.
     *
     * @param game The Love Letter game in progress.
     */
    @Override
    public void applyEffect(Game game) {
        System.out.println("NOTE \uD83D\uDD12: You are now immune to other effects for one round.");
        game.getCurrentPlayer().setImmune();
    }
}
