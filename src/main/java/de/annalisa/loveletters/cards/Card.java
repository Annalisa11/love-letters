package de.annalisa.loveletters.cards;

import de.annalisa.loveletters.Game;

import java.util.ArrayList;

public abstract class Card {
    private String name;
    private String effect;
    private int closeness;

    public Card(String name, int closeness, String effect){
        this.name = name;
        this.effect = effect;
        this.closeness = closeness;
    }

    //Getters
    public abstract void applyEffect(Game game);

    public String getEffect(){
        return effect;
    }

    public String getName() {
        return name;
    }

    public int getCloseness() {
        return closeness;
    }

    //TODO: maybe implement an equals functions? To compare cards?

    /**
     * Splits a given string into an array of substrings, ensuring that each substring
     * contains a maximum number of characters (or less) and does not cut words in the middle.
     *
     * @param string    The input string to be split.
     * @param maxChars  The maximum number of characters allowed in each substring/line.
     * @return An array of substrings, each of which is less than or equal to maxChars characters in length,
     * and words are not split in the middle. Leading and trailing spaces are trimmed.
     */
    private String[] splitStringAtNextSpaceAfterMaxChars(String string, int maxChars){
        if (string.length() <= maxChars) {
            return new String[]{string};
        }
        ArrayList<String> splits = new ArrayList<>();

        int index = 0;
        while (index < string.length()) {
            int endIndex = Math.min(index + maxChars, string.length());
            String part = string.substring(index, endIndex);

            if (endIndex < string.length() && string.charAt(endIndex) != ' ') {
                // check if end char is not a space and if not move endIndex backwards until you find one
                while (endIndex > index && string.charAt(endIndex) != ' ') {
                    endIndex--;
                }
                part = string.substring(index, endIndex);
            }

            splits.add(part);
            index = endIndex;

            // ignore leading spaces in the next string
            while (index < string.length() && string.charAt(index) == ' '){
                index++;
            }
        }
        return splits.toArray(new String[0]);
    }

    /**
     * Combines multiple cards into a single string so that multiple cards can be displayed side by side.
     *
     * @param cards An arrayList of Card objects to display next to each other.
     * @return A string ready to print to the console containing the cards displayed beside each other.
     */
    public static String printCardsBesideEachOther(ArrayList<Card> cards) {
        StringBuilder string = new StringBuilder();
        int maxLines = cards.get(0).toString().split("\n").length; //get the amount of lines of a card
        for (int i = 0; i < maxLines; i++) {
            for (Card card : cards) {
                string.append(card.toString().split("\n")[i]).append("  ");
            }
            string.append("\n");
        }
        return string.toString();
    }

    @Override
    public String toString(){
        StringBuilder cardString = new StringBuilder();

        String leftAlignFormat = "| %-20s | %4d |";
        String effectFormat = "| %-27s |";

        cardString.append("+----------------------+------+\n");
        cardString.append(String.format(leftAlignFormat, name, closeness)).append("\n");
        cardString.append("+----------------------+------+\n");

        String[] effectLines = splitStringAtNextSpaceAfterMaxChars(effect, 27);
        //make every card's length the same adding white space if necessary
        for (int i=0; i<6; i++){
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
