package com.game.industrial_robolution;

import java.util.LinkedHashMap;

public class CustomLevel extends Level {
    private final LinkedHashMap<String, Integer> commandCount;
    private final int[][] loopCount;

    public CustomLevel(int row, int col, int stationNumber, Tile[][] matrix, LinkedHashMap<String, Integer> commandCount, int[][] loopCount) {
        super(row, col, stationNumber, matrix);
        this.commandCount = commandCount;
        this.loopCount = loopCount;
    }

    public LinkedHashMap<String, Integer> getCommandCount() {
        return commandCount;
    }

    public int[][] getLoopCount() {
        return loopCount;
    }

}
