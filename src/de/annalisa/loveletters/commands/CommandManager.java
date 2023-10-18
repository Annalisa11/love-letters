package de.annalisa.loveletters.commands;

import de.annalisa.loveletters.Game;
import de.annalisa.loveletters.commands.impl.StartCommand;
import de.annalisa.loveletters.commands.impl.playCardCommand;
import de.annalisa.loveletters.commands.impl.showHandCommand;
import de.annalisa.loveletters.commands.impl.showScoreCommand;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CommandManager {
    private List<Command> commands = new ArrayList<>();

    public CommandManager(){
        this.commands.add(new StartCommand());
        this.commands.add(new playCardCommand());
        this.commands.add(new showHandCommand());
        this.commands.add(new showScoreCommand());
    }

    public boolean isCommand(String input){
        return input.startsWith("\\");
    }

    public boolean handleInput(String input, Game game){
        String strippedInput = input.substring(1);
        Optional<Command> commandToExecute = commands.stream().filter(command -> command.getCommand().equals(strippedInput)).findFirst();
        if (commandToExecute.isEmpty()){
            return true;
        }
        return commandToExecute.get().execute(game);
    }

}
