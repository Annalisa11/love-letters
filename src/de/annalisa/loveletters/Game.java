package de.annalisa.loveletters;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static java.util.Arrays.stream;

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
    private int turns = 0;

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

        System.out.println("\n\n----------------");
        System.out.println("Congratulations " + gameWinner.getName() + ", you win!");
        System.out.println("----------------\n\n");
    }

    private void startRound() {
        System.out.println("------");
        System.out.println("START ROUND " + round + ". With these players: " + printOnlyNames(activePlayers));
        System.out.println("TOKENS: " + players.stream().map(player -> player.getName() + " (" + player.getLoveToken() + ")").collect(Collectors.joining("   ")));

        while(deck.getNumberOfCards() != 0){
//            System.out.println("TURN " + turns + "--------------  mod: " + (turns % activePlayers.size()) + " remaining cards: " + deck.getNumberOfCards());
            takeTurn(activePlayers.get(turns % activePlayers.size()));
            turns++;

            if(activePlayers.size() == 1){
                break;
            }
        }
        System.out.println("End of Round " + round + "!");
        calculateWinnerOfRound();
        calculateWinnerOfGame();
        System.out.println("the winner is: " + roundWinner.getName() );

        round++;
    }

    public String printOnlyNames(ArrayList<Player> players){
        List<String> names = players.stream().map(Player::getName).toList();
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
        if(player.isImmune()){
            System.out.println("NOTE: Handmaid effect expired. You are not immune anymore.");
            player.setImmune();
        }
        //draw card
        Card drawnCard = deck.getTopCard();
        player.addCardToHand(drawnCard);
        //update score
        player.updateScore();
        System.out.println("SCORE: " + player.getScore());
        //play card
        int chosenCard = chooseCardToPlay(player) - 1;
        applyEffect(player.getHand().get(chosenCard), player);
        player.removeCardFromHand(chosenCard);
        player.updateScore();
    }

    private void applyEffect(Card card, Player currentPlayer) {
        System.out.println("effect has been applied: " + card.getEffect());
        switch (card.getName()){
            case "Guard":
                guardEffect(currentPlayer);
            case "Priest":
                priestEffect(currentPlayer.getName());
            case "Baron":
                baronEffect(currentPlayer);
            case "Handmaid":
                handmaidEffect(currentPlayer);
            case "Prince":
                princeEffect(currentPlayer);
            default:
                return;
        }

    }

    private List<Player> getOtherPlayersExcludingCurrent(String currentPlayer){
        return players.stream().filter(player -> !Objects.equals(player.getName(), currentPlayer)).toList(); //remove current player from possible candidates to unleash effect upon
    }

    private int choosePlayerForEffect(List<Player> otherPlayers, String message){
//        ArrayList<Integer> immunePlayersIndices = IntStream.range(0, otherPlayers.size()).filter(index -> otherPlayers.get(index).isImmune());
        Integer[] numbers = IntStream.rangeClosed(1, otherPlayers.size()).boxed().toArray(Integer[]::new); //transform IntStream into Integer[]
        String names = "";
        for(int i=1; i<=otherPlayers.size(); i++){
            names += otherPlayers.get(i-1).getName() + (otherPlayers.get(i-1).isImmune() ? " [immune]" : "") +" (" + i + ")  ";
        }
        numbers = Arrays.stream(numbers).filter(index -> otherPlayers.get(index).isImmune()).toArray(Integer[]::new);
        return validateInputNumbers(numbers, message + names);
    }

    private void guardEffect(Player currentPlayer) {
        List<Player> otherPlayers = getOtherPlayersExcludingCurrent(currentPlayer.getName());
        int cardNumber = validateInputNumbers(new Integer[]{2,3,4,5,6,7,8}, "Choose a card number from 2 - 8.");
        int playerNumber = choosePlayerForEffect(otherPlayers, "Choose a player to potentially knock out: ");

        Player chosenPlayer;
        if(playerNumber == -1){
//            chosenPlayer = currentPlayer;
            System.out.println("you can't choose yourself for guard effect. Effect is not applied");
            return;
        } else {
            chosenPlayer = otherPlayers.get(playerNumber-1);
        }

        if(chosenPlayer.getHand().stream().anyMatch(card -> card.getCloseness() == cardNumber)) {
            System.out.println("You successfully knocked out " + chosenPlayer.getName() + "!");
            activePlayers.remove(chosenPlayer);
            //to get the right turn again since one is missing for mod function
            turns++;
        } else {
            System.out.println("Unfortunately for you, your chosen player doesn't have your chosen card on their hand. Your Guard has no effect.");
        }
    }

    private void priestEffect(String currentPlayer){
        List<Player> otherPlayers = getOtherPlayersExcludingCurrent(currentPlayer);
        int playerNumber = choosePlayerForEffect(otherPlayers, "Choose a player whose cards you want to look at: ");

        Player chosenPlayer = otherPlayers.get(playerNumber-1);

        System.out.println(chosenPlayer.getName() + " has following cards: \n" + chosenPlayer.getHand());
    }

    private void baronEffect(Player currentPlayer){
        List<Player> otherPlayers = getOtherPlayersExcludingCurrent(currentPlayer.getName());
        int playerNumber = choosePlayerForEffect(otherPlayers, "Choose a player to compare hands with: ");
        Player chosenPlayer = otherPlayers.get(playerNumber-1);

        System.out.println("Alright! The scores are:   You (" + currentPlayer.getScore() + ")   " + chosenPlayer.getName() + " (" + chosenPlayer.getScore() + ")." );
        ArrayList<Player> winner = new ArrayList<>(Arrays.asList(currentPlayer, chosenPlayer));
        winner.sort(new Player.sortByScore());
        Player loser = winner.get(winner.size() -1);
        System.out.println(loser.getName() + " has the lowest score and is therefore knocked out!");
        activePlayers.remove(loser);
        turns++;
    }

    private void handmaidEffect(Player currentPlayer){
        System.out.println("NOTE: You are now immune to other effects for one round.");
        currentPlayer.setImmune();
    }

    private void princeEffect(Player currentPlayer){

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
            activePlayers.sort(new Player.sortByScore());
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


    public int validateInputNumbers(Integer[] validValues, String message, Integer... exceptions){
        if(validValues.length == 0){
            System.out.println("All players are immune. You have to choose yourself.");
            return -1;
        }
        Scanner scanner = new Scanner(System.in);
        System.out.println(message);
        int input = scanner.nextInt();
        while(!Arrays.asList(validValues).contains(input) || Arrays.asList(exceptions).contains(input)){
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
