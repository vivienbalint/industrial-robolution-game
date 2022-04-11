package com.ui.industrial_robolution;

import com.game.industrial_robolution.FixedLevels;
import com.game.industrial_robolution.Level;
import com.game.industrial_robolution.Robot;
import com.game.industrial_robolution.Tile;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;

public class FixedLevelsFX {

    private boolean dynamiteIsClicked = false;
    private boolean bridgeIsClicked = false;

    private boolean isStopBtnClicked = false;
    private boolean isLoopBtnClicked = false;

    private int timeToUse;
    private int timeToLoop;
    private int loopTimeToUse;

    private YouWonFX wonPage = new YouWonFX();

    private String levelDiff;

    private GridPane levelPane;
    private Level currentLevel;
    private Tile[][] currentMatrix;
    private Robot robot;
    private Label commandCountLabel;
    private LinkedHashMap<String, Integer> commandCount = new LinkedHashMap<String, Integer>() {{
        put("north", 0);
        put("east", 0);
        put("west", 0);
        put("south", 0);
        put("dynamite", 0);
        put("bridge", 0);
    }};

    private ArrayList<String> actionsInLoop;

    private String[] directions = new String[]{"north", "east", "west", "south"};

    private int[][] loopCount;

    public void drawLevel(String levelDifficulty) {

        //****** Layout ******

        levelPane = new GridPane();
        levelPane.setAlignment(Pos.CENTER);
        levelPane.setStyle("-fx-background-color: #343A40");

        //****** Get matrix ******

        switch (levelDifficulty) {
            case "novice" -> currentLevel = FixedLevels.getNoviceLevel();
            case "adept" -> currentLevel = FixedLevels.getAdeptLevel();
            case "expert" -> currentLevel = FixedLevels.getExpertLevel();
            case "master" -> currentLevel = FixedLevels.getMasterLevel();
            default -> throw new IllegalArgumentException("Level difficulty can only be novice/adept/expert/master.");
        }

        currentMatrix = currentLevel.getMatrix();

        robot = new Robot(currentLevel);
        robot.goToStartingPos();

        setOriginalCommandCount(levelDifficulty);

        //****** Draw matrix ******

        drawMatrix(currentMatrix);

        //****** Add commands ******

        drawCommands();

        levelDiff = levelDifficulty;
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

    private Rectangle getNodeByPos(int row, int col) {
        for (Node node : levelPane.getChildren()) {
            if (GridPane.getRowIndex(node) == row && GridPane.getColumnIndex(node) == col && node instanceof Rectangle) {
                return (Rectangle) node;
            }
        }
        return null;
    }

    private void colorMatrix(Tile[][] matrix) {
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

            bindActionToCommandBtn(key, commandBtn, commandCountLabel);

        }
        row = 0;

        for (int arrayCount = 0; arrayCount < loopCount.length; arrayCount++) {
            commandCountLabel = new Label();
            commandCountLabel.setFont(Font.font("Agency FB", 18));
            commandCountLabel.setStyle("-fx-text-fill: #eeeaa9");
            commandCountLabel.setPadding(new Insets(0, 40, 0, 0));

            StackPane loopBtn = commandsFX.getLoopBtn(loopCount[arrayCount][0], loopCount[arrayCount][1]);

            levelPane.add(loopBtn, 2, row);
            commandCountLabel.setText(loopCount[arrayCount][0] + " x");
            levelPane.add(commandCountLabel, 3, row++);

            int currentBtnTimeToLoop = loopCount[arrayCount][1];

            bindActionToLoopBtn(loopBtn, commandCountLabel, currentBtnTimeToLoop);
        }
        StackPane stopBtn = commandsFX.getCommandBtn("stop", 20);
        levelPane.add(stopBtn, 2, row++);

        bindActionToStopBtn(stopBtn);
    }

