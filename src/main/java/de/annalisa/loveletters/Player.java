package de.annalisa.loveletters;

import de.annalisa.loveletters.cards.Card;

import java.util.ArrayList;
import java.util.Comparator;

/**
 * The `Player` class represents a player in the Love Letter card game. It manages player attributes,
 * such as name, hand, score, love tokens, and game-related actions.
 */
public class Player {
    private String name;
    private ArrayList<Card> hand;
    private int score;
    private int loveToken;
    private int turn;
    private boolean immune;

    /**
     * Initializes a new player with the given name.
     *
     * @param name The name of the player.
     */
    public Player(String name) {
        this.name = name;
        this.hand = new ArrayList<>();
        this.score = 0;
        this.loveToken = 0;
        this.turn = 1;
        this.immune = false;
    }

    //Getters
    public int getTurn() {
        return turn;
    }

    public String getName() {
        return name;
    }

    public int getLoveToken() {
        return loveToken;
    }

    public ArrayList<Card> getHand() {
        return hand;
    }

    public int getScore() {
        return score;
    }

    /**
     * Checks if the player is immune to card effects.
     *
     * @return {@code true} if the player is immune, {@code false} if the player is not immune.
     */
    public boolean isImmune() {
        return immune;
    }

    //Setters
    public void setImmune() {
        this.immune = !immune;
    }

    public void setScore(int score) {
        this.score = score;
    }

    //Other

    /**
     * Resets the player's immunity status to not immune.
     */
    public void resetImmune() {
        this.immune = false;
    }

    /**
     * Resets the player's current turn to 1.
     */
    public void resetTurn() {
        this.turn = 1;
    }

    /**
     * Increments the player's current turn by 1.
     */
    public void incrementTurn() {
        this.turn++;
    }

    /**
     * Adds a card to the player's hand.
     *
     * @param card The card to add to the player's hand.
     */
    public void addCardToHand(Card card) {
        this.hand.add(card);
    }

    /**
     * Removes a card from the player's hand at the specified index.
     *
     * @param index The index of the card to remove from the player's hand.
     */
    public void removeCardFromHand(int index) {
        if (!hand.isEmpty()) {
            this.hand.remove(index);
        }
    }

    /**
     * Clears (removes) all cards from the player's hand.
     */
    public void clearHand() {
        hand.clear();
    }

    /**
     * Adds love tokens to the player's current love token count.
     *
     * @param loveToken The number of love tokens to add.
     */
    public void addLoveToken(int loveToken) {
        this.loveToken += loveToken;
    }

    /**
     * Updates the player's score based on the sum of the closeness values of the cards on their hand.
     */
    public void updateScore() {
        int res = 0;
        for (Card card : hand) {
            res += card.getCloseness();
        }
        score = res;
    }

    //TODO: maybe implement an equals functions? To compare players?

    //Comparators

    /**
     * A comparator for sorting Player objects based on their scores in descending order.
     * Players with higher scores will appear first in the sorted list.
     */
    public static class sortByScore implements Comparator<Player> {
        /**
         * Compares two Player objects based on their scores.
         *
         * @param p1 The first Player object to compare.
         * @param p2 The second Player object to compare.
         * @return a negative integer, zero, or a positive integer as the first player's score is
         * less than, equal to, or greater than the second player's score.
         */
        public int compare(Player p1, Player p2) {
            return p2.score - p1.score;
        }
    }

    //Formatting

    /**
     * Returns a string representation of the player including their name, score, and hand.
     *
     * @return A string containing the player's name, score, and hand.
     * @warning should be rewritten to adjust to the new representation of the card/hand.
     */
    @Override
    public String toString() {
        return name + " - " + score + " - " + hand;
    }
}
