package de.annalisa.loveletters.cards;

import de.annalisa.loveletters.Game;
import de.annalisa.loveletters.Player;

import java.util.List;

public class Priest extends Card{
    public Priest() {
        super("Priest", 2, "Look at another player's hand");
    }

    @Override
    public void applyEffect(Game game) {
        Player currentPlayer = game.getCurrentPlayer();
        List<Player> otherPlayers = game.getOtherPlayersExcludingCurrent(currentPlayer.getName());
        //there is at least one player who isn't immune
        if (otherPlayers.stream().anyMatch(player -> !player.isImmune())){
            int playerNumber = game.choosePlayerForEffect(otherPlayers, "Choose a player whose cards you want to look at: ");
            Player chosenPlayer = otherPlayers.get(playerNumber-1);
            System.out.println(chosenPlayer.getName() + " has following cards: \n" + Card.printCardsBesideEachOther(chosenPlayer.getHand()));
            return;
        }
        System.out.println("All players are immune. More luck next time ;)");
    }
}
