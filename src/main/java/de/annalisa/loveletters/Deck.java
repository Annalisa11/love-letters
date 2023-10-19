package de.annalisa.loveletters;

import java.util.ArrayList;
import java.util.Random;

public class Deck {
    private final int deckSize;
    private Random random = new Random();
    private ArrayList<Card> cards;
    private int numberOfCards;


    public Deck(int deckSize){
        this.deckSize = deckSize;
        this.cards = new ArrayList<Card>();
        this.numberOfCards = 0;
    }

    //Getters
    public int getDeckSize() {
        return deckSize;
    }

    public int getNumberOfCards() {
        return numberOfCards;
    }

    public Card getTopCard(){

        Card topCard = cards.get(cards.size()-1);
        cards.remove((cards.size()-1));
        numberOfCards--;
        return topCard;
    }

    public Card getTopCardWithSpecialFallback(Card firstCard){
        if(cards.isEmpty()){
            return firstCard;
        }
        return getTopCard();
    }

    public void addCard(Card card){
        cards.add(card);
        numberOfCards++;
    }

    public void shuffleDeck(){
        ArrayList<Card> unshuffled = new ArrayList<>(cards);
        ArrayList<Card> shuffled = new ArrayList<>();
        while(!unshuffled.isEmpty()){
            Card card = unshuffled.get(random.nextInt(unshuffled.size()));
            unshuffled.remove(card);
            shuffled.add(card);
        }
        this.cards = shuffled;
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        for(Card card: cards){
            result.append(card);
        }
        return result.toString();
    }
}
