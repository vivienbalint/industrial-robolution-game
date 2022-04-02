package com.game.industrial_robolution;

public class Robot implements IRobot {
    @Override
    public int[] goToStartingPos(Level level) {
        int[] coordinates = new int[2];
        Tile[][] levelMatrix = level.getMatrix();
        int matrixRow = level.getRow();
        int matrixCol = level.getCol();

        for (int row = 0; row < matrixRow; row++) {
            for (int col = 0; col < matrixCol; col++) {
                if (levelMatrix[row][col].getType().equals("station")) {
                    coordinates[0] = row;
                    coordinates[1] = col;
                    break;
                }
            }
        }
        return coordinates;
    }

    @Override
    public int[] go(int[] startingPos) {
        return new int[]{1, 2};
    }

    @Override
    public void build() {

    }

    @Override
    public void throwDynamite() {

    }

    @Override
    public void startOver() {

    }
}
