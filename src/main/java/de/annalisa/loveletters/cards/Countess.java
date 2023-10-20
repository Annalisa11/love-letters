package de.annalisa.loveletters.cards;

import de.annalisa.loveletters.Game;

public class Countess extends Card{
    public Countess() {
        super("Countess", 7, "If you have this card and the King or Prince in your hand, you must discard this card.");
    }

    @Override
    public void applyEffect(Game game) {
        System.out.println("You have discarded the countess...");
    }
}
