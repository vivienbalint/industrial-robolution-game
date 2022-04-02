package com.game.industrial_robolution;

public class Level {

    int row;
    int col;
    int stationNumber;
    Tile[][] matrix;

    //****** Constructor ******

    public Level(int row, int col, int stationNumber, Tile[][] matrix) {
        if (row > 5 && row < 9 && col > 5 && col < 9 && stationNumber > 1 && stationNumber < 6 && matrix.length == row && matrix[0].length == col) {
            this.row = row;
            this.col = col;
            this.stationNumber = stationNumber;
            this.matrix = matrix;
        } else
            throw new IllegalArgumentException("Row and Col must be between 6 and 8, stationNumber must be between 2 and 5");
    }

 /*   //****** Setters ******

    public void setRow(int row) {
        if(row > 5 && row < 9) {
            this.row = row;
        } else
            throw new IllegalArgumentException("Row must be between 6 and 8");
    }

    public void setCol(int col) {
        if(col > 5 && col < 9) {
            this.col = col;
        } else
            throw new IllegalArgumentException("Col must be between 6 and 8");
    }

    public void setStationNumber(int stationNumber) {
        if(stationNumber > 1 && stationNumber < 6) {
            this.stationNumber = stationNumber;
        } else
            throw new IllegalArgumentException("StationNumber must be between 2 and 5");
    } */

    //****** Getters ******

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }

    public int getStationNumber() {
        return stationNumber;
    }

    public Tile[][] getMatrix() {
        return matrix;
    }
}