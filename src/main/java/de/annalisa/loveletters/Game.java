package de.annalisa.loveletters;

import de.annalisa.loveletters.cards.Card;
import de.annalisa.loveletters.commands.CommandManager;

import java.util.ArrayList;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import de.annalisa.loveletters.cards.*;

public class Game {
    private Deck deck;
    private int round = 1;
    private int turns = 0;
    private int effectID;
    private Card firstCardOfDeck;
    private ArrayList<Card> threeOpenCards = new ArrayList<>(3);
    private Player currentPlayer;
    private Player roundWinner;
    private Player gameWinner;
    private ArrayList<Player> players = new ArrayList<>();
    private ArrayList<Player> activePlayers = new ArrayList<>();
    private CommandManager commandManager = new CommandManager();

    private boolean playAgain;

    public CommandManager getCommandManager() {
        return commandManager;
    }

    public ArrayList<Player> getPlayers() {
        return players;
    }

    public Deck getDeck() {
        return deck;
    }

    public Card getFirstCardOfDeck(){
        return firstCardOfDeck;
    }

    public Player getCurrentPlayer() {
        return currentPlayer;
    }

    public ArrayList<Player> getActivePlayers() {
        return activePlayers;
    }

    public Game(){
        displayIntroduction();
        waitForInput(this);
    }

    private void startNewGame(Game game){
        playAgain = true;
        while (playAgain){
            displayIntroduction();
            waitForInput(game);

        }
    }

    public static void exitGame() {
        return;
    }

    public void startGame(){
        commandManager.setInGame(true);
        System.out.println("Before we start with the game, let's define the players.");
        createPlayers();
        do {
            //set up game
            createDeck();
            shuffleDeck();

            //set up deck/cards
            System.out.println("\n-------");
            firstCardOfDeck = deck.getTopCard();
            System.out.println("first card: \n" + firstCardOfDeck); // remove later, first card should not be visible
            if(players.size() == 2){
                for(int i=0; i < 3; i++){
                    threeOpenCards.add(deck.getTopCard());
                }
                printThreeOpenCards(threeOpenCards);
                //TODO: reset open cards.. in round two there were 6
            }
            System.out.println("-------\n\n");

            //set up players
            activePlayers.clear();
            activePlayers.addAll(players);
            allPlayersDrawCard(activePlayers);

            //start round
            startRound();
        } while(!calculateWinnerOfGame());

        System.out.println("\n\n----------------");
        System.out.println("Congratulations " + gameWinner.getName() + ", you win!");
        System.out.println("----------------\n\n");
    }

    //TODO: refactor so that activeplayers is boolean attribute in every player?
    private void startRound() {
        System.out.println("------");
        System.out.println("START ROUND " + round + ". With these players: " + printOnlyNames(activePlayers));
        System.out.println("TOKENS: " + players.stream().map(player -> player.getName() + " (" + player.getLoveToken() + ")").collect(Collectors.joining("   ")));

        while(deck.getNumberOfCards() != 0){
            if(turns > activePlayers.size()-1){
                turns = 0;
            }
            if (roundWinner != null){
                currentPlayer = roundWinner;
                turns = activePlayers.indexOf(roundWinner);
                roundWinner = null;
            } else {
                currentPlayer = activePlayers.get(turns);
            }

            takeTurn(currentPlayer);
            turns++;

            if(activePlayers.size() == 1){
                break;
            }
        }
        System.out.println("End of Round " + round + "!");
        calculateWinnerOfRound();
        calculateWinnerOfGame();
        System.out.println("the winner is: " + roundWinner.getName() );
        //reset stats for next round
        players.forEach(Player::clearHand);
        players.forEach(Player::resetTurn);
        deck.clearDeck();
        threeOpenCards.clear();
        firstCardOfDeck = null;

        round++;
    }

