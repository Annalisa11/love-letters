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
