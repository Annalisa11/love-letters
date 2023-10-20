package de.annalisa.loveletters.cards;

import de.annalisa.loveletters.Game;

public class Handmaid extends Card{
    public Handmaid() {
        super("Handmaid", 4, "Until your next turn, ignore all effects from other players' cards.");
    }

    @Override
    public void applyEffect(Game game) {
        System.out.println("NOTE: You are now immune to other effects for one round.");
        game.getCurrentPlayer().setImmune();
    }
}
