package de.annalisa.loveletters.cards;

import de.annalisa.loveletters.Game;
import de.annalisa.loveletters.Player;

import java.util.ArrayList;
import java.util.List;

public class Guard extends Card{
    public Guard() {
        super("Guard", 1, "Name a non-Guard card and choose another player. If that player has that card, he or she is out of the round.");
    }

    @Override
    public void applyEffect(Game game){
        Player currentPlayer = game.getCurrentPlayer();

        List<Player> otherPlayers = game.getOtherPlayersExcludingCurrent(currentPlayer.getName());
        int cardNumber = game.validateInputNumbers(new Integer[]{2,3,4,5,6,7,8}, "Choose a card number from 2 - 8.");
        int playerNumber = game.choosePlayerForEffect(otherPlayers, "Choose a player to potentially knock out: ");

        Player chosenPlayer;
        if(playerNumber == -1){
            System.out.println("you can't choose yourself for guard effect. Effect is not applied");
            return;
        } else {
            chosenPlayer = otherPlayers.get(playerNumber-1);
        }

        if(chosenPlayer.getHand().stream().anyMatch(card -> card.getCloseness() == cardNumber)) {
            System.out.println("You successfully knocked out " + chosenPlayer.getName() + "!");
            game.knockOutPlayer(chosenPlayer, currentPlayer);
        } else {
            System.out.println("Unfortunately for you, your chosen player doesn't have your chosen card on their hand. Your Guard has no effect.");
        }
    }
}