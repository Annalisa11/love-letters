package de.annalisa.loveletters;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.*;
import java.util.stream.Collectors;

public class Game {

    private ArrayList<Player> players = new ArrayList<>();
    private Deck deck;
    private int round = 1;
    private Card firstCardOfDeck;
    private Card[] threeOpenCards = new Card[3];
    private Player roundWinner;
    private Player gameWinner;
    private ArrayList<Player> activePlayers = new ArrayList<>();
    private boolean firstRound = true;

    private String[] names = {"Prince", "Prince", "King", "Countess", "Princess", "Guard", "Guard", "Guard", "Guard", "Guard", "Priest", "Priest", "Baron", "Baron", "Handmaid", "Handmaid"};
    private int[] closeness = {5, 5, 6, 7, 8, 1, 1, 1, 1, 1, 2, 2, 3, 3, 4, 4};
    private String[] effects = {"Choose any player (including yourself) to discard his or her hand and draw a new card", "Choose any player (including yourself) to discard his or her hand and draw a new card", "Trade hands with another player your choice", "If you have this card and the King or Prince in your hand, you must discard this card.", "If you discard this card, you are out of the round", "Name a non-Guard card and choose another player. If that player has that card, he or she is out of the round.", "Name a non-Guard card and choose another player. If that player has that card, he or she is out of the round.", "Name a non-Guard card and choose another player. If that player has that card, he or she is out of the round.", "Name a non-Guard card and choose another player. If that player has that card, he or she is out of the round.", "Name a non-Guard card and choose another player. If that player has that card, he or she is out of the round.", "Look at another player's hand", "Look at another player's hand", "You and another player secretly compare hands. The player with the lower value is out of the round.", "You and another player secretly compare hands. The player with the lower value is out of the round.", "Until your next turn, ignore all effects from other players' cards.", "Until your next turn, ignore all effects from other players' cards."};

    public void startGame(){

        createPlayers();
        do {
            //set up game
            createDeck();
            shuffleDeck();

            //set up deck/cards
            System.out.println("\n-------");
            firstCardOfDeck = deck.getTopCard();
            System.out.println("first card: " + firstCardOfDeck); // remove later, first card should not be visible
            if(players.size() == 2){
                for(int i=0; i < 3; i++){
                    threeOpenCards[i] = deck.getTopCard();
                }
                printThreeOpenCards(threeOpenCards);
            }
            System.out.println("-------\n\n");

            if(firstRound){
                //set up players
                allPlayersDrawCard(players);
                firstRound = false;
            }

            //start round
            activePlayers = players;
            startRound();
            //if player enough tokens, player wins

        } while(!calculateWinnerOfGame());
    }

    private void startRound() {
        System.out.println("------");
        System.out.println("START ROUND " + round + ". With these players: " + printOnlyNames(activePlayers));
        System.out.println("TOKENS: " + players.stream().map(player -> player.getName() + " (" + player.getLoveToken() + ")").collect(Collectors.joining("   ")));
        int turns = 0;
        while(deck.getNumberOfCards() != 0){
//            System.out.println("TURN " + turns + "--------------  mod: " + (turns % activePlayers.size()) + " remaining cards: " + deck.getNumberOfCards());
            takeTurn(activePlayers.get(turns % activePlayers.size()));
            turns++;
        }
        System.out.println("End of Round " + round + "!");
        calculateWinnerOfRound();
        calculateWinnerOfGame();
        System.out.println("the winner is: " + roundWinner.getName() );

        round++;
    }

    public String printOnlyNames(ArrayList<Player> players){
        List<String> names = players.stream().map(Player::getName).collect(Collectors.toList());
        String res = "";
        if (names.size() == 2) {
            return names.get(0) + " and " + names.get(1);
        } else if (names.size() == 1) {
            return names.get(0);
        } else {
            for(int i=0; i<names.size() -1; i++){
                res += names.get(i) + ", ";
            }
            res = res.substring(0, res.length() - 2);
            res += " and " + names.get(names.size() -1);
        }
        return res;
    }

    private void takeTurn(Player player){
        System.out.println("== " + player.getName() + ", it's your turn! ==");
        //draw card
        Card drawnCard = deck.getTopCard();
        player.addCardToHand(drawnCard);
        //update score
        player.updateScore();
        System.out.println("SCORE: " + player.getScore());
        //play card
        int chosenCard = chooseCardToPlay(player) - 1;
        applyEffect(player.getHand().get(chosenCard));
        player.removeCardFromHand(chosenCard);
        player.updateScore();
    }

    private void applyEffect(Card card) {
        System.out.println("effect has been applied: " + card.getEffect() + "\n\n");

    }

    private int chooseCardToPlay(Player player) {
        return validateInputNumbers(new Integer[]{1,2}, "Which card do you want to discard?\n" + player.cardsOnHand() + "first (1) or second (2)");
    }

    private boolean calculateWinnerOfGame() {
        return switch (players.size()) {
            case 2 -> decideWinner(7);
            case 3 -> decideWinner(5);
            case 4 -> decideWinner(4);
            default -> false;
        };
    }

    private boolean decideWinner(int neededTokens){
        List<Player> winners = players.stream().filter(player -> player.getLoveToken() == neededTokens).toList();
        if(winners.isEmpty()){
            return false;
        }else if (winners.size() == 1) {
            gameWinner = winners.get(0);
            return true;
        }
        else {
            int index;
            System.out.println("There is a tie! Who went most recently on a date?");
            index = validateInputNumbers(new Integer[]{1,2}, winners.get(0) + "(1) or " + winners.get(1) + "(2)");
            gameWinner = winners.get(index);
            return true;
        }
    }

    private void calculateWinnerOfRound() {
        if(activePlayers.size() > 1){
            Collections.sort(activePlayers, new Player.sortByScore());
        }
        System.out.println("active players:: " + activePlayers);
        roundWinner = activePlayers.get(0);
        roundWinner.addLoveToken(1);
        //winner gets token
    }

    private void allPlayersDrawCard(ArrayList<Player> players) {
        for(Player player : players){
            player.addCardToHand(deck.getTopCard());
        }
    }

    private void createPlayers() {
        int numberOfPlayers;
        numberOfPlayers = validateInputNumbers(new Integer[]{2,3,4}, "With how many players do you want to play? (2,3,4)");
        for(int i = 0; i < numberOfPlayers; i++){
            System.out.println("What should player " + (i+1) + " be called?");
            String name = getStringFromConsole();
            Player player = new Player(name);
            players.add(player);
        }
        System.out.println("Great! Let's start!");
    }


    public int validateInputNumbers(Integer[] validValues, String message){
        Scanner scanner = new Scanner(System.in);
        System.out.println(message);
        int input = scanner.nextInt();
        while(!Arrays.asList(validValues).contains(input)){
            System.out.println("invalid input. Try again.");
            input = scanner.nextInt();
        }
        return input;
    }

    public String getStringFromConsole(){
        Scanner scanner = new Scanner(System.in);
        return scanner.nextLine();
    }

    private void shuffleDeck() {
        deck.shuffleDeck();
    }

    private void createDeck() {
        deck = new Deck(16);
        for (int i = 0 ; i < deck.getDeckSize(); i++){
            Card card = new Card(names[i], closeness[i], effects[i]);
            deck.addCard(card);
        }
    }

    public void printThreeOpenCards(Card[] openCards){
        System.out.println("three open cards");
        for(Card card : openCards){
            System.out.println("* " + card);
        }
    }

    public Game(){
        startGame();
    }
    public static void main(String[] args) {
        new Game();
    }
}