    private void bindActionToCommandBtn(String command, StackPane commandBtn, Label commandCountLabel) {

        robot = new Robot(currentLevel);

        for (String direction : directions) {

            if (command.equals(direction)) {
                commandBtn.setOnMouseClicked(e -> {
                    {
                        if (!dynamiteIsClicked && !bridgeIsClicked) {
                            if (!isLoopBtnClicked) {
                                timeToUse = commandCount.get(direction);
                                if (timeToUse > 0) {
                                    boolean isDoable = robot.go(direction);
                                    if (isDoable) {
                                        commandCount.replace(direction, timeToUse - 1);
                                        commandCountLabel.setText(commandCount.get(direction) + " x");
                                        if(currentLevel.getIsWon()) {
                                            commandBtn.getScene().setRoot(wonPage.getWonPane());
                                        }
                                    } else if (robot.getIsReset()) {
                                        setOriginalCommandCount(levelDiff);
                                        commandCountLabel.setText(commandCount.get(direction) + " x");
                                    }
                                }
                            } else {
                                actionsInLoop.add(direction);
                            }
                        }
                    }

                    if (dynamiteIsClicked) {
                        if (!isLoopBtnClicked) {
                            dynamiteIsClicked = false;
                            timeToUse = commandCount.get("dynamite");
                            if (timeToUse >= 0) {
                                boolean isDoable = robot.throwDynamite(direction);
                                if (isDoable) {
                                    int timetoUseDirection = commandCount.get(direction);
                                    commandCount.replace(direction, timetoUseDirection - 1);
                                    commandCountLabel.setText(commandCount.get(direction) + " x");
                                } else if (robot.getIsReset()) {
                                    setOriginalCommandCount(levelDiff);
                                    commandCountLabel.setText(commandCount.get(direction) + " x");
                                }
                            }

                        } else actionsInLoop.add(direction);
                    }

                    if (bridgeIsClicked) {
                        if (!isLoopBtnClicked) {
                            bridgeIsClicked = false;
                            timeToUse = commandCount.get("bridge");
                            if (timeToUse >= 0) {
                                boolean isDoable = robot.buildBridge(direction);
                                if (isDoable) {
                                    int timetoUseDirection = commandCount.get(direction);
                                    commandCount.replace(direction, timetoUseDirection - 1);
                                    commandCountLabel.setText(commandCount.get(direction) + " x");
                                } else if (robot.getIsReset()) {
                                    setOriginalCommandCount(levelDiff);
                                    commandCountLabel.setText(commandCount.get(direction) + " x");
                                }
                            }
                        } else actionsInLoop.add(direction);
                    }
                    colorMatrix(currentMatrix);
                });
            }
            ;
        }
        if (command.equals("dynamite")) {
            commandBtn.setOnMouseClicked(e -> {
                if (!dynamiteIsClicked) {
                    if (!isLoopBtnClicked) {
                        int timeToUse = commandCount.get("dynamite");
                        if (timeToUse > 0) {
                            dynamiteIsClicked = true;
                            commandCount.replace("dynamite", timeToUse - 1);
                            commandCountLabel.setText(commandCount.get("dynamite") + " x");
                        }
                    } else actionsInLoop.add("dynamite");
                } else dynamiteIsClicked = false;
            });
        }
        if (command.equals("bridge")) {
            commandBtn.setOnMouseClicked(e -> {
                if (!bridgeIsClicked) {
                    if (!isLoopBtnClicked) {
                        int timeToUse = commandCount.get("bridge");
                        if (timeToUse > 0) {
                            bridgeIsClicked = true;
                            commandCount.replace("bridge", timeToUse - 1);
                            commandCountLabel.setText(commandCount.get("bridge") + " x");
                        }
                    } else actionsInLoop.add("bridge");
                } else bridgeIsClicked = false;
            });
        }
    }

    private void bindActionToLoopBtn(StackPane loopBtn, Label commandCountLabel, int currentBtnTimeToLoop) {

        loopBtn.setOnMouseClicked(e -> {
            for (int idx = 0; idx < loopCount.length; idx++) {
                if (loopCount[idx][1] == currentBtnTimeToLoop) {
                    loopTimeToUse = loopCount[idx][0];
                    loopCount[idx][0] = loopTimeToUse;
                }
            }
            if (loopTimeToUse > 0) {
                actionsInLoop = new ArrayList<>();
                isLoopBtnClicked = true;
                timeToLoop = currentBtnTimeToLoop;
                loopTimeToUse--;

                for (int idx = 0; idx < loopCount.length; idx++) {
                    if (loopCount[idx][1] == currentBtnTimeToLoop) {
                        loopCount[idx][0] = loopTimeToUse;
                    }
                }
                commandCountLabel.setText(loopTimeToUse + " x");
            }
        });
    }

