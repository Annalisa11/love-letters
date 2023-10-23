package de.annalisa.loveletters;

import de.annalisa.loveletters.cards.Card;

import java.util.ArrayList;
import java.util.Comparator;

public class Player {
    private String name;
    private ArrayList<Card> hand;
    private int score;
    private int loveToken;
    private int turn;
    private boolean immune;

    public Player(String name){
        this.name = name;
        this.hand = new ArrayList<Card>();
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

    public int getScore(){
        return score;
    }

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

    public void resetImmune() {
        this.immune = false;
    }

    //Other
    public void resetTurn() {
        this.turn = 1;
    }
    public void incrementTurn() {
        this.turn++;
    }

    public void addCardToHand(Card card){
        this.hand.add(card);
    }

    public void removeCardFromHand(int index){
        if(!hand.isEmpty()){
            this.hand.remove(index);
        }
    }

    public void clearHand() {
        hand.clear();
    }

    public void addLoveToken(int loveToken) {
        this.loveToken += loveToken;
    }

    public void updateScore(){
        int res = 0;
        for(Card card : hand){
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
         *         less than, equal to, or greater than the second player's score.
         */
        public int compare(Player p1, Player p2){
            return p2.score - p1.score;
        }
    }

    //Formatting
    @Override
    public String toString() {
        return  name + " - " + score + " - " + hand;
    }

    public String cardsOnHand(){
        return "You have these cards on your hand:\n" + hand + "\n";
    }
}
