package de.annalisa.loveletters.cards;

import de.annalisa.loveletters.Game;
import de.annalisa.loveletters.Player;

import java.util.List;

public class King extends Card{
    public King() {
        super("King", 6, "Trade hands with another player your choice");
    }

    @Override
    public void applyEffect(Game game) {
        Player currentPlayer = game.getCurrentPlayer();
        List<Player> otherPlayers = game.getOtherPlayersExcludingCurrent(currentPlayer.getName());
        int playerNumber = game.choosePlayerForEffect(otherPlayers, "Choose a player swap your card with: ");

        Player chosenPlayer;
        if(playerNumber == -1){
            return;
        } else {
            chosenPlayer = otherPlayers.get(playerNumber-1);
        }

        //get Cards to swap
        Card otherPlayersCard = chosenPlayer.getHand().get(0);
        int yourCardIndex = Game.getIndexOfOtherCardOnHand(currentPlayer, 6); // what?

        Card yourCard = currentPlayer.getHand().get(yourCardIndex);

        //set the Cards in each others hands
        chosenPlayer.getHand().set(0, yourCard);
        currentPlayer.getHand().set(yourCardIndex, otherPlayersCard);
        System.out.println("You have successfully swapped cards!");
    }
}
