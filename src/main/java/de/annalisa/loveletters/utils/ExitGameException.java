package de.annalisa.loveletters.utils;

/**
 * The `ExitGameException` is a custom exception class.
 * It is thrown to indicate that the Love Letter game should exit or terminate. This exception is used to control the flow of the game and allow players to choose whether to play again or exit the game.
 *
 * @see Exception
 */
public class ExitGameException extends Exception {
    /**
     * Constructs an `ExitGameException` with the specified detail message.
     *
     * @param message The detail message describing the reason for the exception.
     */
    public ExitGameException(String message) {
        super(message);
    }
}
