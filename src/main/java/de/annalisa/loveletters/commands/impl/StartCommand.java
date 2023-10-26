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

    @Override
    public String getDescription() {
        return "With start you can start the game. Have fun!";
    }

    //TODO: maybe make it abstract? So that if(inGame) is not implemented every time

    /**
     *
     * @param game   The current game instance where the command will be executed.
     * @param inGame A boolean indicating whether the command is executed during a game session.
     * @return {@code true} of the loop should ask again for input, since it was invalid. Otherwise, {@code false}.
     * @throws  ExitGameException to indicate that the Love Letter game should exit or terminate. This exception is used to control the flow of the game and allow players to choose whether to play again or exit the game.
     */
    @Override
    public boolean execute(Game game, boolean inGame) throws ExitGameException {
        if(inGame){
            System.out.println("❗ you have to end the program and start again or use the '\\exit' command to start a new game.");
            return true;
        }
        game.startGame();
        return false;
    }
}
