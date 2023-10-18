package de.annalisa.loveletters;

import java.util.ArrayList;
import java.util.Comparator;

import java.util.List;

public class Player {
    private String name;
    private int score;
    private ArrayList<Card> hand;

    public Player(String name){
        this.name = name;
        this.score = 0;
        this.hand = new ArrayList<Card>();
    }

    public String getName() {
        return name;
    }

    public void addCardToHand(Card card){
        this.hand.add(card);
    }

    public ArrayList<Card> getHand() {
        return hand;
    }

    static class sortByScore implements Comparator<Player> {
        public int compare(Player p1, Player p2){
            return p1.score - p2.score;
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
