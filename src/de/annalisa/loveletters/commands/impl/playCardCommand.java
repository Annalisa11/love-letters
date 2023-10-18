package de.annalisa.loveletters.commands.impl;

import de.annalisa.loveletters.Game;
import de.annalisa.loveletters.Player;
import de.annalisa.loveletters.commands.Command;

public class playCardCommand implements Command {
    @Override
    public String getCommand() {
        return "playCard";
    }

    @Override
    public String[] getUsages() {
        return new String[0];
    }

    @Override
    public String getDescription() {
        return null;
    }

    @Override
    public boolean execute(Game game) {
        Player player = game.currentPlayer;
        int chosenCard = game.chooseCardToPlay(player) - 1;
        game.applyEffect(player.getHand().get(chosenCard), player);
        player.removeCardFromHand(chosenCard);
        player.updateScore();
        return false;
    }
}