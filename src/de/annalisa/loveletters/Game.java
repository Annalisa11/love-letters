package de.annalisa.loveletters;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.*;

public class Game {

    private ArrayList<Player> players;
    private Deck deck;
    private int round;
    private int numberOfCards = 16;

    private String[] names = {"Prince", "Prince", "King", "Countess", "Princess", "Guard", "Guard", "Guard", "Guard", "Guard", "Priest", "Priest", "Baron", "Baron", "Handmaid", "Handmaid"};
    private int[] closeness = {5, 5, 6, 7, 8, 1, 1, 1, 1, 1, 2, 2, 3, 3, 4, 4};
    private String[] effects = {"Choose any player (including yourself) to discard his or her hand and draw a new card", "Choose any player (including yourself) to discard his or her hand and draw a new card", "Trade hands with another player your choice", "If you have this card and the King or Prince in your hand, you must discard this card.", "If you discard this card, you are out of the round", "Name a non-Guard card and choose another player. If that player has that card, he or she is out of the round.", "Name a non-Guard card and choose another player. If that player has that card, he or she is out of the round.", "Name a non-Guard card and choose another player. If that player has that card, he or she is out of the round.", "Name a non-Guard card and choose another player. If that player has that card, he or she is out of the round.", "Name a non-Guard card and choose another player. If that player has that card, he or she is out of the round.", "Look at another player's hand", "Look at another player's hand", "You and another player secretly compare hands. The player with the lower value is out of the round.", "You and another player secretly compare hands. The player with the lower value is out of the round.", "Until your next turn, ignore all effects from other players' cards.", "Until your next turn, ignore all effects from other players' cards."};

    public void startGame(){
        createDeck();
        shuffleDeck();
        System.out.println(deck);
    }

    private void shuffleDeck() {
        deck.shuffleDeck();
    }

    private void createDeck() {
        deck = new Deck();
        for (int i = 0 ; i < numberOfCards; i++){
            Card card = new Card(names[i], closeness[i], effects[i]);
            deck.addCard(card);
        }
        System.out.println(deck);
    }

    public Game(){
        startGame();
        System.out.println("game");
    }
    public static void main(String[] args) {
        System.out.println("hallo");
        System.out.println("pierre");
        Game game = new Game();

    }
}
