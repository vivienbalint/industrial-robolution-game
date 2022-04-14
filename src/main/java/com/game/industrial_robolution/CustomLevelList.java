package com.game.industrial_robolution;

import java.util.ArrayList;

public class CustomLevelList {
    private static ArrayList<CustomLevel> savedCustomLevels = new ArrayList<>();

    public ArrayList<CustomLevel> getSavedCustomLevels() {
        return savedCustomLevels;
    }

    public void addSavedCustomLevel(CustomLevel level) {
        savedCustomLevels.add(level);
    }

    public void printLevel() {
        if(!savedCustomLevels.isEmpty()) {
        CustomLevel level = savedCustomLevels.get(0);
        System.out.println("level row = " + level.getRow());}
        else System.out.println("miért ürressss");

    }

}
