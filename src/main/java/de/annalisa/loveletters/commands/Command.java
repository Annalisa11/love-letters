package de.annalisa.loveletters.commands;

import de.annalisa.loveletters.Game;
import de.annalisa.loveletters.exceptions.ExitGameException;

/**
 * The `Command` interface defines the structure for implementing different game commands in the Love Letter game.
 * It provides methods to retrieve information about the command and execute it.
 */
public interface Command {
    /**
     * Retrieves the command name or keyword.
     *
     * @return The name or keyword of the command.
     */
    String getCommand();

    /**
     * Retrieves an array of usage descriptions for the command.
     *
     * @return An array of usage descriptions for the command, each describing how the command can be used.
     */
    String[] getUsages();

    /**
     * Retrieves a brief description of what the command does.
     *
     * @return A brief description of the command's functionality.
     */
    String getDescription();

    /**
     * Executes the command based on the fact, if the player is currently playing a game or not.
     *
     * @param game   The current game instance where the command will be executed.
     * @param inGame A boolean indicating whether the command is executed during a game session.
     * @return {@code false} if the command execution was successful and thus the input loop should stop asking for input, {@code true} otherwise.
     * @throws  ExitGameException to indicate that the Love Letter game should exit or terminate. This exception is used to control the flow of the game and allow players to choose whether to play again or exit the game.
     */
    boolean execute(Game game, boolean inGame) throws ExitGameException;
}
