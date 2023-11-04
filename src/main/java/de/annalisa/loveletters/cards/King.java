/**
 * The King card in the Love Letter game.
 * King is a card with a value of 6 and the following effect: "Trade hands with another player of your choice."
 */
package de.annalisa.loveletters.cards;

import de.annalisa.loveletters.Game;
import de.annalisa.loveletters.Player;

import java.util.List;

public class King extends Card {
    public King() {
        super("King", 6, "Trade hands with another player of your choice.");
    }

    /**
     * Applies the effect of the King card.
     * <p>
     * When played, the King card allows the current player to choose another player and swap hands with that player.
     *
     * @param game The Love Letter game in progress.
     */
    @Override
    public void applyEffect(Game game) {
        Player currentPlayer = game.getCurrentPlayer();
        List<Player> otherPlayers = game.getOtherPlayersExcludingCurrent(currentPlayer.getName());
        int playerNumber = game.choosePlayerForEffect(otherPlayers, "Choose a player to swap your card with: ");

        Player chosenPlayer;
        if (playerNumber == -1) {
            return;
        } else {
            chosenPlayer = otherPlayers.get(playerNumber - 1);
        }

        //get Cards to swap
        Card otherPlayersCard = chosenPlayer.getHand().get(0);
        int yourCardIndex = Game.getIndexOfOtherCardOnHand(currentPlayer, 6);

        Card yourCard = currentPlayer.getHand().get(yourCardIndex);

        //set the Cards in each others hands
        chosenPlayer.getHand().set(0, yourCard);
        currentPlayer.getHand().set(yourCardIndex, otherPlayersCard);
        System.out.println("You have successfully swapped cards!");
    }
}
