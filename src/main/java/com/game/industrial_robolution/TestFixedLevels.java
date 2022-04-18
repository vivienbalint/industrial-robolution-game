package com.game.industrial_robolution;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class TestFixedLevels {
    private final Tile testTile = new Tile("station", true, "peru");
    private FixedLevels fixedLevels = new FixedLevels();

    @Test
    @DisplayName("Testing getTile method with good values")
    public void testGetTileMethod() {
        assertEquals(testTile.getType(), fixedLevels.getTile("s").getType());
        assertEquals(testTile.getColor(), fixedLevels.getTile("s").getColor());
        assertEquals(testTile.getIsBuildable(), fixedLevels.getTile("s").getIsBuildable());
    }

    @Test
    @DisplayName("Testing getTile method with bad values")
    public void testGetTileMethod2() {
        assertNotEquals(testTile.getType(), fixedLevels.getTile("w").getType());
        assertNotEquals(testTile.getColor(), fixedLevels.getTile("w").getColor());
    }

    @Test
    @DisplayName("Testing getTile method with bad values 2")
    public void testGetTileMethod3() {
        IllegalArgumentException err = assertThrows(IllegalArgumentException.class, () -> fixedLevels.getTile("k"));
        assertEquals("Type can only be r/w/f/s", err.getMessage());
    }
}
