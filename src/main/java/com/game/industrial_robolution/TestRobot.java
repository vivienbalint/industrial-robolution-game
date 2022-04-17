package com.game.industrial_robolution;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class TestRobot {
    private Level testLevel;
    private Robot testRobot;
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
    @DisplayName("Testing goToStartingPos() method with good values")
    public void testGoToStartingPos() {
        testLevel = new Level(7, 7, 2, testMatrix);
        testRobot = new Robot(testLevel);
        int[] testPos = new int[]{0, 0};
        testRobot.goToStartingPos();
        assertEquals(testPos[0], testLevel.getPos()[0]);
        assertEquals(testPos[1], testLevel.getPos()[1]);
    }

    @Test
    @DisplayName("Testing goToStartingPos() method with good values")
    public void testGoToStartingPos2() {
        testLevel = new Level(7, 7, 2, testMatrix);
        testRobot = new Robot(testLevel);
        int[] testPos = new int[]{0, 0};
        testLevel.setInfinite(true);
        testRobot.goToStartingPos();
        assertEquals(testPos[0], testLevel.getPos()[0]);
        assertEquals(testPos[1], testLevel.getPos()[1]);
    }

    @Test
    @DisplayName("Testing goToStartingPos() method with bad values")
    public void testGoToStartingPos3() {
        testLevel = new Level(7, 7, 2, testMatrix);
        testRobot = new Robot(testLevel);
        int[] testPos = new int[]{4, 4};
        testRobot.goToStartingPos();
        assertNotEquals(testPos[0], testLevel.getPos()[0]);
        assertNotEquals(testPos[1], testLevel.getPos()[1]);
    }

    @Test
    @DisplayName("Testing goToStartingPos() method with bad values")
    public void testGoToStartingPos4() {
        testLevel = new Level(7, 7, 2, testMatrix);
        testRobot = new Robot(testLevel);
        int[] testPos = new int[]{1, 0};
        testLevel.setInfinite(true);
        testRobot.goToStartingPos();
        assertNotEquals(testPos[0], testLevel.getPos()[0]);
    }

    @Test
    @DisplayName("Testing go() method with good values")
    public void testGo() {
        testLevel = new Level(7, 7, 2, testMatrix);
        testRobot = new Robot(testLevel);
        int[] testPos = new int[]{0, 0};
        testLevel.setPos(testPos);
        boolean testIsDoable = testRobot.go("east");
        assertTrue(testIsDoable);
    }

    @Test
    @DisplayName("Testing go() method with good values")
    public void testGo2() {
        testLevel = new Level(7, 7, 2, testMatrix);
        testRobot = new Robot(testLevel);
        int[] testPos = new int[]{0, 0};
        testLevel.setPos(testPos);
        boolean testIsDoable = testRobot.go("south");
        assertTrue(testIsDoable);
    }

    @Test
    @DisplayName("Testing go() method with good values")
    public void testGo3() {
        testLevel = new Level(7, 7, 2, testMatrix);
        testRobot = new Robot(testLevel);
        int[] testPos = new int[]{0, 0};
        testLevel.setPos(testPos);
        boolean testIsDoable = testRobot.go("west");
        assertFalse(testIsDoable);
    }

    @Test
    @DisplayName("Testing go() method with good values")
    public void testGo4() {
        testLevel = new Level(7, 7, 2, testMatrix);
        testRobot = new Robot(testLevel);
        int[] testPos = new int[]{0, 0};
        testLevel.setPos(testPos);
        boolean testIsDoable = testRobot.go("north");
        assertFalse(testIsDoable);
    }

    @Test
    @DisplayName("Testing go() method with good values")
    public void testGo5() {
        testLevel = new Level(7, 7, 2, testMatrix);
        testRobot = new Robot(testLevel);
        int[] testPos = new int[]{0, 0};
        testLevel.setPos(testPos);
        testRobot.go("east");
        String testColor = testLevel.getMatrix()[testLevel.getPos()[0]][testLevel.getPos()[1]].getColor();
        assertEquals("forestGreen", testColor);
    }

    @Test
    @DisplayName("Testing go() method with good values")
    public void testGo6() {
        testLevel = new Level(7, 7, 2, testMatrix);
        testRobot = new Robot(testLevel);
        int[] testPos = new int[]{0, 0};
        testLevel.setPos(testPos);
        testRobot.go("south");
        String testColor = testLevel.getMatrix()[testLevel.getPos()[0]][testLevel.getPos()[1]].getColor();
        assertEquals("forestGreen", testColor);
    }

    @Test
    @DisplayName("Testing go() method with good values")
    public void testGo7() {
        testLevel = new Level(7, 7, 2, testMatrix);
        testRobot = new Robot(testLevel);
        int[] testPos = new int[]{1, 1};
        testLevel.setPos(testPos);
        testRobot.go("north");
        String testColor = testLevel.getMatrix()[testLevel.getPos()[0]][testLevel.getPos()[1]].getColor();
        assertEquals("forestGreen", testColor);
    }

    @Test
    @DisplayName("Testing go() method with good values")
    public void testGo8() {
        testLevel = new Level(7, 7, 2, testMatrix);
        testRobot = new Robot(testLevel);
        int[] testPos = new int[]{1, 1};
        testLevel.setPos(testPos);
        testRobot.go("west");
        String testColor = testLevel.getMatrix()[testLevel.getPos()[0]][testLevel.getPos()[1]].getColor();
        assertEquals("forestGreen", testColor);
    }

    @Test
    @DisplayName("Testing go() method with good values")
    public void testGo9() {
        testLevel = new Level(7, 7, 2, testMatrix);
        testRobot = new Robot(testLevel);
        int[] testPos = new int[]{5, 4};
        testLevel.setPos(testPos);
        testRobot.go("north");
        String testColor = testLevel.getMatrix()[testLevel.getPos()[0]][testLevel.getPos()[1]].getColor();
        assertEquals("sienna", testColor);
    }

    @Test
    @DisplayName("Testing go() method with good values")
    public void testGo10() {
        testLevel = new Level(7, 7, 2, testMatrix);
        testRobot = new Robot(testLevel);
        int[] testPos = new int[]{5, 4};
        testLevel.setPos(testPos);
        testRobot.go("north");
        assertEquals(1, testLevel.getStationNumberBuiltOn());
    }

    @Test
    @DisplayName("Testing go() method with good values")
    public void testGo11() {
        testMatrix[4][4] = new Tile("water", false, "darkCyan");
        testLevel = new Level(7, 7, 2, testMatrix);
        testRobot = new Robot(testLevel);
        int[] testPos = new int[]{5, 4};
        int[] testStartingPos = new int[]{0, 0};
        testLevel.setPos(testPos);
        testRobot.go("north");
        assertEquals(testStartingPos[0], testLevel.getPos()[0]);
        assertEquals(testStartingPos[1], testLevel.getPos()[1]);
    }

    @Test
    @DisplayName("Testing go() method with good values")
    public void testGo12() {
        testLevel = new Level(7, 7, 2, testMatrix);
        testRobot = new Robot(testLevel);
        int[] testPos = new int[]{0, 0};
        testLevel.setPos(testPos);
        boolean testIsDoable = testRobot.go("north");
        assertFalse(testIsDoable);
    }

    @Test
    @DisplayName("Testing go() method with good values")
    public void testGo13() {
        testLevel = new Level(7, 7, 2, testMatrix);
        testRobot = new Robot(testLevel);
        int[] testPos = new int[]{4, 5};
        testLevel.setPos(testPos);
        testLevel.setStationNumberBuiltOn(1);
        testRobot.go("west");
        assertTrue(testLevel.getIsWon());
    }

    @Test
    @DisplayName("Testing go() method with good values")
    public void testGo14() {
        testLevel = new Level(7, 7, 2, testMatrix);
        testRobot = new Robot(testLevel);
        int[] testPos = new int[]{5, 4};
        testLevel.setPos(testPos);
        testLevel.setStationNumberBuiltOn(1);
        testRobot.go("north");
        assertTrue(testLevel.getIsWon());
    }

    @Test
    @DisplayName("Testing go() method with good values")
    public void testGo15() {
        testLevel = new Level(7, 7, 2, testMatrix);
        testRobot = new Robot(testLevel);
        int[] testPos = new int[]{4, 3};
        testLevel.setPos(testPos);
        testLevel.setStationNumberBuiltOn(1);
        testRobot.go("east");
        assertTrue(testLevel.getIsWon());
    }

    @Test
    @DisplayName("Testing go() method with good values")
    public void testGo16() {
        testLevel = new Level(7, 7, 2, testMatrix);
        testRobot = new Robot(testLevel);
        int[] testPos = new int[]{3, 4};
        testLevel.setPos(testPos);
        testLevel.setStationNumberBuiltOn(1);
        testRobot.go("south");
        assertTrue(testLevel.getIsWon());
    }

    @Test
    @DisplayName("Testing go() method with good values")
    public void testGo17() {
        testMatrix[0][1] = new Tile("water", false, "darkCyan");
        testLevel = new Level(7, 7, 2, testMatrix);
        testRobot = new Robot(testLevel);
        int[] testPos = new int[]{0, 0};
        testLevel.setPos(testPos);
        testLevel.setInfinite(true);
        testRobot.go("east");
        assertTrue(testRobot.isGoToMenu());
    }

    @Test
    @DisplayName("Testing go() method with good values")
    public void testGo18() {
        testMatrix[0][1] = new Tile("water", false, "darkCyan");
        testLevel = new Level(7, 7, 2, testMatrix);
        testRobot = new Robot(testLevel);
        int[] testPos = new int[]{1, 1};
        testLevel.setPos(testPos);
        testLevel.setInfinite(true);
        testRobot.go("north");
        assertTrue(testRobot.isGoToMenu());
    }

    @Test
    @DisplayName("Testing go() method with good values")
    public void testGo19() {
        testMatrix[1][0] = new Tile("water", false, "darkCyan");
        testLevel = new Level(7, 7, 2, testMatrix);
        testRobot = new Robot(testLevel);
        int[] testPos = new int[]{1, 1};
        testLevel.setPos(testPos);
        testLevel.setInfinite(true);
        testRobot.go("west");
        assertTrue(testRobot.isGoToMenu());
    }

    @Test
    @DisplayName("Testing go() method with good values")
    public void testGo20() {
        testMatrix[2][1] = new Tile("water", false, "darkCyan");
        testLevel = new Level(7, 7, 2, testMatrix);
        testRobot = new Robot(testLevel);
        int[] testPos = new int[]{1, 1};
        testLevel.setPos(testPos);
        testLevel.setInfinite(true);
        testRobot.go("south");
        assertTrue(testRobot.isGoToMenu());
    }

    @Test
    @DisplayName("Testing go() method with bad values")
    public void testGo21() {
        testLevel = new Level(7, 7, 2, testMatrix);
        testRobot = new Robot(testLevel);
        int[] testPos = new int[]{0, 0};
        testLevel.setPos(testPos);
        IllegalArgumentException err = assertThrows(IllegalArgumentException.class, () -> testRobot.go("straight"));
        assertEquals("Direction must be north/east/west/south", err.getMessage());
    }

    @Test
    @DisplayName("Testing throwDynamite() method with good values")
    public void testThrowDynamite() {
        testMatrix[2][2] = new Tile("rock", false, "grey");
        testLevel = new Level(7, 7, 2, testMatrix);
        testRobot = new Robot(testLevel);

        int[] testPos = new int[]{2, 1};
        testLevel.setPos(testPos);
        testRobot.throwDynamite("east");
        assertTrue(testMatrix[testLevel.getPos()[0]][testLevel.getPos()[1]].getIsBuildable());
    }

    @Test
    @DisplayName("Testing throwDynamite() method with good values")
    public void testThrowDynamite2() {
        testMatrix[2][2] = new Tile("rock", false, "grey");
        testLevel = new Level(7, 7, 2, testMatrix);
        testRobot = new Robot(testLevel);

        int[] testPos = new int[]{3, 2};
        testLevel.setPos(testPos);
        testRobot.throwDynamite("north");
        assertTrue(testMatrix[testLevel.getPos()[0]][testLevel.getPos()[1]].getIsBuildable());
    }

    @Test
    @DisplayName("Testing throwDynamite() method with good values")
    public void testThrowDynamite3() {
        testMatrix[2][2] = new Tile("rock", false, "grey");
        testLevel = new Level(7, 7, 2, testMatrix);
        testRobot = new Robot(testLevel);

        int[] testPos = new int[]{2, 3};
        testLevel.setPos(testPos);
        testRobot.throwDynamite("west");
        assertTrue(testMatrix[testLevel.getPos()[0]][testLevel.getPos()[1]].getIsBuildable());
    }

    @Test
    @DisplayName("Testing throwDynamite() method with good values")
    public void testThrowDynamite4() {
        testMatrix[2][2] = new Tile("rock", false, "grey");
        testLevel = new Level(7, 7, 2, testMatrix);
        testRobot = new Robot(testLevel);

        int[] testPos = new int[]{1, 2};
        testLevel.setPos(testPos);
        testRobot.throwDynamite("south");
        assertTrue(testMatrix[testLevel.getPos()[0]][testLevel.getPos()[1]].getIsBuildable());
    }

    @Test
    @DisplayName("Testing throwDynamite() method with bad values")
    public void testThrowDynamite5() {
        testMatrix[2][2] = new Tile("rock", false, "grey");
        testLevel = new Level(7, 7, 2, testMatrix);
        testRobot = new Robot(testLevel);

        int[] testPos = new int[]{1, 2};
        testLevel.setPos(testPos);

        IllegalArgumentException err = assertThrows(IllegalArgumentException.class, () -> testRobot.throwDynamite("straight"));
        assertEquals("Direction must be north/east/west/south", err.getMessage());
    }

    @Test
    @DisplayName("Testing buildBridge() method with good values")
    public void testBuildBridge() {
        testMatrix[2][2] = new Tile("water", false, "darkCyan");
        testLevel = new Level(7, 7, 2, testMatrix);
        testRobot = new Robot(testLevel);

        int[] testPos = new int[]{2, 1};
        testLevel.setPos(testPos);
        testRobot.buildBridge("east");
        assertTrue(testMatrix[testLevel.getPos()[0]][testLevel.getPos()[1]].getIsBuildable());
    }

    @Test
    @DisplayName("Testing buildBridge() method with good values")
    public void testBuildBridge2() {
        testMatrix[2][2] = new Tile("water", false, "darkCyan");
        testLevel = new Level(7, 7, 2, testMatrix);
        testRobot = new Robot(testLevel);

        int[] testPos = new int[]{3, 2};
        testLevel.setPos(testPos);
        testRobot.buildBridge("north");
        assertTrue(testMatrix[testLevel.getPos()[0]][testLevel.getPos()[1]].getIsBuildable());
    }

    @Test
    @DisplayName("Testing buildBridge() method with good values")
    public void testBuildBridge3() {
        testMatrix[2][2] = new Tile("water", false, "darkCyan");
        testLevel = new Level(7, 7, 2, testMatrix);
        testRobot = new Robot(testLevel);

        int[] testPos = new int[]{2, 3};
        testLevel.setPos(testPos);
        testRobot.buildBridge("west");
        assertTrue(testMatrix[testLevel.getPos()[0]][testLevel.getPos()[1]].getIsBuildable());
    }

    @Test
    @DisplayName("Testing buildBridge() method with good values")
    public void testBuildBridge4() {
        testMatrix[2][2] = new Tile("water", false, "darkCyan");
        testLevel = new Level(7, 7, 2, testMatrix);
        testRobot = new Robot(testLevel);

        int[] testPos = new int[]{1, 2};
        testLevel.setPos(testPos);
        testRobot.buildBridge("south");
        assertTrue(testMatrix[testLevel.getPos()[0]][testLevel.getPos()[1]].getIsBuildable());
    }

    @Test
    @DisplayName("Testing buildBridge() method with bad values")
    public void testBuildBridge5() {
        testMatrix[2][2] = new Tile("water", false, "darkCyan");
        testLevel = new Level(7, 7, 2, testMatrix);
        testRobot = new Robot(testLevel);

        int[] testPos = new int[]{2, 1};
        testLevel.setPos(testPos);
        IllegalArgumentException err = assertThrows(IllegalArgumentException.class, () -> testRobot.buildBridge("straight"));
        assertEquals("Direction must be north/east/west/south", err.getMessage());
    }


}
