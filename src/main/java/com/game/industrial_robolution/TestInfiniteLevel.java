package com.game.industrial_robolution;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class TestInfiniteLevel {
    private final InfiniteLevel testInfiniteLevel = new InfiniteLevel();

    @Test
    @DisplayName("Testing generateSavedCol() method with good values")
    public void testGenerateSavedColMethod() {
        String[] testArray = new String[6];
        assertEquals(testArray.length, testInfiniteLevel.generateSavedCol(6).length);
    }

    @Test
    @DisplayName("Testing generateSavedCol() method with bad values")
    public void testGenerateSavedCol2() {
        String[] testArray = new String[2];
        assertNotEquals(testArray.length, testInfiniteLevel.generateSavedCol(7).length);
    }

    @Test
    @DisplayName("Testing generateSavedCol() method with bad values")
    public void testGenerateSavedCol3() {
        IllegalArgumentException err = assertThrows(IllegalArgumentException.class, () -> testInfiniteLevel.generateSavedCol(-5));
        assertEquals("Row must be between 6 and 10.", err.getMessage());
    }

    @Test
    @DisplayName("Testing generateMatrix() method with good values")
    public void testGenerateMatrix() {
        String[] testCol = testInfiniteLevel.generateSavedCol(7);
        testInfiniteLevel.setSavedCol(testCol);
        assertEquals(testCol.length, testInfiniteLevel.generateMatrix(7, 7, 4).length);
    }

    @Test
    @DisplayName("Testing generateMatrix() method with good values")
    public void testGenerateMatrix2() {
        String[] testCol = testInfiniteLevel.generateSavedCol(7);
        testInfiniteLevel.setSavedCol(testCol);
        assertEquals(testCol.length, testInfiniteLevel.generateMatrix(7, 7, 4)[0].length);
    }

    @Test
    @DisplayName("Testing generateMatrix() method with good values")
    public void testGenerateMatrix3() {
        String[] testCol = testInfiniteLevel.generateSavedCol(7);
        testInfiniteLevel.setSavedCol(testCol);
        Tile[][] testGeneratedMatrix = testInfiniteLevel.generateMatrix(7, 7, 4);

        int testStationNumber = 0;

        for(int rowCount = 0; rowCount < 7; rowCount++) {
            for(int colCount = 0; colCount < 7; colCount++) {
                if(testGeneratedMatrix[rowCount][colCount].getType().equals("station")) {
                    testStationNumber++;
                }
            }
        }

        assertEquals(4, testStationNumber);
    }

}
