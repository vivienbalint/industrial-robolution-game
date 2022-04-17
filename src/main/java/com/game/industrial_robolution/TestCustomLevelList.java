package com.game.industrial_robolution;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.LinkedHashMap;

import static org.junit.jupiter.api.Assertions.*;

public class TestCustomLevelList {
    private final CustomLevelList customLevelList = new CustomLevelList();
    private final Tile testStationTile = new Tile("station", true, "peru");
    private final Tile testFieldTile = new Tile("field", true, "lightGreen");
    private final Tile[][] testMatrix = new Tile[7][7];
    private final LinkedHashMap<String, Integer> testCommands = new LinkedHashMap<>();

    @BeforeEach
    public void setTestMatrix() {
        for (int i = 0; i < 7; i++) {
            for (int j = 0; j < 7; j++) {
                if ((i == 0 && j == 0) || (i == 4 && j == 4)) {
                    testMatrix[i][j] = testStationTile;
                } else testMatrix[i][j] = testFieldTile;
            }
        }
    }

    @Test
    @DisplayName("Testing addSaveCustomLevel() method with good values")
    public void testAddSaveCustomLevel() {
        testCommands.put("north", 5);
        testCommands.put("east", 7);
        testCommands.put("dynamite", 2);
        int[][] testLoopCount = new int[][]{{1, 2}, {2, 3}};

        CustomLevel testLevel = new CustomLevel(7, 7, 2, testMatrix, testCommands, testLoopCount);
        customLevelList.addSavedCustomLevel(testLevel);
        assertEquals(testCommands.get("east"), customLevelList.getSavedCustomLevels().get(0).getCommandCount().get("east"));
    }

    @Test
    @DisplayName("Testing addSaveCustomLevel() method with bad values")
    public void testAddSaveCustomLevel2() {
        testCommands.put("north", 5);
        testCommands.put("east", 7);
        testCommands.put("dynamite", 2);
        int[][] testLoopCount = new int[][]{{1, 2}, {2, 3}};

        CustomLevel testLevel = new CustomLevel(7, 7, 2, testMatrix, testCommands, testLoopCount);
        customLevelList.addSavedCustomLevel(testLevel);

        assertThrows(IndexOutOfBoundsException.class, () -> customLevelList.getSavedCustomLevels().get(-1000));
    }

}
