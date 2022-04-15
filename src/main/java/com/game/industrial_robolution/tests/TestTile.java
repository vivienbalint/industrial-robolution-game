package com.game.industrial_robolution.tests;

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

import com.game.industrial_robolution.Tile;

public class TestTile {
    private Tile testTile;

    @Test
    @DisplayName("Testing getters")
    public void testGetters() {
        testTile = new Tile("station", true, "peru");
        assertNotNull(testTile.getType());
        assertNotNull(testTile.getColor());
        assertTrue(testTile.getIsBuildable());
        assertEquals("station", testTile.getType());
        assertEquals("peru", testTile.getColor());
    }

    @Test
    @DisplayName("Testing constructors with bad values")
    public void testConstructors() {
        IllegalArgumentException err = assertThrows(IllegalArgumentException.class, () -> {
            testTile = new Tile("wood", true, "peru");
        });
        assertEquals("Type can only be water/rock/field/station", err.getMessage());
    }

    @Test
    @DisplayName("Testing constructors with bad values 2")
    public void testConstructors2() {
        IllegalArgumentException err = assertThrows(IllegalArgumentException.class, () -> {
            testTile = new Tile("station", true, "blue");
        });
        assertEquals("Color can only be darkCyan/grey/lightGreen/sienna/forestGreen", err.getMessage());
    }

    @Test
    @DisplayName("Testing setters with good values")
    public void testSetters() {
        testTile = new Tile("water", false, "darkCyan");
        testTile.setColor("forestGreen");
        assertEquals("forestGreen", testTile.getColor());
    }

    @Test
    @DisplayName("Testing setter with bad values")
    public void testSetters2() {
        testTile = new Tile("water", false, "darkCyan");
        IllegalArgumentException err = assertThrows(IllegalArgumentException.class, () -> {
            testTile.setColor("blue");
        });
        assertEquals("Color can only be darkCyan/grey/lightGreen/peru/sienna/forestGreen", err.getMessage());
    }

}
