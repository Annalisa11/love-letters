package de.annalisa.loveletters;

import java.util.ArrayList;
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

    @Override
    public String toString() {
        return  name + " - " + score + " - " + hand;
    }
}
