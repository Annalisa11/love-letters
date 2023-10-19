package de.annalisa.loveletters;

public class Card {
    private String name;
    private String effect;
    private int closeness;

    public Card(String name, int closeness, String effect){
        this.name = name;
        this.effect = effect;
        this.closeness = closeness;
    }

    //Getters
    public String getEffect() {
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
