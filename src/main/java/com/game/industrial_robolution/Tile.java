package com.game.industrial_robolution;

public class Tile {

    String type;
    boolean isBuildable;
    String color;

    //****** Constructor ******

    public Tile(String type, boolean isBuildable, String color) {

        if (type.equals("water") || (type.equals("rock") || type.equals("field") || type.equals("station"))) {
            this.type = type;
        } else throw new IllegalArgumentException("Field can only be water/rock/field type");

        if (color.equals("lightBlue") || color.equals("grey") || color.equals("lightGreen") || color.equals("peru") || color.equals("crimson") || color.equals("green")) {
            this.color = color;
        } else throw new IllegalArgumentException("Color can only be lightBlue/grey/lightGreen/crimson/green");

        this.isBuildable = isBuildable;

    }

    //******* Setters ******

    public void setType(String type) {
        if (type.equals("water") || (type.equals("rock") || type.equals("field"))) {
            this.type = type;
        } else throw new IllegalArgumentException("Field can only be water/rock/field type");
    }

    public void setBuildable(boolean buildable) {
        isBuildable = buildable;
    }

    public void setColor(String color) {
        if (color.equals("lightBlue") || color.equals("grey") || color.equals("lightGreen") || color.equals("crimson") || color.equals("green")) {
            this.color = color;
        } else throw new IllegalArgumentException("Color can only be lightBlue/grey/lightGreen/crimson/green");
    }

    //****** Getters ******

    public String getType() {
        return type;
    }

    public boolean isBuildable() {
        return isBuildable;
    }

    public String getColor() {
        return color;
    }
}