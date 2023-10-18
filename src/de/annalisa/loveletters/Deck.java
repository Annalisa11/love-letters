package de.annalisa.loveletters;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class Deck {
    private Random random = new Random();
    private ArrayList<Card> cards;
    private int numberOfCards;
    private final int DECK_SIZE;


    public Deck(int deckSize){
        this.cards = new ArrayList<Card>();
        this.DECK_SIZE = deckSize;
        this.numberOfCards = 0;
    }

    public int getDeckSize() {
        return DECK_SIZE;
    }

    public int getNumberOfCards() {
        return numberOfCards;
    }

    public void addCard(Card card){
        cards.add(card);
        numberOfCards++;
    }

    public void shuffleDeck(){
//        Collections.shuffle(cards);
        ArrayList<Card> unshuffled = new ArrayList<>(cards);
        ArrayList<Card> shuffled = new ArrayList<>();
        while(!unshuffled.isEmpty()){
            Card card = unshuffled.get(random.nextInt(unshuffled.size()));
            unshuffled.remove(card);
            shuffled.add(card);
        }
        this.cards = shuffled;
    }
    

    public Card getTopCard(){
        Card topCard = cards.get(cards.size()-1);
        cards.remove((cards.size()-1));
        numberOfCards--;
        return topCard;
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
