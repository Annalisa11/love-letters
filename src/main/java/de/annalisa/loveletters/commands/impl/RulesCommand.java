package de.annalisa.loveletters.commands.impl;

import de.annalisa.loveletters.Game;
import de.annalisa.loveletters.commands.Command;
import de.annalisa.loveletters.utils.FileHelper;
import de.annalisa.loveletters.utils.StringHelper;

public class RulesCommand implements Command {
    private final String[] rules;
    private static final String RULES_PATH = "rules.txt";

    /**
     * Constructs a new `RulesCommand` instance by reading the rules from a file in the resources directory.
     * The constructor retrieves the rules text from a file named "rules.txt" located in the resources directory.
     * It ensures that the `rulesText` is not null, and then splits it into an array of substrings with a maximum
     * line length of 80 characters, ensuring that words are not split in the middle.
     */
    public RulesCommand(){
        String rulesText = FileHelper.readFileFromResources(RULES_PATH);
        if(rulesText != null){
            rules = StringHelper.splitStringAtNextSpaceAfterMaxChars(rulesText, 80);
        } else {
            rules = new String[]{"Oh no.. something went wrong :("};
        }
    }
    @Override
    public String getCommand() {
        return "rules";
    }

    @Override
    public String[] getUsages() {
        return new String[0];
    }

    @Override
    public String getDescription() {
        return "if you don't know the rules yet, you can read them with the rules command.";
    }

    @Override
    public boolean execute(Game game, boolean inGame){
        if(rules != null) {
            for (String ruleLine : rules){
                System.out.println(ruleLine);
            }
        }
        return true;
    }
}
