/**
 * The Priest card in the Love Letter game.
 * Priest is a card with a value of 2 and the following effect: "Look at another player's hand."
 */
package de.annalisa.loveletters.cards;

import de.annalisa.loveletters.Game;
import de.annalisa.loveletters.Player;
import de.annalisa.loveletters.utils.StringHelper;

import java.util.List;

public class Priest extends Card {
    public Priest() {
        super("Priest", 2, "Look at another player's hand");
    }

    /**
     * Applies the effect of the Priest card.
     * <p>
     * The player can choose a player and see their cards.
     *
     * @param game The Love Letter game in progress.
     */
    @Override
    public void applyEffect(Game game) {
        Player currentPlayer = game.getCurrentPlayer();
        List<Player> otherPlayers = game.getOtherPlayersExcludingCurrent(currentPlayer.getName());
        //there is at least one player who isn't immune
        if (otherPlayers.stream().anyMatch(player -> !player.isImmune())) {
            int playerNumber = game.choosePlayerForEffect(otherPlayers, "Choose a player whose cards you want to look at: ");
            Player chosenPlayer = otherPlayers.get(playerNumber - 1);
            System.out.println(chosenPlayer.getName() + " has the following cards: \n" + StringHelper.printCardsBesideEachOther(chosenPlayer.getHand()));
        }
    }
}
