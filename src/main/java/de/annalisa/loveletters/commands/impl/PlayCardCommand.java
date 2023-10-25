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

    @Override
    public String getDescription() {
        return "With playCard you can play a card that you have on your hand. (In-game only)";
    }

    @Override
    public boolean execute(Game game, boolean inGame) {
        if(!inGame){
            System.out.println("❗ you have to start the game to play a card");
            return true;
        }
        Player player = game.getCurrentPlayer();
        int chosenCard;
        if(game.isSpecificCardOnHand(player, 7) && (game.isSpecificCardOnHand(player, 6) || game.isSpecificCardOnHand(player, 5)) ){
            System.out.println("Oh no! Look at your cards!");
            StringHelper.printCardsBesideEachOther(player.getHand());
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
