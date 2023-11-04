/**
 * The "start" command allows a player to start a new game of Love Letter.
 *
 * When executed, this command is used to begin a new game within the context of the Love Letter game. It initializes the game
 * and allows players to start playing from the beginning.
 */
package de.annalisa.loveletters.commands.impl;

import de.annalisa.loveletters.Game;
import de.annalisa.loveletters.commands.Command;
import de.annalisa.loveletters.exceptions.ExitGameException;

public class StartCommand implements Command {

    @Override
    public String getCommand() {
        return "start";
    }

    @Override
    public String[] getUsages() {
        return new String[0];
    }

    /**
     * Get the description of the "start" command.
     *
     * @return A brief description of the "start" command and its purpose in the Love Letter game.
     */
    @Override
    public String getDescription() {
        return "With 'start,' you can start a new game. Have fun!";
    }

    /**
     * Execute the "start" command to begin a new game of Love Letter.
     * The command initializes the game and allows players to start playing from the beginning.
     *
     * @param game   The current game instance where the command will be executed.
     * @param inGame A boolean indicating whether the command is executed during a game session.
     * @return {@code true} of the loop should ask again for input, since it was invalid. Otherwise, {@code false}.
     * @throws ExitGameException to indicate that the Love Letter game should exit or terminate. This exception is used to control the flow of the game and allow players to choose whether to play again or exit the game.
     */
    @Override
    public boolean execute(Game game, boolean inGame) throws ExitGameException {
        if (inGame) {
            System.out.println("❗ You have to end the program and start again or use the '\\exit' command to start a new game.");
            return true;
        }
        game.startGame();
        return false;
    }
}
