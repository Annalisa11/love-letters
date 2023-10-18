package de.annalisa.loveletters;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.*;

public class Game {

    private ArrayList<Player> players = new ArrayList<>();
    private Deck deck;
    private int round = 1;
    private Card firstCardOfDeck;
    private Card[] threeOpenCards = new Card[3];
    private Player roundWinner;
    private Player gameWinner;
    private ArrayList<Player> activePlayers = new ArrayList<>();

    private String[] names = {"Prince", "Prince", "King", "Countess", "Princess", "Guard", "Guard", "Guard", "Guard", "Guard", "Priest", "Priest", "Baron", "Baron", "Handmaid", "Handmaid"};
    private int[] closeness = {5, 5, 6, 7, 8, 1, 1, 1, 1, 1, 2, 2, 3, 3, 4, 4};
    private String[] effects = {"Choose any player (including yourself) to discard his or her hand and draw a new card", "Choose any player (including yourself) to discard his or her hand and draw a new card", "Trade hands with another player your choice", "If you have this card and the King or Prince in your hand, you must discard this card.", "If you discard this card, you are out of the round", "Name a non-Guard card and choose another player. If that player has that card, he or she is out of the round.", "Name a non-Guard card and choose another player. If that player has that card, he or she is out of the round.", "Name a non-Guard card and choose another player. If that player has that card, he or she is out of the round.", "Name a non-Guard card and choose another player. If that player has that card, he or she is out of the round.", "Name a non-Guard card and choose another player. If that player has that card, he or she is out of the round.", "Look at another player's hand", "Look at another player's hand", "You and another player secretly compare hands. The player with the lower value is out of the round.", "You and another player secretly compare hands. The player with the lower value is out of the round.", "Until your next turn, ignore all effects from other players' cards.", "Until your next turn, ignore all effects from other players' cards."};

    public void startGame(){
        //set up game
        createDeck();
        shuffleDeck();
        System.out.println(deck);
        createPlayers();

        //set up deck
        firstCardOfDeck = deck.getTopCard();
        if(players.size() == 2){
            for(int i=0; i < 3; i++){
                threeOpenCards[i] = deck.getTopCard();
            }
        }
        allPlayersDrawCard(players);

        System.out.println("-------");
        System.out.println("first card: " + firstCardOfDeck);
        printThreeOpenCards(threeOpenCards);
        System.out.println(deck);
        System.out.println(players);

        //players get second card
//        allPlayersDrawCard(players);

        //while no winner and cardnumber is 0?

        activePlayers = players;
        startRound();


        //if player enough tokens, player wins
    }

    private void startRound() {
        System.out.println("round start. Round " + round + ". With these players: " + activePlayers);
        int turns = 0;
        while(deck.getNumberOfCards() != 0){
//            int index = calculateTurn(activePlayers, turns);
            System.out.println("TURN " + turns + "--------------  mod: " + (turns % activePlayers.size()) + " remaining cards: " + deck.getNumberOfCards());
            startTurn(activePlayers.get(turns % activePlayers.size()));
            turns++;
        }
        System.out.println("round ends");
        calculateWinnerOfRound();
        calculateWinnerOfGame();
        round++;

    }

    private void startTurn(Player player){
        System.out.println("-- player " + player.getName() + " is taking a turn --");
        Card drawnCard = deck.getTopCard();
        player.addCardToHand(drawnCard);
        int chosenCard = chooseCardToPlay(player);
        applyEffect(player.getHand().get(chosenCard - 1));
    }

    private void applyEffect(Card card) {
        System.out.println("effect has been applied: " + card.getEffect());
    }

    private int chooseCardToPlay(Player player) {
        return validateInputNumbers(new Integer[]{1,2}, "Which card do you want to discard?\n" + player.cardsOnHand() + " first (1) or second (2)");
    }

    private void calculateWinnerOfGame() {
        //if player has enough tokens, player wins
    }

    private void calculateWinnerOfRound() {
        if(activePlayers.size() > 1){
            Collections.sort(activePlayers, new Player.sortByScore());
        }
        roundWinner = activePlayers.get(0);
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
        System.out.println(players);
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
            System.out.print(card);
        }
    }

    public Game(){
        startGame();
        System.out.println("game");
    }
    public static void main(String[] args) {
        System.out.println("hallo");
        System.out.println("pierre");
        Game game = new Game();
    }
}
