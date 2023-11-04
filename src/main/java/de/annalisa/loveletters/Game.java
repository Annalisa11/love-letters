package de.annalisa.loveletters;

import de.annalisa.loveletters.cards.Card;
import de.annalisa.loveletters.commands.CommandManager;

import java.util.ArrayList;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import de.annalisa.loveletters.cards.*;
import de.annalisa.loveletters.exceptions.ExitGameException;
import de.annalisa.loveletters.utils.InputHelper;
import de.annalisa.loveletters.utils.StringHelper;

/**
 * The `Game` class represents a Love Letter card game session. It manages the game flow, player interactions, and scoring.
 */
public class Game {
    private Deck deck;
    private int round = 1;
    private int turns = 0;
    private Card firstCardOfDeck;
    private final ArrayList<Card> threeOpenCards = new ArrayList<>(3);
    private Player currentPlayer;
    private Player roundWinner;
    private Player gameWinner;
    private final ArrayList<Player> players = new ArrayList<>();
    private final ArrayList<Player> activePlayers = new ArrayList<>();
    private final CommandManager commandManager = new CommandManager();

    public CommandManager getCommandManager() {
        return commandManager;
    }

    public ArrayList<Player> getPlayers() {
        return players;
    }

    public Deck getDeck() {
        return deck;
    }

    public Card getFirstCardOfDeck() {
        return firstCardOfDeck;
    }

    public Player getCurrentPlayer() {
        return currentPlayer;
    }

    public ArrayList<Player> getActivePlayers() {
        return activePlayers;
    }

    /**
     * Initializes a new Love Letter card game session.
     */
    public Game() {

    }


    /**
     * Starts the Love Letter game.
     *
     * @throws ExitGameException to indicate that the Love Letter game should exit or terminate. This exception is used to control the flow of the game and allow players to choose whether to play again or exit the game.
     */
    public void startGame() throws ExitGameException {
        commandManager.setInGame(true);
        System.out.println("Before we start with the game, let's define the players.");
        createPlayers();
        do {
            //set up game
            createDeck();
            deck.shuffleDeck();


            //set up deck/cards
            System.out.println("\n-------");
            firstCardOfDeck = deck.getTopCard();
            System.out.println("first card of the deck was drawn and set aside..."); // remove later, first card should not be visible
            if (players.size() == 2) {
                for (int i = 0; i < 3; i++) {
                    threeOpenCards.add(deck.getTopCard());
                }
                System.out.println("The next three cards were drawn and set aside, face up, so you can see them...");
                StringHelper.printThreeOpenCards(threeOpenCards);
            }
            System.out.println("-------\n\n");

            //set up players
            activePlayers.clear();
            activePlayers.addAll(players);
            allPlayersDrawCard(activePlayers);

            //start round
            startRound();
        } while (!calculateWinnerOfGame());

        System.out.println("\n\n----------------");
        System.out.println("Congratulations \uD83D\uDC8C" + gameWinner.getName() + "\uD83D\uDC8C, you win!");
        System.out.println("----------------\n\n");
    }

    /**
     * Starts a new round of the game.
     *
     * @throws ExitGameException to indicate that the Love Letter game should exit or terminate. This exception is used to control the flow of the game and allow players to choose whether to play again or exit the game.
     */
    private void startRound() throws ExitGameException {
        System.out.println("------");
        System.out.println("\uD83D\uDC95 START ROUND " + round + " \uD83D\uDC95");
        System.out.println("PLAYERS: " + StringHelper.printOnlyNames(activePlayers));
        System.out.println("TOKENS: " + players.stream().map(player -> player.getName() + " (" + player.getLoveToken() + ")").collect(Collectors.joining("   ")));

        while (deck.getNumberOfCards() != 0) {
            if (turns > activePlayers.size() - 1) {
                turns = 0;
            }
            if (roundWinner != null) {
                currentPlayer = roundWinner;
                turns = activePlayers.indexOf(roundWinner);
                roundWinner = null;
            } else {
                currentPlayer = activePlayers.get(turns);
            }

            takeTurn(currentPlayer);
            turns++;

            if (activePlayers.size() == 1) {
                break;
            }
        }
        if (deck.getNumberOfCards() == 0) {
            System.out.println("\n\n+----------------------------------------+");
            System.out.println("| There are no more cards in the deck... |");
            System.out.println("+----------------------------------------+");
        }
        System.out.println("\n\n-------");
        System.out.println("End of Round " + round + "!");
        calculateWinnerOfRound();
        calculateWinnerOfGame();
        System.out.println("the winner is: " + roundWinner.getName());
        //reset stats for next round
        players.forEach(player -> {
            player.clearHand();
            player.resetTurn();
            player.resetImmune();
            player.clearDiscardedCards();
        });
        deck.clearDeck();
        threeOpenCards.clear();
        firstCardOfDeck = null;

        round++;
    }