    private void bindActionToStopBtn(StackPane stopBtn) {
        robot = new Robot(currentLevel);

        stopBtn.setOnMouseClicked(e -> {
            isStopBtnClicked = true;
            int currentLoop = 0;
            boolean isReduceable = false;
            if (isLoopBtnClicked) {
                isLoopBtnClicked = false;
                while (currentLoop < timeToLoop) {
                    for (int idx = 0; idx < actionsInLoop.size(); idx++) {
                        if (idx > 0) {
                            for (String direction : directions) {
                                if (actionsInLoop.get(idx).equals(direction) && !actionsInLoop.get(idx - 1).equals("dynamite") && !actionsInLoop.get(idx - 1).equals("bridge")) {
                                    if (commandCount.get(direction) > 0) {
                                        boolean isDoable = robot.go(direction);
                                        if (isDoable && isReduceable) {
                                            commandCount.replace(direction, commandCount.get(direction) - 1);
                                            isReduceable = false;
                                            if(currentLevel.getIsWon()) {
                                                stopBtn.getScene().setRoot(wonPage.getWonPane());
                                            }
                                        } else if (robot.getIsReset()) {
                                            setOriginalCommandCount(levelDiff);
                                        } else isReduceable = true;
                                    }
                                }
                            }
                            if (actionsInLoop.get(idx).equals("dynamite")) {
                                if (commandCount.get("dynamite") > 0 && commandCount.get(actionsInLoop.get(idx + 1)) > 0) {
                                    boolean isDoable = robot.throwDynamite(actionsInLoop.get(idx + 1));
                                    if (isDoable && isReduceable) {
                                        commandCount.replace("dynamite", commandCount.get("dynamite") - 1);
                                        commandCount.replace(actionsInLoop.get(idx + 1), commandCount.get(actionsInLoop.get(idx + 1)) - 1);
                                        isReduceable = false;
                                    } else if (robot.getIsReset()) {
                                        setOriginalCommandCount(levelDiff);
                                    } else isReduceable = true;
                                }
                            }
                            if (actionsInLoop.get(idx).equals("bridge")) {
                                if (commandCount.get("bridge") > 0 && commandCount.get(actionsInLoop.get(idx + 1)) > 0) {
                                    boolean isDoable = robot.buildBridge(actionsInLoop.get(idx + 1));
                                    if (isDoable && isReduceable) {
                                        commandCount.replace("bridge", commandCount.get("bridge") - 1);
                                        commandCount.replace(actionsInLoop.get(idx + 1), commandCount.get(actionsInLoop.get(idx + 1)) - 1);
                                        isReduceable = false;
                                    } else if (robot.getIsReset()) {
                                        setOriginalCommandCount(levelDiff);
                                    } else isReduceable = true;
                                }
                            }
                        } else {
                            for (String direction : directions) {
                                if (actionsInLoop.get(idx).equals(direction)) {
                                    if (commandCount.get(direction) > 0) {
                                        boolean isDoable = robot.go(direction);
                                        if (isDoable && isReduceable) {
                                            commandCount.replace(direction, commandCount.get(direction) - 1);
                                            isReduceable = false;
                                            if(currentLevel.getIsWon()) {
                                                stopBtn.getScene().setRoot(wonPage.getWonPane());
                                            }
                                        } else if (robot.getIsReset()) {
                                            setOriginalCommandCount(levelDiff);
                                        } else isReduceable = true;
                                    }
                                }
                            }
                            if (actionsInLoop.get(idx).equals("dynamite")) {
                                if (commandCount.get("dynamite") > 0 && commandCount.get(actionsInLoop.get(idx + 1)) > 0) {
                                    boolean isDoable = robot.throwDynamite(actionsInLoop.get(idx + 1));
                                    if (isDoable && isReduceable) {
                                        commandCount.replace("dynamite", commandCount.get("dynamite") - 1);
                                        commandCount.replace(actionsInLoop.get(idx + 1), commandCount.get(actionsInLoop.get(idx + 1)) - 1);
                                        isReduceable = false;
                                    } else if (robot.getIsReset()) {
                                        setOriginalCommandCount(levelDiff);
                                    } else isReduceable = true;
                                }
                            }
                            if (actionsInLoop.get(idx).equals("bridge")) {
                                if (commandCount.get("bridge") > 0 && commandCount.get(actionsInLoop.get(idx + 1)) > 0) {
                                    boolean isDoable = robot.buildBridge(actionsInLoop.get(idx + 1));
                                    if (isDoable && isReduceable) {
                                        commandCount.replace("bridge", commandCount.get("bridge") - 1);
                                        commandCount.replace(actionsInLoop.get(idx + 1), commandCount.get(actionsInLoop.get(idx + 1)) - 1);
                                        isReduceable = false;
                                    } else if (robot.getIsReset()) {
                                        setOriginalCommandCount(levelDiff);
                                    } else isReduceable = true;
                                }
                            }
                        }
                    }
                    colorMatrix(currentMatrix);
                    currentLoop++;
                }
            }
        });
    }

    private void setOriginalCommandCount(String levelDifficulty) {
        switch (levelDifficulty) {
            case "novice" -> {

                commandCount.replace("north", 4);
                commandCount.replace("east", 2);
                commandCount.replace("west", 3);
                commandCount.replace("south", 4);
                commandCount.replace("dynamite", 2);

                loopCount = new int[][]{{1, 2}, {1, 3}};

            }
            case "adept" -> {

                commandCount.replace("east", 4);
                commandCount.replace("west", 2);
                commandCount.replace("south", 4);
                commandCount.replace("dynamite", 2);
                commandCount.replace("bridge", 1);

                loopCount = new int[][]{{1, 2}, {1, 3}};

            }
            case "expert" -> {

                commandCount.replace("east", 4);
                commandCount.replace("west", 2);
                commandCount.replace("south", 4);
                commandCount.replace("dynamite", 2);
                commandCount.replace("bridge", 1);

                loopCount = new int[][]{{1, 2}, {1, 3}};

            }
            case "master" -> {

                commandCount.replace("east", 4);
                commandCount.replace("west", 2);
                commandCount.replace("south", 4);
                commandCount.replace("dynamite", 2);
                commandCount.replace("bridge", 1);

                loopCount = new int[][]{{1, 2}, {1, 3}};

            }
            default -> throw new IllegalArgumentException("Level difficulty can only be novice/adept/expert/master.");
        }
    }

    public GridPane getLevelPane() {
        return levelPane;
    }
}
