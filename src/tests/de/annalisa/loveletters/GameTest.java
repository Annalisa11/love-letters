package de.annalisa.loveletters;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.*;

class GameTest {

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void startGame() {
        // Create a new game
        Game game = new Game();

        // Create two players with specific cards
        Player player1 = new Player("Player1");
        player1.addCardToHand(new Card("Countess", 7, "If you have this card and the King or Prince in your hand, you must discard this card."));
        player1.addCardToHand(new Card("Priest", 2, "Look at another player's hand"));

        Player player2 = new Player("Player2");
        player2.addCardToHand(new Card("Handmaid", 4, "Until your next turn, ignore all effects from other players' cards."));
        player2.addCardToHand(new Card("Priest", 2, "Look at another player's hand"));

        // Add the players to the game
        game.getPlayers().add(player1);
        game.getPlayers().add(player2);

        // Create a deck with specific cards
        game.createDeck();
        game.getDeck().addCard(new Card("King", 6, "Trade hands with another player your choice"));
        game.getDeck().addCard(new Card("Prince", 5, "Choose any player (including yourself) to discard his or her hand and draw a new card"));
        game.getDeck().addCard(new Card("Prince", 5, "Choose any player (including yourself) to discard his or her hand and draw a new card"));

        // Simulate the game
        game.startGame();

        // Add your assertions here to verify the game results
        // For example:
        // assertEquals(player1, game.gameWinner); // Check the expected winner
        // assertEquals(expectedScore, player1.getScore()); // Check the expected score for player1
    }

}