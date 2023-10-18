package de.annalisa.loveletters.commands;

import de.annalisa.loveletters.commands.impl.StartCommand;
import de.annalisa.loveletters.commands.impl.playCardCommand;
import de.annalisa.loveletters.commands.impl.showHandCommand;
import de.annalisa.loveletters.commands.impl.showScoreCommand;

import java.util.ArrayList;
import java.util.List;

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

    public void handleInput(String input){
        String strippedInput = input.substring(1);
        commands.stream().filter(command -> command.getCommand().equals(strippedInput)).findFirst().ifPresent(Command::execute);
    }

}
