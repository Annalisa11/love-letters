package de.annalisa.loveletters.commands.impl;

import de.annalisa.loveletters.Game;
import de.annalisa.loveletters.Player;
import de.annalisa.loveletters.commands.Command;

public class playCardCommand implements Command {
    @Override
    public String getCommand() {
        return "playCard";
    }

    @Override
    public String[] getUsages() {
        return new String[0];
    }

    @Override
    public String getDescription() {
        return null;
    }

    @Override
    public boolean execute(Game game) {
        Player player = game.currentPlayer;
        int chosenCard;
        if(game.isSpecificCardOnHand(player, 7) && (game.isSpecificCardOnHand(player, 6) || game.isSpecificCardOnHand(player, 5)) ){
            System.out.println("The Countess doesn't want to be near the king or the prince.. you have to discard her!");
            chosenCard = game.getIndexOfCardInHand(player, 7);
        } else {
            chosenCard = game.chooseCardToPlay(player) - 1;
        }
        game.applyEffect(player.getHand().get(chosenCard), player);
        player.removeCardFromHand(chosenCard);
        player.updateScore();
        return false;
    }
}
