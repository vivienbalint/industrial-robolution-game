package com.game.industrial_robolution;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class TestFixedLevels {
    private final Tile testTile = new Tile("station", true, "peru");

    @Test
    @DisplayName("Testing getTile method with good values")
    public void testGetTileMethod() {
        assertEquals(testTile.getType(), FixedLevels.getTile("s").getType());
        assertEquals(testTile.getColor(), FixedLevels.getTile("s").getColor());
        assertEquals(testTile.getIsBuildable(), FixedLevels.getTile("s").getIsBuildable());
    }

    @Test
    @DisplayName("Testing getTile method with bad values")
    public void testGetTileMethod2() {
        assertNotEquals(testTile.getType(), FixedLevels.getTile("w").getType());
        assertNotEquals(testTile.getColor(), FixedLevels.getTile("w").getColor());
    }

    @Test
    @DisplayName("Testing getTile method with bad values 2")
    public void testGetTileMethod3() {
        IllegalArgumentException err = assertThrows(IllegalArgumentException.class, () -> FixedLevels.getTile("k"));
        assertEquals("Type can only be r/w/f/s", err.getMessage());
    }
}
