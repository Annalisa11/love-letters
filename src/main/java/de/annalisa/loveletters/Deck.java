package de.annalisa.loveletters;

import de.annalisa.loveletters.cards.Card;

import java.util.ArrayList;
import java.util.Random;

public class Deck {
    private final Random random = new Random();
    private ArrayList<Card> cards;
    private int numberOfCards;


    public Deck(){
        this.cards = new ArrayList<>();
        this.numberOfCards = 0;
    }

    //Getters
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

    public void addSameCardNTimes(Card card, int n){
        for (int i=0; i<n; i++){
            addCard(card);
        }
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

    public void clearDeck(){
        cards.clear();
        numberOfCards = 0;
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
