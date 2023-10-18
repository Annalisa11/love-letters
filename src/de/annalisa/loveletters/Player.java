package de.annalisa.loveletters;

import java.util.ArrayList;
import java.util.Comparator;

import java.util.List;
import java.util.stream.IntStream;

public class Player {
    private String name;
    private int score;
    private ArrayList<Card> hand;
    private int loveToken;
    private boolean immune;
    private int turn;

    public Player(String name){
        this.name = name;
        this.score = 0;
        this.hand = new ArrayList<Card>();
        this.loveToken = 0;
        this.immune = false;
        this.turn = 1;
    }

    public int getTurn() {
        return turn;
    }

    public void resetTurn() {
        this.turn = 1;
    }
    public void incrementTurn() {
        this.turn++;
    }

    public boolean isImmune() {
        return immune;
    }

    public void setImmune() {
        this.immune = !immune;
    }

    public String getName() {
        return name;
    }

    public int getLoveToken() {
        return loveToken;
    }

    public void addLoveToken(int loveToken) {
        this.loveToken += loveToken;
    }

    public void addCardToHand(Card card){
        this.hand.add(card);
    }

    public void removeCardFromHand(int index){
        if(!hand.isEmpty()){
            this.hand.remove(index);
        }
    }

    public ArrayList<Card> getHand() {
        return hand;
    }

    public void updateScore(){
        int res = 0;
        for(Card card : hand){
            res += card.getCloseness();
        }
        score = res;
    }

    public int getScore(){
        return score;
    }

    public void clearHand() {
        hand.clear();
    }

    static class sortByScore implements Comparator<Player> {
        public int compare(Player p1, Player p2){
            return p2.score - p1.score;
        }
    }

    @Override
    public String toString() {
        return  name + " - " + score + " - " + hand;
    }

    public String cardsOnHand(){
        return "You have these cards on your hand:\n" + hand + "\n";
    }
}
