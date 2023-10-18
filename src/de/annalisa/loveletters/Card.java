package de.annalisa.loveletters;

public class Card {
    private String name;
    private int closeness;
    private String effect;

    public Card(String name, int closeness, String effect){
        this.name = name;
        this.closeness = closeness;
        this.effect = effect;
    }

    public String getEffect() {
        return effect;
    }

    public int getCloseness() {
        return closeness;
    }

    @Override
    public String toString(){
        return "[ " + name + " - " + closeness + " - " + effect + " ]";
    }
}
