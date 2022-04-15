package com.game.industrial_robolution.tests;

import com.game.industrial_robolution.Level;
import com.game.industrial_robolution.Tile;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class TestLevel {
    private Level testLevel;
    private final Tile testStationTile = new Tile("station", true, "peru");
    private final Tile testFieldTile = new Tile("field", true, "lightGreen");
    private final Tile[][] testMatrix = new Tile[7][7];

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
    @DisplayName("Testing constructors and getters with good values")
    public void testConstructorsAndGetters() {
        testLevel = new Level(7, 7, 3, testMatrix);
        assertEquals(7, testLevel.getRow());
        assertEquals(7, testLevel.getCol());
        assertEquals(3, testLevel.getStationNumber());
        assertNotNull(testLevel.getMatrix());
        assertEquals(testMatrix, testLevel.getMatrix());
    }

    @Test
    @DisplayName("Testing constructors and getters with bad values")
    public void testConstructorsAndGetters2() {
        IllegalArgumentException err = assertThrows(IllegalArgumentException.class, () -> testLevel = new Level(4, 7, 3, testMatrix));
        assertEquals("Row and Col must be between 6 and 10, stationNumber must be between 2 and 5", err.getMessage());
    }
}
