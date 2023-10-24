package de.annalisa.loveletters;

import de.annalisa.loveletters.cards.Card;

import java.util.ArrayList;
import java.util.Random;

/**
 * The `Deck` class represents a deck of cards used in a Love Letter card game.
 * It manages card operations such as shuffling, drawing and adding and removing cards.
 */
public class Deck {
    private final Random random = new Random();
    private ArrayList<Card> cards;
    private int numberOfCards;


    /**
     * Initializes a new empty deck with no cards.
     */
    public Deck(){
        this.cards = new ArrayList<>();
        this.numberOfCards = 0;
    }

    //Getters
    public int getNumberOfCards() {
        return numberOfCards;
    }

    /**
     * Retrieves the top card from the deck, removing it from the deck.
     *
     * @return The top card of the deck.
     */
    public Card getTopCard(){
        Card topCard = cards.get(cards.size()-1);
        cards.remove((cards.size()-1));
        numberOfCards--;
        return topCard;
    }

    /**
     * Retrieves the top card from the deck. If empty, it returns a special card that is specified.
     *
     * @param firstCard The card to use as a fallback if the deck is empty. Normally the first card of the deck that was drawn in the beginning.
     * @return The top card of the deck if available; otherwise the fallback card.
     */
    public Card getTopCardWithSpecialFallback(Card firstCard){
        if(cards.isEmpty()){
            return firstCard;
        }
        return getTopCard();
    }

    /**
     * Adds a card to the deck.
     *
     * @param card The card to be added to the deck.
     */
    public void addCard(Card card){
        cards.add(card);
        numberOfCards++;
    }

    /**
     * Adds the same card to the deck multiple times.
     *
     * @param card The card to be added to the deck.
     * @param n    The number of times the card should be added.
     */
    public void addSameCardNTimes(Card card, int n){
        for (int i=0; i<n; i++){
            addCard(card);
        }
    }

    /**
     * Shuffles the cards in the deck to randomize their order.
     */
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

    /**
     * Clears the deck, removing all cards.
     */
    public void clearDeck(){
        cards.clear();
        numberOfCards = 0;
    }

    /**
     * Converts the deck to a formatted string containing the representations of its cards.
     * @warning should be written anew, since the UI of cards has been changed.
     *
     * @return A formatted string representing the deck, including its cards.
     */
    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        for(Card card: cards){
            result.append(card);
        }
        return result.toString();
    }
}
