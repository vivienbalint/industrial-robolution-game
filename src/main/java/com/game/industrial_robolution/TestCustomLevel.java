package com.game.industrial_robolution;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.LinkedHashMap;

import static org.junit.jupiter.api.Assertions.*;

public class TestCustomLevel {
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
    @DisplayName("Testing constructors and getters")
    public void testConstructorsAndGetters() {
        testCommands.put("north", 5);
        testCommands.put("east", 7);
        testCommands.put("dynamite", 2);
        int[][] testLoopCount = new int[][]{{1, 2}, {2, 3}};

        CustomLevel testLevel = new CustomLevel(7, 7, 2, testMatrix, testCommands, testLoopCount);

        assertEquals(7, testLevel.getRow());
        assertEquals(7, testLevel.getCol());
        assertEquals(2, testLevel.getStationNumber());
        assertEquals(testMatrix.length, testLevel.getMatrix().length);
        assertEquals(testMatrix[0].length, testLevel.getMatrix()[0].length);
        assertEquals(testCommands.get("north"), testLevel.getCommandCount().get("north"));
        assertEquals(testLoopCount[0][0], testLevel.getLoopCount()[0][0]);
    }
}
