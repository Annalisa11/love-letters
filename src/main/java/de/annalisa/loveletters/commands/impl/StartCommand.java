package de.annalisa.loveletters.commands.impl;

import de.annalisa.loveletters.Game;
import de.annalisa.loveletters.commands.Command;

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
    @Override
    public boolean execute(Game game, boolean inGame) {
        if(inGame){
            System.out.println("you have to end the program and start again to start a new game.");
            return true;
        }
        game.startGame();
        return false;
    }
}
