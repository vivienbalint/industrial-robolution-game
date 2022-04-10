package com.game.industrial_robolution;

public class Tile {

    private String type;
    private boolean isBuildable;
    private String color;

    //****** Constructor ******

    public Tile(String type, boolean isBuildable, String color) {

        if (type.equals("water") || (type.equals("rock") || type.equals("field") || type.equals("station"))) {
            this.type = type;
        } else throw new IllegalArgumentException("Type can only be water/rock/field/station");

        if (color.equals("darkCyan") || color.equals("grey") || color.equals("lightGreen") || color.equals("peru") || color.equals("sienna") ||  color.equals("forestGreen")) {
            this.color = color;
        } else throw new IllegalArgumentException("Color can only be darkCyan/grey/lightGreen/sienna/forestGreen");

        this.isBuildable = isBuildable;

    }

    //******* Setters ******

    public void setType(String type) {
        if (type.equals("water") || (type.equals("rock") || type.equals("field"))) {
            this.type = type;
        } else throw new IllegalArgumentException("Type can only be water/rock/field");
    }

    public void setBuildable(boolean buildable) {
        isBuildable = buildable;
    }

    public void setColor(String color) {
        if (color.equals("darkCyan") || color.equals("grey") || color.equals("lightGreen") || color.equals("peru") || color.equals("sienna") || color.equals("forestGreen")) {
            this.color = color;
        } else throw new IllegalArgumentException("Color can only be darkCyan/grey/lightGreen/peru/sienna/forestGreen");
    }

    //****** Getters ******

    public String getType() {
        return type;
    }

    public boolean getIsBuildable() {
        return isBuildable;
    }

    public String getColor() {
        return color;
    }
}