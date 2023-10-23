package de.annalisa.loveletters.cards;

import de.annalisa.loveletters.Game;
import de.annalisa.loveletters.Player;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Prince extends Card{

    public Prince() {
        super("Prince", 5, "Choose any player (including yourself) to discard his or her hand and draw a new card");
    }

    @Override
    public void applyEffect(Game game) {
        Player currentPlayer = game.getCurrentPlayer();

        List<Player> allPlayers = game.getActivePlayers();
        //there is at least one player who isn't immune (always true because of currentPlayer)
        if (allPlayers.stream().anyMatch(player -> !player.isImmune())){
            int playerNumber = game.choosePlayerForEffect(allPlayers, "Choose a player who has to discard and draw a new card: ");
            Player chosenPlayer = allPlayers.get(playerNumber-1);

            //effect logic
            List<Card> hand = chosenPlayer.getHand();
            Integer[] numbers = IntStream.rangeClosed(1, hand.size()).boxed().toArray(Integer[]::new);
            Card nonPrinceCard = chosenPlayer.getHand().stream().filter(card -> card.getCloseness() != 5).findFirst().orElse(chosenPlayer.getHand().get(0));
            int cardToDropIndex = game.getIndexOfCardInHand(chosenPlayer, nonPrinceCard.getCloseness());
            Card removedCard = hand.get(cardToDropIndex);
            if(removedCard.getCloseness() == 8){
                game.knockOutPlayer(chosenPlayer, currentPlayer);
                System.out.println(chosenPlayer + " had to discard the Princess... what a shame, " + chosenPlayer.getName() + " is knocked out!");
                return;
            }
            hand.remove(cardToDropIndex);
            Card drawnCard = game.getDeck().getTopCardWithSpecialFallback(game.getFirstCardOfDeck());
            chosenPlayer.addCardToHand(drawnCard);

            System.out.println(chosenPlayer.getName() + " has discarded the card " + removedCard.getName() + " and drawn a new one.");
        }
    }
}
