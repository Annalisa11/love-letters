package de.annalisa.loveletters.cards;

import de.annalisa.loveletters.Game;
import de.annalisa.loveletters.utils.StringHelper;

/**
 * The `Card` class is an abstract base class for representing cards in the Love Letter card game.
 * Each card has a name, effect, and a closeness value. Subclasses of `Card` implement specific card effects.
 */
public abstract class Card {
    private final String name;
    private final String effect;
    private final int closeness;

    /**
     * Creates a new `Card` instance with a specified name, closeness value, and effect.
     *
     * @param name      The name of the card.
     * @param closeness The closeness value of the card.
     * @param effect    The effect description of the card.
     */
    public Card(String name, int closeness, String effect) {
        this.name = name;
        this.effect = effect;
        this.closeness = closeness;
    }

    //Getters

    public String getName() {
        return name;
    }

    public int getCloseness() {
        return closeness;
    }

    /**
     * Applies the specific effect of this card to the game.
     *
     * @param game The current `Game` instance where the card's effect is applied.
     */
    public abstract void applyEffect(Game game);

    //TODO: maybe implement an equals functions? To compare cards?


    /**
     * Converts the card to a formatted string for display.
     *
     * @return A formatted string representing the card as an actual card-looking card.
     */
    @Override
    public String toString() {
        StringBuilder cardString = new StringBuilder();

        String leftAlignFormat = "| %-20s | %4d |";
        String effectFormat = "| %-27s |";

        cardString.append("+----------------------+------+\n");
        cardString.append(String.format(leftAlignFormat, name, closeness)).append("\n");
        cardString.append("+----------------------+------+\n");

        String[] effectLines = StringHelper.splitStringAtNextSpaceAfterMaxChars(effect, 27);
        //make every card's length the same adding white space if necessary
        for (int i = 0; i < 6; i++) {
            if (i < effectLines.length) {
                cardString.append(String.format(effectFormat, effectLines[i])).append("\n");
            } else {
                cardString.append(String.format(effectFormat, "")).append("\n");
            }
        }

        cardString.append("+-----------------------------+\n");

        return cardString.toString();
    }
}