    private void takeTurn(Player player){
        System.out.println("\n== " + player.getName() + ", it's your turn! ==");
        System.out.println("CARDS STILL IN DECK: " + deck.getNumberOfCards());
        if(player.isImmune()){
            System.out.println("NOTE: Handmaid effect expired. You are not immune anymore.");
            player.setImmune();
        }
        //draw card
        if(!(player.getTurn() == 1)){
            Card drawnCard = deck.getTopCard();
            player.addCardToHand(drawnCard);
        }
        //update score
        player.updateScore();
        System.out.println("SCORE: " + player.getScore());
        //update turn
        player.incrementTurn();
        //play card
        waitForInput(this);
    }

    //formatting helper functions
    private void displayIntroduction(){
        System.out.println("Welcome to LOVE LETTER!");
        System.out.println("To start the game write '\\start'. To see other commands write '\\help'.");
    }
    public String printOnlyNames(ArrayList<Player> players){
        //TODO: use a string builder? Make it more elegant...
        List<String> names = players.stream().map(Player::getName).toList();
        String res = "";
        if (names.size() == 2) {
            return names.get(0) + " and " + names.get(1);
        } else if (names.size() == 1) {
            return names.get(0);
        } else {
            for(int i=0; i<(names.size()-1); i++){
                res += names.get(i) + ", ";
            }
            res = res.substring(0, res.length() - 2);
            res += " and " + names.get(names.size() -1);
        }
        return res;
    }

    public void printThreeOpenCards(ArrayList<Card> openCards){
        System.out.println("three open cards");
        System.out.println(Card.printCardsBesideEachOther(openCards));
    }

    //other helper functions
    public List<Player> getOtherPlayersExcludingCurrent(String currentPlayer){
        return activePlayers.stream().filter(player -> !Objects.equals(player.getName(), currentPlayer)).toList(); //remove current player from possible candidates
    }

    /**
     * Prompts the current player to choose a player for a game effect from a list of eligible players.
     *
     * @param otherPlayers A list of players from which the current player can choose.
     * @param message      The message displayed to instruct the player's choice.
     * @return The index of the chosen player in the 'otherPlayers' list.
     * <ul>
     *      <li><code>-1</code> - Returns <code>-1</code> if all players are immune.</li>
     *      <li><code>200</code> - Returns <code>200</code> if all players are immune AND the player wants to apply the Prince effect and therefore has to choose himself.</li>
     * </ul>
     */
    public int choosePlayerForEffect(List<Player> otherPlayers, String message){
        Integer[] numbers = IntStream.rangeClosed(1, otherPlayers.size()).boxed().toArray(Integer[]::new); //transform IntStream into Integer[]
        String names = "";
        for(int i=1; i<=otherPlayers.size(); i++){
            names += otherPlayers.get(i-1).getName() + (otherPlayers.get(i-1).isImmune() ? " [immune]" : "") +" (" + i + ")  ";
        }
        numbers = Arrays.stream(numbers).filter(index -> !otherPlayers.get(index-1).isImmune()).toArray(Integer[]::new);

        //All players immune
        if(numbers.length == 0){
            System.out.println("All players are immune.");
            if (effectID == 5){             //TODO: add update of effectID before (or after?) the applyEffect()
                System.out.println("You have to choose yourself.");
                return 200;                 //TODO: check for code 200 or change this implementation to something else
            }
            System.out.println("Effect is not applied.");
            return -1;
        }

        return validateInputNumbers(numbers, message + names);
    }

    public void knockOutPlayer(Player player, Player currentPlayer){
        int indexOfPlayer = activePlayers.indexOf(player);
        int indexOfCurrentPlayer = activePlayers.indexOf(currentPlayer);
        if(indexOfPlayer == indexOfCurrentPlayer && indexOfCurrentPlayer !=( activePlayers.size()-1)){
            turns--;
        }
        activePlayers.remove(player);
    }

