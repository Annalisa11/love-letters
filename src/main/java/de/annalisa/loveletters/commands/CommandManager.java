package de.annalisa.loveletters.commands;

import de.annalisa.loveletters.Game;
import de.annalisa.loveletters.commands.impl.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CommandManager {
    private List<Command> commands = new ArrayList<>();

    private boolean inGame = false;

    public CommandManager() {
        this.commands.add(new StartCommand());
        this.commands.add(new PlayCardCommand());
        this.commands.add(new ShowHandCommand());
        this.commands.add(new ShowScoreCommand());
        this.commands.add(new HelpCommand());
        this.commands.add(new ShowPlayersCommand());
        this.commands.add(new RulesCommand());
        //TODO: add an exit and/or retry command?
    }

    public List<Command> getCommands() {
        return commands;
    }

    public void setInGame(boolean value) {
        inGame = value;
    }

    public boolean isCommand(String input) {
        return input.startsWith("\\");
    }

    /**
     * Handles user input by checking if it corresponds to a valid command, and executes the command if found.
     *
     * @param input The user's input, which is tested to determine if it is a valid command starting with a backslash ('\').
     * @param game  The current game instance.
     * @return <ul>
     *   <li><code>true</code> - Returns <code>true</code> if the input is not a valid command, and the loop should keep asking for new input.</li>
     *   <li><code>false</code> - Returns <code>false</code> if the command has been successfully carried out, and the loop should stop asking.</li>
     * </ul>
     */
    public boolean handleInput(String input, Game game) {
        String strippedInput = input.substring(1);
        Optional<Command> commandToExecute = commands.stream().filter(command -> command.getCommand().equals(strippedInput)).findFirst();
        if (commandToExecute.isEmpty()) {
            return true;
        }
        return commandToExecute.get().execute(game, inGame);
        //TODO: when entering invalid command you have to press enter once again because there is a random empty line
    }

}
