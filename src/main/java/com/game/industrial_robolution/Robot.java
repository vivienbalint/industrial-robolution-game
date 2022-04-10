package com.game.industrial_robolution;

import com.ui.industrial_robolution.FixedLevelsFX;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Robot implements IRobot {

    Level level;
    private Tile[][] levelMatrix;
    private int matrixRow;
    private int matrixCol;
    private String msg = "You can't go that way :)";

    public Robot(Level level) {
        this.level = level;
    }

    public Level getLevel() {
        return level;
    }

    @Override
    public void goToStartingPos() {

        levelMatrix = level.getMatrix();
        matrixRow = level.getRow();
        matrixCol = level.getCol();
        int[] pos = {0, 0};

      outer:  for (int row = 0; row < matrixRow; row++) {
            for (int col = 0; col < matrixCol; col++) {
                if (levelMatrix[row][col].getType().equals("station")) {
                    pos[0] = row;
                    pos[1] = col;
                    levelMatrix[row][col].setColor("sienna");
                    level.setStationNumberBuiltOn(1);
                    level.setPos(pos);
                    break outer;
                }
            }
        }
    }

    @Override
    public void go(String direction) {

        levelMatrix = level.getMatrix();
        matrixRow = level.getRow();
        matrixCol = level.getCol();
        int[] pos = level.getPos();
        int row = pos[0];
        int col = pos[1];

        switch (direction) {
            case "north" -> {
                if (row - 1 >= 0) {
                    if (levelMatrix[row - 1][col].getIsBuildable() && !levelMatrix[row - 1][col].getColor().equals("forestGreen")) {
                        pos[0] = row - 1;
                        level.setPos(pos);
                        if (levelMatrix[row - 1][col].getType().equals("station")) {
                            levelMatrix[row - 1][col].setColor("sienna");
                            incrementStationNumber();
                            if (level.getStationNumberBuiltOn() == level.getStationNumber()) {
                                level.setIsWon(true);
                                //TODO
                            }
                        } else levelMatrix[row - 1][col].setColor("forestGreen");
                    } else if(levelMatrix[row - 1][col].getType().equals("water")) {
                        setOriginalColor();
                        goToStartingPos();
                    } else {
                        System.out.println(msg);
                    }
                } else {
                    System.out.println(msg);
                }
            }
            case "east" -> {
                if (col + 1 < matrixCol) {
                    if (levelMatrix[row][col + 1].getIsBuildable() && !levelMatrix[row][col + 1].getColor().equals("forestGreen")) {
                        pos[1] = col + 1;
                        level.setPos(pos);
                        if (levelMatrix[row][col + 1].getType().equals("station")) {
                            incrementStationNumber();
                            levelMatrix[row][col + 1].setColor("sienna");
                            if (level.getStationNumberBuiltOn() == level.getStationNumber()) {
                                level.setIsWon(true);
                                //TODO
                            }
                        } else levelMatrix[row][col + 1].setColor("forestGreen");
                    }  else if(levelMatrix[row][col + 1].getType().equals("water")) {
                        setOriginalColor();
                        goToStartingPos();
                } else {
                        System.out.println(msg);
                }
                } else {
                    System.out.println(msg);
                }
            }
            case "west" -> {
                if (col - 1 >= 0) {
                    if (levelMatrix[row][col - 1].getIsBuildable() && !levelMatrix[row][col - 1].getColor().equals("forestGreen")) {
                        pos[1] = col - 1;
                        level.setPos(pos);
                        if (levelMatrix[row][col - 1].getType().equals("station")) {
                            incrementStationNumber();
                            levelMatrix[row][col - 1].setColor("sienna");
                            if (level.getStationNumberBuiltOn() == level.getStationNumber()) {
                                level.setIsWon(true);
                                //TODO
                            }
                        } else levelMatrix[row][col - 1].setColor("forestGreen");
                    } else if(levelMatrix[row][col - 1].getType().equals("water")) {
                        setOriginalColor();
                        goToStartingPos();
                    } else {
                        System.out.println(msg);
                    }
                } else {
                    System.out.println(msg);
                }
            }
            case "south" -> {
                if (row + 1 < level.getRow()) {
                    if (levelMatrix[row + 1][col].getIsBuildable() && !levelMatrix[row + 1][col].getColor().equals("forestGreen")) {
                        pos[0] = row + 1;
                        level.setPos(pos);
                        if (levelMatrix[row + 1][col].getType().equals("station")) {
                            incrementStationNumber();
                            levelMatrix[row + 1][col].setColor("sienna");
                            if (level.getStationNumberBuiltOn() == level.getStationNumber()) {
                                level.setIsWon(true);
                                //TODO
                            }
                        } else levelMatrix[row + 1][col].setColor("forestGreen");
                    } else if(levelMatrix[row + 1][col].getType().equals("water")) {
                         setOriginalColor();
                         goToStartingPos();
                    } else {
                        System.out.println(msg);
                    }
                } else {
                    System.out.println(msg);
                }
            }
            default -> throw new IllegalArgumentException("Direction must be north/east/west/south");
        }
    }

    @Override
    public void throwDynamite(String direction) {
        setToBuildable("rock", direction);
    }

    @Override
    public void buildBridge(String direction) {
        setToBuildable("water", direction);
    }

    private void incrementStationNumber() {
        level.setStationNumberBuiltOn(level.getStationNumberBuiltOn() + 1);
    }

    private void setOriginalColor() {
        levelMatrix = level.getMatrix();
        matrixRow = level.getRow();
        matrixCol = level.getCol();

        for (int row = 0; row < matrixRow; row++) {
            for (int col = 0; col < matrixCol; col++) {
                if (levelMatrix[row][col].getType().equals("station")) {
                    levelMatrix[row][col].setColor("peru");
                }
                if (levelMatrix[row][col].getType().equals("rock")) {
                    levelMatrix[row][col].setColor("grey");
                }
                if (levelMatrix[row][col].getType().equals("water")) {
                    levelMatrix[row][col].setColor("darkCyan");
                }
                if (levelMatrix[row][col].getType().equals("field")) {
                    levelMatrix[row][col].setColor("lightGreen");
                }
            }
        }
    }

  /*  private void changeColor(int row, int col) {


        levelMatrix = level.getMatrix();

        String originalColor = levelMatrix[row][col].getColor();
        System.out.println("original color = " + originalColor);
        levelMatrix[row][col].setColor("crimson");
        System.out.println("crimson = " + levelMatrix[row][col].getColor());

        try {
            Thread.sleep(2000);
            levelMatrix[row][col].setColor(originalColor);
            System.out.println("delayed color = " + levelMatrix[row][col].getColor());
        } catch (InterruptedException ie) {
            Thread.currentThread().interrupt();
        }
    } */

    private void setToBuildable(String type, String direction) {

        levelMatrix = level.getMatrix();
        matrixRow = level.getRow();
        matrixCol = level.getCol();
        int[] pos = level.getPos();

        int row = pos[0];
        int col = pos[1];

        switch(direction) {
            case("north") -> {
                if (row - 1 >= 0) {
                    if (levelMatrix[row - 1][col].getType().equals(type)) {
                        levelMatrix[row - 1][col].setBuildable(true);
                        go(direction);
                    } else {
                        System.out.println(msg);
                    }
                } else {
                    System.out.println(msg);
                }
            }
            case "east" -> {
                if (col + 1 < matrixCol) {
                    if (levelMatrix[row][col + 1].getType().equals(type)) {
                        levelMatrix[row][col + 1].setBuildable(true);
                        go(direction);
                    } else {
                        System.out.println(msg);
                    }
                } else {
                    System.out.println(msg);
                }
            }
            case "west" -> {
                if (col - 1 >= 0) {
                    if (levelMatrix[row][col - 1].getType().equals(type)) {
                        levelMatrix[row][col - 1].setBuildable(true);
                        go(direction);
                    } else {
                        System.out.println(msg);
                    }
                } else {
                    System.out.println(msg);
                }
            }
            case "south" -> {
                if (row + 1 < level.getRow()) {
                    if (levelMatrix[row + 1][col].getType().equals(type)) {
                        levelMatrix[row + 1][col].setBuildable(true);
                        go(direction);
                    } else {
                        System.out.println(msg);
                    }
                } else {
                    System.out.println(msg);
                }
            }
            default -> throw new IllegalArgumentException("Direction must be north/east/west/south");
        }
    }

}
