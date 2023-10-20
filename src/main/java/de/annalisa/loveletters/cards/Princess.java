package de.annalisa.loveletters.cards;

import de.annalisa.loveletters.Game;
import de.annalisa.loveletters.Player;

public class Princess extends Card{
    public Princess() {
        super("Princess", 8, "If you discard this card, you are out of the round");
    }

    @Override
    public void applyEffect(Game game) {
        Player currentPlayer = game.getCurrentPlayer();
        System.out.println("You discarded the Princess? HOW DARE YOU!? \nYou have been knocked out!");
        game.knockOutPlayer(currentPlayer, currentPlayer);
    }
}
