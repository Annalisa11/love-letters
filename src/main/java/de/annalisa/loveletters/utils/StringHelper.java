package de.annalisa.loveletters.utils;

import de.annalisa.loveletters.Player;
import de.annalisa.loveletters.cards.Card;

import java.util.ArrayList;
import java.util.List;


/**
 * The StringHelper class provides utility methods for handling strings, text formatting, and console output in the Love Letter game.
 */
public class StringHelper {
    /**
     * Displays an introduction message for the Love Letter game.
     */
    public static void displayIntroduction(){
        System.out.println("------  Welcome to \uD83D\uDC8C LOVE LETTER! \uD83D\uDC8C ------");
        System.out.println("To start the game write '\\start'. To see other commands write '\\help'.");
    }

    /**
     * Prints the names of the players.
     *
     * @param players The list of players.
     * @return A formatted string containing the names of the players.
     */
    public static String printOnlyNames(ArrayList<Player> players) {
        List<String> names = players.stream().map(Player::getName).toList();
        StringBuilder res = new StringBuilder();
        if (names.size() == 2) {
            return names.get(0) + " and " + names.get(1);
        } else if (names.size() == 1) {
            return names.get(0);
        } else {
            for(int i=0; i<(names.size()-1); i++){
                res.append(names.get(i)).append(", ");
            }
            res = new StringBuilder(res.substring(0, res.length() - 2));
            res.append(" and ").append(names.get(names.size() - 1));
        }

        return res.toString();
    }

    /**
     * Prints the three open cards in the game.
     *
     * @param openCards The list of open cards.
     */
    public static void printThreeOpenCards(ArrayList<Card> openCards){
        System.out.println("three open cards:");
        System.out.println(StringHelper.printCardsBesideEachOther(openCards));
    }

    /**
     * Splits a given string into an array of substrings, ensuring that each substring
     * contains a maximum number of characters (or less) and does not cut words in the middle.
     *
     * @param string    The input string to be split.
     * @param maxChars  The maximum number of characters allowed in each substring/line.
     * @return An array of substrings, each of which is less than or equal to maxChars characters in length,
     * and words are not split in the middle. Leading and trailing spaces are trimmed.
     */
    public static String[] splitStringAtNextSpaceAfterMaxChars(String string, int maxChars){
        if (string.length() <= maxChars) {
            return new String[]{string};
        }
        ArrayList<String> splits = new ArrayList<>();

        int index = 0;
        while (index < string.length()) {
            int endIndex = Math.min(index + maxChars, string.length());
            String part = string.substring(index, endIndex);

            if(part.contains("\n")){
                endIndex = string.substring(0, endIndex).indexOf("\n", index); //so that index is from string and matches the index numbers of part. Otherwise, the index would be always too small
                part = string.substring(index, endIndex);
                string = string.substring(0, endIndex).concat(string.substring(endIndex+1));
            } else {
                if (endIndex < string.length() && string.charAt(endIndex-1) != ' ') {
                    // check if end char is not a space and if not move endIndex backwards until you find one
                    while (endIndex > index && string.charAt(endIndex-1) != ' ') {
                        endIndex--;
                    }
                    part = string.substring(index, endIndex);
                }
            }

            splits.add(part);
            index = endIndex;

            // ignore leading spaces in the next string
            while (index < string.length() && (string.charAt(index) == ' ' )){
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
}
