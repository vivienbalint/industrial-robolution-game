package com.ui.industrial_robolution;

import com.game.industrial_robolution.FixedLevels;
import com.game.industrial_robolution.Level;
import com.game.industrial_robolution.Robot;
import com.game.industrial_robolution.Tile;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;

import java.util.HashMap;
import java.util.LinkedHashMap;

public class FixedLevelsFX {

    private boolean dynamiteIsClicked = false;
    private boolean bridgeIsClicked = false;

    private GridPane levelPane;
    private Level currentLevel;
    private Tile[][] currentMatrix;
    private Label commandCountLabel;
    private LinkedHashMap<String, Integer> commandCount = new LinkedHashMap<String, Integer>() {{
        put("north", 0);
        put("east", 0);
        put("west", 0);
        put("south", 0);
        put("dynamite", 0);
        put("bridge", 0);
    }};

    private int[][] loopCount;

    public void drawLevel(String levelDifficulty) {

        //****** Layout ******

        levelPane = new GridPane();
        levelPane.setAlignment(Pos.CENTER);
        levelPane.setStyle("-fx-background-color: #343A40");

        //****** Get matrix ******

        switch (levelDifficulty) {
            case "novice" -> {
                currentLevel = FixedLevels.getNoviceLevel();

                commandCount.replace("north", 4);
                commandCount.replace("east", 2);
                commandCount.replace("west", 3);
                commandCount.replace("south", 4);
                commandCount.replace("dynamite", 2);

                loopCount = new int[][]{{1, 3},
                        {1, 4}};

            }
            case "adept" -> {
                currentLevel = FixedLevels.getAdeptLevel();

                commandCount.replace("east", 4);
                commandCount.replace("west", 2);
                commandCount.replace("south", 4);
                commandCount.replace("dynamite", 2);
                commandCount.replace("bridge", 1);

                loopCount = new int[][]{{1, 2},
                        {1, 3}};

            }
            case "expert" -> {
                currentLevel = FixedLevels.getExpertLevel();

                commandCount.replace("east", 4);
                commandCount.replace("west", 2);
                commandCount.replace("south", 4);
                commandCount.replace("dynamite", 2);
                commandCount.replace("bridge", 1);

                loopCount = new int[][]{{1, 2},
                        {1, 3}};

            }
            case "master" -> {
                currentLevel = FixedLevels.getMasterLevel();

                commandCount.replace("east", 4);
                commandCount.replace("west", 2);
                commandCount.replace("south", 4);
                commandCount.replace("dynamite", 2);
                commandCount.replace("bridge", 1);

                loopCount = new int[][]{{1, 2},
                        {1, 3}};

            }
            default -> throw new IllegalArgumentException("Level difficulty can only be novice/adept/expert/master.");
        }

        currentMatrix = currentLevel.getMatrix();


        //****** Draw matrix ******

        drawMatrix(currentMatrix);

        //****** Add commands ******

        drawCommands();

        Robot robot = new Robot(currentLevel);
        robot.goToStartingPos();

        colorMatrix(currentMatrix);
    }

    private void drawMatrix(Tile[][] matrix) {
        for (int rowCounter = 0; rowCounter < currentLevel.getRow(); rowCounter++) {
            for (int colCounter = 0; colCounter < currentLevel.getCol(); colCounter++) {
                Rectangle tile = new Rectangle(80, 80);
                tile.setArcWidth(20);
                tile.setArcHeight(20);
                tile.setStroke(Color.BLACK);
                levelPane.add(tile, colCounter + 4, rowCounter);
            }
        }
        colorMatrix(matrix);
    }

    public Rectangle getNodeByPos(int row, int col) {
        for (Node node : levelPane.getChildren()) {
            if (GridPane.getRowIndex(node) == row && GridPane.getColumnIndex(node) == col && node instanceof Rectangle) {
                return (Rectangle) node;
            }
        }
        return null;
    }

    public void colorMatrix(Tile[][] matrix) {
        for (int rowCounter = 0; rowCounter < currentLevel.getRow(); rowCounter++) {
            for (int colCounter = 0; colCounter < currentLevel.getCol(); colCounter++) {
                Rectangle node = getNodeByPos(rowCounter, colCounter + 4);
                String color = matrix[rowCounter][colCounter].getColor();
                node.setFill(Color.valueOf(color));
            }
        }
    }

    private void drawCommands() {
        CommandsFX commandsFX = new CommandsFX();
        int row = 0;
        for (String key : commandCount.keySet()) {
            commandCountLabel = new Label();
            commandCountLabel.setFont(Font.font("Agency FB", 18));
            commandCountLabel.setStyle("-fx-text-fill: #eeeaa9");
            commandCountLabel.setPadding(new Insets(0, 40, 0, 0));

            StackPane commandBtn = commandsFX.getCommandBtn(key, commandCount.get(key));

            levelPane.add(commandBtn, 0, row);
            commandCountLabel.setText(commandCount.get(key) + " x");
            levelPane.add(commandCountLabel, 1, row);
            row++;

            bindActionToCommandBtn(key, commandBtn);

        }
        row = 0;

        for (int arrayCount = 0; arrayCount < loopCount.length; arrayCount++) {
            commandCountLabel = new Label();
            commandCountLabel.setFont(Font.font("Agency FB", 18));
            commandCountLabel.setStyle("-fx-text-fill: #eeeaa9");
            commandCountLabel.setPadding(new Insets(0, 40, 0, 0));

            levelPane.add(commandsFX.getLoopBtn(loopCount[arrayCount][0], loopCount[arrayCount][1]), 2, row);
            commandCountLabel.setText(loopCount[arrayCount][0] + " x");
            levelPane.add(commandCountLabel, 3, row++);
        }
        levelPane.add(commandsFX.getCommandBtn("stop", 20), 2, row++);
    }

    private void bindActionToCommandBtn(String command, StackPane commandBtn) {

        Robot robot = new Robot(currentLevel);

        String[] directions = new String[]{"north", "east", "west", "south"};

        for (String direction : directions) {
            if (command.equals(direction)) {
                commandBtn.setOnMouseClicked(e -> {
                    if (!dynamiteIsClicked && !bridgeIsClicked) {
                        robot.go(direction);
                    }

                    if (dynamiteIsClicked) {
                        dynamiteIsClicked = false;
                        robot.throwDynamite(direction);

                    }
                    if (bridgeIsClicked) {
                        bridgeIsClicked = false;
                        robot.buildBridge(direction);
                    }
                    colorMatrix(currentMatrix);
                });
            }
            if (command.equals("dynamite")) {
                commandBtn.setOnMouseClicked(e -> {
                    dynamiteIsClicked = !dynamiteIsClicked ? true : false;
                });
            }
            if (command.equals("bridge")) {
                commandBtn.setOnMouseClicked(e -> {
                    bridgeIsClicked = !bridgeIsClicked ? true : false;
                });
            }
        }

    }

    public GridPane getLevelPane() {
        return levelPane;
    }
}
