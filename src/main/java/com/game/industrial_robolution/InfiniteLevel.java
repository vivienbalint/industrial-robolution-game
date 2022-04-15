package com.game.industrial_robolution;

import java.util.Random;

public class InfiniteLevel {

    private final String[] tileTypes = new String[]{"r", "w", "f", "s"};
    private String[] savedCol;

    public void setSavedCol(String[] savedCol) {
        this.savedCol = savedCol;
    }

    public String[] generateSavedCol(int row) {
        String[] col = new String[row];
        boolean isStation = false;
        int randomInt = 0;

        while (!isStation) {
            if (tileTypes[getRandomIndex(tileTypes)].equals("s")) {
                randomInt = new Random().nextInt(row);
                col[randomInt] = "s";
                isStation = true;
            }
        }

        for (int rowCount = 0; rowCount < row; rowCount++) {
            int randomIdx = getRandomIndex(tileTypes);
            if (rowCount == randomInt) {
                continue;
            }
            while (tileTypes[randomIdx].equals("s")) {
                randomIdx = getRandomIndex(tileTypes);
            }
            col[rowCount] = tileTypes[randomIdx];
        }
        return col;
    }


    public Tile[][] generateMatrix(int row, int col, int stationNumber) {
        int currentStationNumber = 2;
        Tile[][] matrix = new Tile[row][col];
        int idx1 = 0;
        int idx2 = 0;
        String[] rightCol = generateSavedCol(row);

        for (int rowCount = 0; rowCount < row; rowCount++) {
            for (int colCount = 0; colCount < col; colCount++) {
                String randomTileType = tileTypes[getRandomIndex(tileTypes)];
                if (colCount == 0) {
                    matrix[rowCount][colCount] = FixedLevels.getTile(savedCol[idx1]);
                    idx1++;
                } else if (colCount == col - 1) {
                    matrix[rowCount][colCount] = FixedLevels.getTile(rightCol[idx2]);
                    idx2++;
                } else if (randomTileType.equals("s") && currentStationNumber < stationNumber) {
                    matrix[rowCount][colCount] = FixedLevels.getTile(randomTileType);
                    currentStationNumber++;
                } else if (!randomTileType.equals("s")) {
                    matrix[rowCount][colCount] = FixedLevels.getTile(randomTileType);
                } else {
                    while (randomTileType.equals("s")) {
                        randomTileType = tileTypes[getRandomIndex(tileTypes)];
                    }
                    matrix[rowCount][colCount] = FixedLevels.getTile(randomTileType);
                }
            }
        }
        setSavedCol(rightCol);

        return matrix;
    }

    private int getRandomIndex(String[] array) {
        return new Random().nextInt(array.length);
    }
}
