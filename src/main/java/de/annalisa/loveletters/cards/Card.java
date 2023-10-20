package de.annalisa.loveletters.cards;

import de.annalisa.loveletters.Game;

public abstract class Card {
    private String name;
    private String effect;
    private int closeness;

    public Card(String name, int closeness, String effect){
        this.name = name;
        this.effect = effect;
        this.closeness = closeness;
    }

    //Getters
    public abstract void applyEffect(Game game);

    public String getEffect(){
        return effect;
    }

    public String getName() {
        return name;
    }

    public int getCloseness() {
        return closeness;
    }

    @Override
    public String toString(){
        return "[ " + name + " - " + closeness + " - " + effect + " ]";
    }
}
