package de.annalisa.loveletters.commands.impl;

import de.annalisa.loveletters.Game;
import de.annalisa.loveletters.Player;
import de.annalisa.loveletters.cards.Card;
import de.annalisa.loveletters.commands.Command;

public class showHandCommand implements Command {
    @Override
    public String getCommand() {
        return "showHand";
    }

    @Override
    public String[] getUsages() {
        return new String[0];
    }

    @Override
    public String getDescription() {
        return "With showHand you can see which cards you have on your hand without having to do play one or do anything else. (In-game only)";
    }

    @Override
    public boolean execute(Game game, boolean inGame) {
        if(!inGame){
            System.out.println("❗ you have to start the game to view your cards");
            return true;
        }
        Player player = game.getCurrentPlayer();
        System.out.println("You have these cards on your hand:");
        System.out.println(Card.printCardsBesideEachOther(player.getHand()));
        return true;
    }
}
