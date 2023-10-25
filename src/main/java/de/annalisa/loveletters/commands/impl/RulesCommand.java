package de.annalisa.loveletters.commands.impl;

import de.annalisa.loveletters.Game;
import de.annalisa.loveletters.cards.Card;
import de.annalisa.loveletters.commands.Command;

import java.io.*;

public class RulesCommand implements Command {
    private String[] rules;

    public RulesCommand(){
        String rulesText = readFileFromResources("rules.txt");
        assert rulesText != null;
        rules = Card.splitStringAtNextSpaceAfterMaxChars(rulesText, 80);
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

    private String readFileFromResources(String filePath){
        try {
            var stream = ClassLoader.getSystemResourceAsStream(filePath);
            StringBuilder stringBuilder = new StringBuilder();

            BufferedReader br = new BufferedReader(new InputStreamReader(stream));
            String ruleLine;
            while ((ruleLine = br.readLine())!= null){
                stringBuilder.append(ruleLine).append('\n');
            }
            return stringBuilder.toString();
        } catch (FileNotFoundException e){
            System.out.println("Something went wrong while retrieving the rules... please google them instead.");
        } catch (Exception e) {
            System.out.println("Oh no.. something went wrong :(\n" + e.getMessage());
        }
        return null;
    }
}
