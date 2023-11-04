/**
 * The "exit" command allows a player to exit the current game session or retry a new game at any point.
 * <p>
 * When executed, this command can be used to exit the current game and return to the main menu or to start a new game.
 */
package de.annalisa.loveletters.commands.impl;

import de.annalisa.loveletters.Game;
import de.annalisa.loveletters.commands.Command;
import de.annalisa.loveletters.exceptions.ExitGameException;

public class ExitCommand implements Command {
    @Override
    public String getCommand() {
        return "exit";
    }

    @Override
    public String[] getUsages() {
        return new String[0];
    }

    /**
     * Get the description of the "exit" command.
     *
     * @return A brief description of the "exit" command and its purpose in the Love Letter game.
     */
    @Override
    public String getDescription() {
        return "With 'exit,' you can exit the current game or start a new game at any point.";
    }

    /**
     * Execute the "exit" command to either exit the current game session or return to the main menu for a new game.
     *
     * @param game   The current game instance where the command will be executed.
     * @param inGame A boolean indicating whether the command is executed during a game session.
     * @return {@code true} of the loop should ask again for input, since it was invalid. Otherwise, ExitGameException is thrown.
     * @throws ExitGameException to indicate that the Love Letter game should exit or terminate. This exception is used to control the flow of the game and allow players to choose whether to play again or exit the game.
     */
    @Override
    public boolean execute(Game game, boolean inGame) throws ExitGameException {
        if (!inGame) {
            System.out.println("You have to start a game to be able to exit it. If you don't want to play anymore, just close the program.");
            return true;
        }
        throw new ExitGameException("You exited this game...");
    }
}
