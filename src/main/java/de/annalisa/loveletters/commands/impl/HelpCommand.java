/**
 * The "help" command provides information about available commands and their descriptions.
 * <p>
 * When executed, this command lists all available commands and their descriptions to help players understand
 * how and when to use them within the game.
 */
package de.annalisa.loveletters.commands.impl;

import de.annalisa.loveletters.Game;
import de.annalisa.loveletters.commands.Command;

import java.util.List;

public class HelpCommand implements Command {
    @Override
    public String getCommand() {
        return "help";
    }

    @Override
    public String[] getUsages() {
        return new String[0];
    }

    /**
     * Get the description of the "help" command.
     *
     * @return A brief description of the "help" command and its purpose.
     */
    @Override
    public String getDescription() {
        return "With 'help,' you can view all the available commands and their usage instructions.";
    }

    /**
     * Execute the "help" command to display a list of available commands and their descriptions.
     *
     * @param game   The Love Letter game in progress.
     * @param inGame A boolean flag indicating whether the command is executed in a game context.
     * @return true so that the input loop keeps scanning for further commands/inputs.
     */
    @Override
    public boolean execute(Game game, boolean inGame) {
        System.out.println("You can use these commands:");
        List<Command> commands = game.getCommandManager().getCommands();
        for (Command command : commands) {
            System.out.println("\uD83E\uDD0D \\" + command.getCommand() + "   ->   " + command.getDescription());
        }
        return true;
    }
}
