package de.annalisa.loveletters;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Deck {
    private ArrayList<Card> cards;

    public Deck(){
        this.cards = new ArrayList<Card>();
    }

    public void addCard(Card card){
        cards.add(card);
    }

    public void shuffleDeck(){
        Collections.shuffle(cards);
    }

    @Override
    public String toString() {
        String result = "";
        for(Card card: cards){
            result += card;
        }
        return result;
    }
}