    private void allPlayersDrawCard(ArrayList<Player> players) {
        for(Player player : players){
            for(int i=0; i<2; i++){
                player.addCardToHand(deck.getTopCard());
            }
            player.updateScore();
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

    private void shuffleDeck() {
        deck.shuffleDeck();
    }
    //TODO: get rid of shuffleDeck. Just use deck.schuffleDeck

    void createDeck() {
        deck = new Deck(16);
        deck.addCard(new Princess());
        deck.addCard(new Countess());
        deck.addCard(new King());
        deck.addSameCardNTimes(new Guard(), 5);
        deck.addSameCardNTimes(new Prince(), 2);
        deck.addSameCardNTimes(new Priest(), 2);
        deck.addSameCardNTimes(new Baron(), 2);
        deck.addSameCardNTimes(new Handmaid(), 2);
    }

    /**
     * Finds the index of a card with a specific closeness value in a player's hand.
     *
     * @param player     The player whose hand is being searched.
     * @param closeness  The closeness value to search for in the player's hand.
     * @return The index of the first card with the specified closeness value in the player's hand, or -1 if not found.
     */
    public int getIndexOfCardInHand(Player player, int closeness){
        return IntStream.range(0, player.getHand().size())
                .filter(i -> player.getHand().get(i).getCloseness() == closeness).findFirst().orElse(-1);
    }

    public boolean isSpecificCardOnHand(Player player, int closeness){
        return player.getHand().stream().anyMatch(card -> card.getCloseness() == closeness);
    }

    public int chooseCardToPlay(Player player) {
        return validateInputNumbers(new Integer[]{1,2}, "Which card do you want to discard?\n" + Card.printCardsBesideEachOther(player.getHand()) + player.getHand().get(0).getName() + " (1) or " + player.getHand().get(1).getName() + " (2)");
    }

    //winning helper functions
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

    private boolean calculateWinnerOfGame() {
        return switch (players.size()) {
            case 2 -> decideWinner(7);
            case 3 -> decideWinner(5);
            case 4 -> decideWinner(4);
            default -> false;
        };
    }

    private void calculateWinnerOfRound() {
        if(activePlayers.size() > 1){
            activePlayers.sort(new Player.sortByScore());
        }
        roundWinner = activePlayers.get(0);

        //winner gets token
        roundWinner.addLoveToken(1);
    }

    //input helper functions
    /**
     * Continuously waits for user input and processes it as a command. <br/>
     * This method is suitable for situations where user input is not required for specific numeric values, and the user is free to choose from a list of available commands.
     *
     * @param game The current game instance that will handle the commands.
     */
    public void waitForInput(Game game){
        Scanner scanner = new Scanner(System.in);
        boolean scan = true;
        while (scan){
            String input = scanner.nextLine();
            if (commandManager.isCommand(input)){
                scan = commandManager.handleInput(input, game);
            } else {
                System.out.println("Input not valid. Try again");
            }
        }
    }

    /**
     * Validates and retrieves an integer input from the user within a specified set of valid values.
     * This method prompts the user for input and ensures that the input is an integer within the valid values.
     * If the input is not a valid integer or is outside the specified valid values, the user will be prompted to try again.
     *
     * @param validValues An array of valid integer values that the user's input can match.
     * @param message     The prompt message displayed to the user.
     * @param exceptions  An optional array of values that are considered exceptions and will not be treated as valid.
     * @return The valid integer input provided by the user.
     * <ul>
     *     <li><code>-1</code> - Returns <code>-1</code> if all players are immune.</li>
     *     <li><code>200</code> - Returns <code>200</code> if all players are immune AND the player wants to apply the Prince effect and therefore has to choose himself.</li>
     * </ul>
     * @throws InputMismatchException If the user enters a non-integer value.
     */
    public int validateInputNumbers(Integer[] validValues, String message, Integer... exceptions){



        //You can choose player
        Scanner scanner = new Scanner(System.in);
        int input;
        while(true){
            System.out.println(message);
            String inputString = scanner.nextLine();
            try{
                input = Integer.parseInt(inputString);

                if(!Arrays.asList(validValues).contains(input) || Arrays.asList(exceptions).contains(input)){
                    System.out.println("invalid input. Try again.");
                    continue;
                }
                return input;
            } catch (Exception e){
                System.out.println("Please enter a number.");
            }
        }
    }

    public String getStringFromConsole(){
        Scanner scanner = new Scanner(System.in);
        return scanner.nextLine();
    }

    public static void main(String[] args) {
        new Game();
    }
}