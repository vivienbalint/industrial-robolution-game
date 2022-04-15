package com.game.industrial_robolution.tests;

import com.game.industrial_robolution.InfiniteLevel;
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
}