    /**
     * Takes a turn for the given player.
     *
     * @param player The player taking their turn.
     * @throws ExitGameException to indicate that the Love Letter game should exit or terminate. This exception is used to control the flow of the game and allow players to choose whether to play again or exit the game.
     */
    private void takeTurn(Player player) throws ExitGameException {
        //draw card
        Card drawnCard = deck.getTopCard();
        player.addCardToHand(drawnCard);

        //display turn
        System.out.println("\n=== " + player.getName() + ", it's your turn! ===");
        System.out.println("(You have drawn a card)");
        System.out.println("CARDS STILL IN DECK: " + deck.getNumberOfCards());
        if (player.isImmune()) {
            System.out.println("NOTE \uD83D\uDD13: Handmaid effect expired. You are not immune anymore.");
            player.setImmune();
        }

        //update score
        player.updateScore();
        System.out.println("SCORE: " + player.getScore());
        //update turn
        player.incrementTurn();
        //play card
        InputHelper.waitForCommandInput(this);
    }

    //other helper functions

    /**
     * Retrieves other players excluding the current player.
     *
     * @param currentPlayer The name of the current player.
     * @return A list of other players.
     */
    public List<Player> getOtherPlayersExcludingCurrent(String currentPlayer) {
        return activePlayers.stream().filter(player -> !Objects.equals(player.getName(), currentPlayer)).toList(); //remove current player from possible candidates
    }

    /**
     * Prompts the current player to choose a player for a game effect from a list of eligible players.
     *
     * @param otherPlayers A list of players from which the current player can choose.
     * @param message      The message displayed to instruct the player's choice.
     * @return The index of the chosen player in the 'otherPlayers' list. min 0 - max 3.
     * <ul>
     *      <li><code>-1</code> - Returns <code>-1</code> if all players are immune.</li>
     * </ul>
     */
    public int choosePlayerForEffect(List<Player> otherPlayers, String message) {
        Integer[] numbers = IntStream.rangeClosed(1, otherPlayers.size()).boxed().toArray(Integer[]::new);
        StringBuilder names = new StringBuilder();
        for (int i = 1; i <= otherPlayers.size(); i++) {
            names.append(otherPlayers.get(i - 1).getName()).append(otherPlayers.get(i - 1).isImmune() ? " \uD83D\uDD12[immune]" : "").append(" (").append(i).append(")  ");
        }
        numbers = Arrays.stream(numbers).filter(index -> !otherPlayers.get(index - 1).isImmune()).toArray(Integer[]::new);

        //All players immune
        if (numbers.length == 0) {
            System.out.println("\uD83D\uDD12 All players are immune. \uD83D\uDD12");
            System.out.println("Effect is not applied. More luck next time ;)");
            return -1;
        }

        return InputHelper.validateInputNumbers(numbers, message + names);
    }

    /**
     * Knocks out a player from the game.
     *
     * @param player        The player to be knocked out.
     * @param currentPlayer The current player who is knocking out the other player.
     */
    public void knockOutPlayer(Player player, Player currentPlayer) {
        int indexOfPlayer = activePlayers.indexOf(player);
        int indexOfCurrentPlayer = activePlayers.indexOf(currentPlayer);
        if (indexOfPlayer == indexOfCurrentPlayer && indexOfCurrentPlayer != (activePlayers.size() - 1)) {
            turns--;
        }
        Card secondCardToDrop = null;
        if (player == currentPlayer){
            secondCardToDrop = player.getHand().get(1);
        }
        Card cardToDrop = player.getHand().get(0);
        System.out.println(player.getName() + " dropped the card " + cardToDrop.getName() + (secondCardToDrop != null ? (" and the card " + secondCardToDrop.getName()) : "") + ".");
        activePlayers.remove(player);
    }

    /**
     * Draws two cards for all players in the game.
     *
     * @param players The list of players.
     */
    private void allPlayersDrawCard(ArrayList<Player> players) {
        for (Player player : players) {
            player.addCardToHand(deck.getTopCard());
            player.updateScore();
        }
    }

    /**
     * Creates the players for the game based on user input.
     */
    private void createPlayers() {
        int numberOfPlayers;
        numberOfPlayers = InputHelper.validateInputNumbers(new Integer[]{2, 3, 4}, "With how many players do you want to play? (2,3,4)");
        for (int i = 0; i < numberOfPlayers; i++) {
            System.out.println("What should player " + (i + 1) + " be called?");
            String name = InputHelper.getStringFromConsole();
            Player player = new Player(name);
            players.add(player);
        }

        //determine first player
        System.out.println("Let's determine who goes first...\nWho went most recently on a date?\nChoose a player. If there is a tie, choose the younger one.");
        Integer[] numbers = new Integer[4];
        for (int i = 0; i < players.size(); i++) {
            System.out.print(players.get(i).getName() + " (" + (i + 1) + ")   ");
            numbers[i] = (i + 1);
        }
        int startingPlayerIndex = InputHelper.validateInputNumbers(numbers, " ");
        roundWinner = players.get(startingPlayerIndex - 1);
        System.out.println("Great! Let's start!");
    }

