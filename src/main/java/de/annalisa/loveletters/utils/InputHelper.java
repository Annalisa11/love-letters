package de.annalisa.loveletters.utils;

import de.annalisa.loveletters.Game;
import de.annalisa.loveletters.commands.CommandManager;

import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * The InputHelper class provides utility methods for handling user input in the Love Letter game.
 */
public class InputHelper {
    /**
     * Continuously waits for user input and processes it as a command. <br/>
     * This method is suitable for situations where user input is not required for specific numeric values, and the user is free to choose from a list of available commands.
     *
     * @param game The current game instance that will handle the commands.
     */
    public static void waitForCommandInput(Game game){
        CommandManager commandManager = game.getCommandManager();
        try (Scanner scanner = new Scanner(System.in)) {
            boolean scan = true;
            while (scan) {
                String input = scanner.nextLine();
                if (commandManager.isCommand(input)) {
                    scan = commandManager.handleInput(input, game);
                } else {
                    System.out.println("⚠ Input not valid. Check out valid commands with '\\help.");
                }
            }
        }
    }

    /**
     * Validates and retrieves an integer input from the user within a specified set of valid values.
     * This method prompts the user for input and ensures that the input is an integer within the valid values.
     * If the input is not a valid integer or is outside the specified valid values, the user will be prompted to try again.
     *
     * @param validValues An array of valid integer values that the user's input can match.
     * @param message     The prompt message displayed to the user.
     * @param exceptions  An optional array of values that are considered exceptions and will not be treated as valid.
     * @return The valid integer input provided by the user.
     * <ul>
     *     <li><code>-1</code> - Returns <code>-1</code> if all players are immune.</li>
     *     <li><code>200</code> - Returns <code>200</code> if all players are immune AND the player wants to apply the Prince effect and therefore has to choose himself.</li>
     * </ul>
     * @throws InputMismatchException If the user enters a non-integer value.
     */
    public static int validateInputNumbers(Integer[] validValues, String message, Integer... exceptions){
        try (Scanner scanner = new Scanner(System.in)) {
            int input;
            while (true) {
                System.out.println(message);
                String inputString = scanner.nextLine();
                try {
                    input = Integer.parseInt(inputString);

                    if (!Arrays.asList(validValues).contains(input) || Arrays.asList(exceptions).contains(input)) {
                        System.out.println("⚠ invalid input. Try again.");
                        continue;
                    }
                    return input;
                } catch (Exception e) {
                    System.out.println("⚠ Please enter a number.");
                }
            }
        }
    }

    /**
     * Reads a string input from the console and returns it.
     *
     * @return The string entered by the user in the console.
     */
    public static String getStringFromConsole(){
        try (Scanner scanner = new Scanner(System.in)) {
            return scanner.nextLine();
        }
    }
}
