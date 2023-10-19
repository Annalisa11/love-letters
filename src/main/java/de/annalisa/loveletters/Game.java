package de.annalisa.loveletters;

import de.annalisa.loveletters.commands.CommandManager;

import java.util.ArrayList;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Game {
    private Deck deck;
    private int round = 1;
    private int turns = 0;
    private int effectID;
    private Card firstCardOfDeck;
    private Card[] threeOpenCards = new Card[3];
    public Player currentPlayer;
    private Player roundWinner;
    private Player gameWinner;
    private ArrayList<Player> players = new ArrayList<>();
    private ArrayList<Player> activePlayers = new ArrayList<>();
    private CommandManager commandManager = new CommandManager();

    //Data to create cards
    private String[] names = {"Guard", "Prince", "Countess", "King", "Princess", "Guard", "Prince", "Guard", "Guard", "Guard", "Priest", "Priest", "Baron", "Baron", "Handmaid", "Handmaid"};
    private int[] closeness = {1, 5, 7, 6, 8, 1, 5, 1, 1, 1, 2, 2, 3, 3, 4, 4};
    private String[] effects = {"Name a non-Guard card and choose another player. If that player has that card, he or she is out of the round.", "Choose any player (including yourself) to discard his or her hand and draw a new card",  "If you have this card and the King or Prince in your hand, you must discard this card.", "Trade hands with another player your choice", "If you discard this card, you are out of the round", "Name a non-Guard card and choose another player. If that player has that card, he or she is out of the round.", "Choose any player (including yourself) to discard his or her hand and draw a new card", "Name a non-Guard card and choose another player. If that player has that card, he or she is out of the round.", "Name a non-Guard card and choose another player. If that player has that card, he or she is out of the round.", "Name a non-Guard card and choose another player. If that player has that card, he or she is out of the round.", "Look at another player's hand", "Look at another player's hand", "You and another player secretly compare hands. The player with the lower value is out of the round.", "You and another player secretly compare hands. The player with the lower value is out of the round.", "Until your next turn, ignore all effects from other players' cards.", "Until your next turn, ignore all effects from other players' cards."};

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

    public Game(){
        displayIntroduction();
        waitForInput(this);
    }

    public int getEffectID() {
        return effectID;
    }

    public void setEffectID(int effectID) {
        this.effectID = effectID;
    }

    public void startGame(){
        commandManager.setInGame(true);
        System.out.println("Before we start with the game, let's define the players.");
        createPlayers();
        do {
            //set up game
            createDeck();
            System.out.println(deck);
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



            //set up players
            //sort all players for next round
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
        System.out.println("%c Welcome to LOVE LETTERS!");
        System.out.println("To start the game write '\\start'. To see other commands write '\\help'.");
    }
    public String printOnlyNames(ArrayList<Player> players){
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

    public void printThreeOpenCards(Card[] openCards){
        System.out.println("three open cards");
        for(Card card : openCards){
            System.out.println("* " + card);
        }
    }

    //other helper functions
    private List<Player> getOtherPlayersExcludingCurrent(String currentPlayer){
        return activePlayers.stream().filter(player -> !Objects.equals(player.getName(), currentPlayer)).toList(); //remove current player from possible candidates
    }

    private int choosePlayerForEffect(List<Player> otherPlayers, String message){
        Integer[] numbers = IntStream.rangeClosed(1, otherPlayers.size()).boxed().toArray(Integer[]::new); //transform IntStream into Integer[]
        String names = "";
        for(int i=1; i<=otherPlayers.size(); i++){
            names += otherPlayers.get(i-1).getName() + (otherPlayers.get(i-1).isImmune() ? " [immune]" : "") +" (" + i + ")  ";
        }
        numbers = Arrays.stream(numbers).filter(index -> !otherPlayers.get(index-1).isImmune()).toArray(Integer[]::new);

        return validateInputNumbers(numbers, message + names);
    }

    private void knockOutPlayer(Player player, Player currentPlayer){
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

    void createDeck() {
        deck = new Deck(16);
        for (int i = 0 ; i < deck.getDeckSize(); i++){
            Card card = new Card(names[i], closeness[i], effects[i]);
            deck.addCard(card);
        }
    }

    public int getIndexOfCardInHand(Player player, int closeness){
        return IntStream.range(0, player.getHand().size())
                .filter(i -> player.getHand().get(i).getCloseness() != closeness).findFirst().orElse(-1);
    }

    public boolean isSpecificCardOnHand(Player player, int closeness){
        return player.getHand().stream().anyMatch(card -> card.getCloseness() == closeness);
    }

    public int chooseCardToPlay(Player player) {
        return validateInputNumbers(new Integer[]{1,2}, "Which card do you want to discard?\n" + player.cardsOnHand() + "first (1) or second (2)");
    }

    //effect functions
    public void applyEffect(Card card, Player currentPlayer) {
        System.out.println("effect has been applied: " + card.getEffect());
        effectID = card.getCloseness();
        switch (card.getName()) {
            case "Guard" -> guardEffect(currentPlayer);
            case "Priest" -> priestEffect(currentPlayer.getName());
            case "Baron" -> baronEffect(currentPlayer);
            case "Handmaid" -> handmaidEffect(currentPlayer);
            case "Prince" -> princeEffect(currentPlayer);
            case "Princess" -> princessEffect(currentPlayer);
            case "King" -> kingEffect(currentPlayer);
            case "Countess" -> countessEffect();
            default -> {
                return;
            }
        }
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
            knockOutPlayer(chosenPlayer, currentPlayer);
        } else {
            System.out.println("Unfortunately for you, your chosen player doesn't have your chosen card on their hand. Your Guard has no effect.");
        }
    }

    private void priestEffect(String currentPlayer){
        List<Player> otherPlayers = getOtherPlayersExcludingCurrent(currentPlayer);
        //there is at least one player who isn't immune
        if (otherPlayers.stream().anyMatch(player -> !player.isImmune())){
            int playerNumber = choosePlayerForEffect(otherPlayers, "Choose a player whose cards you want to look at: ");
            Player chosenPlayer = otherPlayers.get(playerNumber-1);
            System.out.println(chosenPlayer.getName() + " has following cards: \n" + chosenPlayer.getHand());
            return;
        }
        System.out.println("All players are immune. More luck next time ;)");
    }

    private void baronEffect(Player currentPlayer){
        List<Player> otherPlayers = getOtherPlayersExcludingCurrent(currentPlayer.getName());
        int playerNumber = choosePlayerForEffect(otherPlayers, "Choose a player to compare hands with: ");

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
        knockOutPlayer(loser, currentPlayer);
    }

    private void handmaidEffect(Player currentPlayer){
        System.out.println("NOTE: You are now immune to other effects for one round.");
        currentPlayer.setImmune();
    }

    private void princeEffect(Player currentPlayer){
        List<Player> otherPlayers = getOtherPlayersExcludingCurrent(currentPlayer.getName());
        List<Player> allPlayers = activePlayers;
        //there is at least one player who isn't immune
        if (allPlayers.stream().anyMatch(player -> !player.isImmune())){
            int playerNumber = choosePlayerForEffect(allPlayers, "Choose a player who has to discard and draw a new card: ");
//            Player chosenPlayer = allPlayers.get(playerNumber-1);

            Player chosenPlayer;
            if(playerNumber == -1){
                chosenPlayer = currentPlayer;
                System.out.println("you have to choose yourself");
            } else {
                chosenPlayer = allPlayers.get(playerNumber-1);
            }
            System.out.println("Discard card and draw new one: " + chosenPlayer);

            //effect logic
            List<Card> hand = chosenPlayer.getHand();
            Integer[] numbers = IntStream.rangeClosed(1, hand.size()).boxed().toArray(Integer[]::new);
            int cardToDropIndex = validateInputNumbers(numbers, "Which card should be discarded? \n(if you choose for yourself, please do not choose the prince, since you are playing the card right now ;)") -1;

            Card removedCard = hand.get(cardToDropIndex);
            if(removedCard.getCloseness() == 8){
                knockOutPlayer(chosenPlayer, currentPlayer);
                System.out.println(chosenPlayer + " had to discard the Princess... what a shame, he or she is knocked out!");
                return;
            }
            hand.remove(cardToDropIndex);
            Card drawnCard = deck.getTopCardWithSpecialFallback(firstCardOfDeck);
            chosenPlayer.addCardToHand(drawnCard);

            System.out.println(chosenPlayer.getName() + " has discarded the card " + removedCard.getName() + " and drawn a new one.");
        }
    }

    private void princessEffect(Player currentPlayer){
        System.out.println("You discarded the Princess? HOW DARE YOU!?");
        knockOutPlayer(currentPlayer, currentPlayer);
    }

    private void countessEffect(){
        System.out.println("You have discarded the countess...");
    }

    private void kingEffect(Player currentPlayer){
        List<Player> otherPlayers = getOtherPlayersExcludingCurrent(currentPlayer.getName());
        int playerNumber = choosePlayerForEffect(otherPlayers, "Choose a player swap your card with: ");

        Player chosenPlayer;
        if(playerNumber == -1){
            System.out.println("Effect is not applied");
            return;
        } else {
            chosenPlayer = otherPlayers.get(playerNumber-1);
        }

        //get Cards to swap
        Card otherPlayersCard = chosenPlayer.getHand().get(0);
        int yourCardIndex = getIndexOfCardInHand(currentPlayer, 6);

        Card yourCard = currentPlayer.getHand().get(yourCardIndex);

        //set the Cards in each others hands
        chosenPlayer.getHand().set(0, yourCard);
        currentPlayer.getHand().set(yourCardIndex, otherPlayersCard);
        System.out.println("You have successfully swapped cards!");
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
        System.out.println("active players:: " + activePlayers);
        roundWinner = activePlayers.get(0);

        //winner gets token
        roundWinner.addLoveToken(1);
    }


    //input helper functions
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

    public int validateInputNumbers(Integer[] validValues, String message, Integer... exceptions){

        //All players immune
        if(validValues.length == 0){
            System.out.println("All players are immune.");
            if (effectID == 5){
                System.out.println("You have to choose yourself.");
                return 200;
            }
            System.out.println("Effect is not applied.");
            return -1;
        }

        //You can choose player
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

    public static void main(String[] args) {
        new Game();
    }
}