    /**
     * Creates a deck of Love Letter cards.
     */
    void createDeck() {
        deck = new Deck();
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
     * @param player    The player whose hand is being searched.
     * @param closeness The closeness value to search for in the player's hand.
     * @return The index of the first card with the specified closeness value in the player's hand, or -1 if not found.
     */
    public static int getIndexOfCardInHand(Player player, int closeness) {
        return IntStream.range(0, player.getHand().size())
                .filter(i -> player.getHand().get(i).getCloseness() == closeness).findFirst().orElse(-1);
    }

    /**
     * Gets the index of the other card in the player's hand, excluding a specific card by closeness.
     *
     * @param player                  The player whose hand is being searched.
     * @param closenessOfCardToIgnore The closeness value of the card to be ignored.
     * @return The index of the other card in the player's hand.
     */
    public static int getIndexOfOtherCardOnHand(Player player, int closenessOfCardToIgnore) {
        Card otherCard = player.getHand().stream().filter(card -> card.getCloseness() != closenessOfCardToIgnore).findFirst().orElse(player.getHand().get(0));
        return Game.getIndexOfCardInHand(player, otherCard.getCloseness());
    }

    /**
     * Checks if a player's hand contains a card with a specific closeness value.
     *
     * @param player    The player whose hand is being checked.
     * @param closeness The closeness value to check for in the player's hand.
     * @return True if the player's hand contains a card with the specified closeness; otherwise false.
     */
    public boolean isSpecificCardOnHand(Player player, int closeness) {
        return player.getHand().stream().anyMatch(card -> card.getCloseness() == closeness);
    }

    /**
     * Prompts the player to choose which card to play.
     *
     * @param player The player choosing a card to play.
     * @return The index of the chosen card in the player's hand (0 or 1).
     */
    public int chooseCardToPlay(Player player) {
        return InputHelper.validateInputNumbers(new Integer[]{1, 2}, "Which card do you want to discard?\n" + StringHelper.printCardsBesideEachOther(player.getHand()) + player.getHand().get(0).getName() + " (1) or " + player.getHand().get(1).getName() + " (2)");
    }

    //winning functions

    /**
     * Determines the winner of the game based on the number of needed love tokens.
     *
     * @param neededTokens The number of love tokens required to win the game.
     * @return {@code true} if there is a game winner, {@code false} if no winner has been decided yet.
     */
    private boolean decideWinner(int neededTokens) {
        List<Player> winners = players.stream().filter(player -> player.getLoveToken() == neededTokens).toList();
        if (winners.isEmpty()) {
            return false;
        } else if (winners.size() == 1) {
            gameWinner = winners.get(0);
            return true;
        } else {
            int index;
            System.out.println("There is a tie! Who went most recently on a date?");
            index = InputHelper.validateInputNumbers(new Integer[]{1, 2}, winners.get(0) + "(1) or " + winners.get(1) + "(2)");
            gameWinner = winners.get(index);
            return true;
        }
    }

    /**
     * Switch case to determine if there is a game winner based on the number of players and their love tokens.
     *
     * @return {@code true} if a game winner has been determined, {@code false} otherwise.
     */
    private boolean calculateWinnerOfGame() {
        return switch (players.size()) {
            case 2 -> decideWinner(7);
            case 3 -> decideWinner(5);
            case 4 -> decideWinner(4);
            default -> false;
        };
    }

    /**
     * Calculates the winner of the current round based on player score and discarded cards score and awards a love token to the winner or winners.
     * Players' scores are updated by considering the score of the card at hand first and if there is a tie by considering the discarded cards score. The player with the highest scores
     * (i.e. from their hand and discarded cards) wins the round.
     * In case of a tie where multiple players have the same score (both score and discarded cards score), all tied players receive a love token.
     * The player or one of the tied players with the highest score is designated as the round winner.
     */
    private void calculateWinnerOfRound() {
        for (Player player : activePlayers) {
            player.updateDiscardedCardsScore();
            player.updateScore();
        }
        if (activePlayers.size() > 1) {
            activePlayers.sort(new Player.sortByScore());
            System.out.println("You have following scores and discarded cards scores: " + activePlayers);
            int highestScore = activePlayers.get(0).getScore();
            int highestDiscardedCardsScore = activePlayers.get(0).getDiscardedCardsScore();
            List<Player> winners = activePlayers.stream().filter(player -> (player.getScore() == highestScore) && (player.getDiscardedCardsScore() == highestDiscardedCardsScore)).toList();
            if (winners.size() == 1) {
                roundWinner = activePlayers.get(0);
                roundWinner.addLoveToken(1);

            } else {
                System.out.println("Wow there is a tie!");
                System.out.println(StringHelper.printOnlyNames((ArrayList<Player>) winners) + " have the exact same scores on hand and the same sum of the scores of all their discarded cards...");
                System.out.println("So you get all a love token!");
                for (Player player : winners) {
                    player.addLoveToken(1);
                }
                roundWinner = winners.get(0);
            }
        } else {
            roundWinner = activePlayers.get(0);
            roundWinner.addLoveToken(1);
        }
    }
}