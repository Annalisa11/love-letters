/**
 * The Baron card in the Love Letter game.
 * Baron is a card with a value of 3 and the following effect: "You and another player secretly compare hands.
 * The player with the lower value is out of the round."
 */
package de.annalisa.loveletters.cards;

import de.annalisa.loveletters.Game;
import de.annalisa.loveletters.Player;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Baron extends Card {
    public Baron() {
        super("Baron", 3, "You and another player secretly compare hands. The player with the lower value is out of the round.");
    }

    /**
     * Applies the effect of the Baron card.
     * <p>
     * the score is set to currentScore - 3 before the comparison takes place, so that the score is only compared to the other card on hand.
     * If the chosen player has the named card, the scores of the card on hand of both players get compared. Whoever has the lowest score gets knocked out.
     * If there is a tie, nothing happens.
     *
     * @param game The Love Letter game in progress.
     */
    @Override
    public void applyEffect(Game game) {
        Player currentPlayer = game.getCurrentPlayer();
        List<Player> otherPlayers = game.getOtherPlayersExcludingCurrent(currentPlayer.getName());
        int playerNumber = game.choosePlayerForEffect(otherPlayers, "Choose a player to compare hands with: ");

        Player chosenPlayer;
        if (playerNumber == -1)
            return;
        else
            chosenPlayer = otherPlayers.get(playerNumber - 1);

        currentPlayer.setScore(currentPlayer.getScore() - 3);

        System.out.println("Alright! The scores are:   You (" + currentPlayer.getScore() + ")   " + chosenPlayer.getName() + " (" + chosenPlayer.getScore() + ").");
        ArrayList<Player> winner = new ArrayList<>(Arrays.asList(currentPlayer, chosenPlayer));
        winner.sort(new Player.sortByScore());
        if (winner.get(0).getScore() == winner.get(1).getScore()) {
            System.out.println("You have both the same score... nothing happens :)");
            return;
        }
        Player loser = winner.get(winner.size() - 1);
        System.out.println(loser.getName() + " has the lowest score and is therefore knocked out!");
        game.knockOutPlayer(loser, currentPlayer);
    }
}
