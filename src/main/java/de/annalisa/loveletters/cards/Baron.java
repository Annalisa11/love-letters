package de.annalisa.loveletters.cards;

import de.annalisa.loveletters.Game;
import de.annalisa.loveletters.Player;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Baron extends Card{
    public Baron() {
        super("Baron", 3, "You and another player secretly compare hands. The player with the lower value is out of the round.");
    }

    @Override
    public void applyEffect(Game game) {
        Player currentPlayer = game.getCurrentPlayer();
        List<Player> otherPlayers = game.getOtherPlayersExcludingCurrent(currentPlayer.getName());
        int playerNumber = game.choosePlayerForEffect(otherPlayers, "Choose a player to compare hands with: ");

        Player chosenPlayer;
        if(playerNumber == -1){
//            chosenPlayer = currentPlayer;
            System.out.println("you can't choose yourself for baron effect. Effect is not applied");
            return;
        } else {
            chosenPlayer = otherPlayers.get(playerNumber-1);
        }

        System.out.println("Alright! The scores are:   You (" + currentPlayer.getScore() + ")   " + chosenPlayer.getName() + " (" + chosenPlayer.getScore() + ")." );
        ArrayList<Player> winner = new ArrayList<>(Arrays.asList(currentPlayer, chosenPlayer));
        winner.sort(new Player.sortByScore());
        Player loser = winner.get(winner.size() -1);
        System.out.println(loser.getName() + " has the lowest score and is therefore knocked out!");
        game.knockOutPlayer(loser, currentPlayer);
    }
}