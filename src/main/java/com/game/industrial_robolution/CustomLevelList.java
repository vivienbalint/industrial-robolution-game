package com.game.industrial_robolution;

import java.util.ArrayList;

public class CustomLevelList {
    private static final ArrayList<CustomLevel> savedCustomLevels = new ArrayList<>();

    public ArrayList<CustomLevel> getSavedCustomLevels() {
        return savedCustomLevels;
    }

    public void addSavedCustomLevel(CustomLevel level) {
        savedCustomLevels.add(level);
    }

}
