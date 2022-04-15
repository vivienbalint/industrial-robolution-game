package com.ui.industrial_robolution;

import com.game.industrial_robolution.*;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Random;

public class LevelsFX {
    private GridPane levelPane;
    private Level currentLevel;
    private Tile[][] currentMatrix;

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
    private Label commandCountLabel;
    private String currentLevelDifficulty;

    private YouWonFX wonPage = new YouWonFX();
    private MenuFX menu = new MenuFX();
    private InfiniteLevel infiniteLevel = new InfiniteLevel();
    private CustomLevelList customLevelList = new CustomLevelList();
    private Robot robot;

    private int timeToUse;
    private int timeToLoop;
    private int loopTimeToUse;

    private boolean dynamiteIsClicked = false;
    private boolean bridgeIsClicked = false;

    private boolean isStopBtnClicked = false;
    private boolean isLoopBtnClicked = false;

    private boolean isInfinite;

    public void drawLevel(String levelDifficulty, int savedCustomLevelIndex) {

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
            case "infinite" -> {
                infiniteLevel.setSavedCol(infiniteLevel.generateSavedCol(9));
                currentLevel = new Level(9, 9, 4, infiniteLevel.generateMatrix(9, 9, 4));
                currentLevel.setInfinite(true);
            }
            case "custom" -> {
                ArrayList<CustomLevel> savedLevels = customLevelList.getSavedCustomLevels();
                if(savedCustomLevelIndex > 0 && savedCustomLevelIndex <= savedLevels.size()) {
                    for (int idx = 0; idx < 5; idx++) {
                        if (savedCustomLevelIndex - 1 == idx) {
                            currentLevel = savedLevels.get(idx);
                        }
                    }
                } else throw new IllegalArgumentException("There is no level with this number!");
            }
            default -> throw new IllegalArgumentException("Level difficulty can only be novice/adept/expert/master/infinite/custom.");
        }

        currentMatrix = currentLevel.getMatrix();

        robot = new Robot(currentLevel);
        robot.goToStartingPos();

        setOriginalCommandCount(levelDifficulty);

        //****** Draw matrix ******

        drawMatrix(currentMatrix);

        //****** Add commands ******

        drawCommands();

        currentLevelDifficulty = levelDifficulty;
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
        drawMenuBtn();
    }

    private void drawMenuBtn() {
        Button menuBtn = new Button("MENU");
        menuBtn.setFont(Font.font("Agency FB", 30));
        menuBtn.setStyle("-fx-background-color: #73688b; -fx-text-fill: #eeeaa9");
        levelPane.add(menuBtn, 2, 5);

        menuBtn.setOnAction(e -> {
            menuBtn.getScene().setRoot(menu.getRootPane());
        });
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

            StackPane commandBtn = commandsFX.getCommandBtn(key);

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

            StackPane loopBtn = commandsFX.getLoopBtn(loopCount[arrayCount][1]);

            levelPane.add(loopBtn, 2, row);
            commandCountLabel.setText(loopCount[arrayCount][0] + " x");
            levelPane.add(commandCountLabel, 3, row++);

            int currentBtnTimeToLoop = loopCount[arrayCount][1];

            bindActionToLoopBtn(loopBtn, commandCountLabel, currentBtnTimeToLoop);
        }
        StackPane stopBtn = commandsFX.getCommandBtn("stop");
        levelPane.add(stopBtn, 2, row++);

        bindActionToStopBtn(stopBtn);
    }

    private void bindActionToCommandBtn(String command, StackPane commandBtn, Label commandCountLabel) {

        robot = new Robot(currentLevel);
        isInfinite = currentLevel.isInfinite();

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
                                        if (currentLevel.getIsWon() && !isInfinite) {
                                            commandBtn.getScene().setRoot(wonPage.getWonPane());
                                        } else if (currentLevel.getIsWon() && isInfinite) {
                                            setNewInfiniteMatrix();
                                            addNewRandomCommands();
                                        }
                                    } else if (robot.getIsReset() && !isInfinite) {
                                        setOriginalCommandCount(currentLevelDifficulty);
                                        commandCountLabel.setText(commandCount.get(direction) + " x");
                                    } else if (robot.isGoToMenu()) {
                                        commandBtn.getScene().setRoot(menu.getRootPane());
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
                                } else if (robot.getIsReset() && !isInfinite) {
                                    setOriginalCommandCount(currentLevelDifficulty);
                                    commandCountLabel.setText(commandCount.get(direction) + " x");
                                } else if (robot.isGoToMenu()) {
                                    commandBtn.getScene().setRoot(menu.getRootPane());
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
                                } else if (robot.getIsReset() && !isInfinite) {
                                    setOriginalCommandCount(currentLevelDifficulty);
                                    commandCountLabel.setText(commandCount.get(direction) + " x");
                                } else if (robot.isGoToMenu()) {
                                    commandBtn.getScene().setRoot(menu.getRootPane());
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
            boolean loopHasRun = false;
            boolean commandHasRun = false;
            if (isLoopBtnClicked) {
                isLoopBtnClicked = false;
                while (currentLoop < timeToLoop) {
                    for (int idx = 0; idx < actionsInLoop.size(); idx++) {
                        if (idx > 0 || actionsInLoop.get(idx).equals("dynamite") || actionsInLoop.get(idx).equals("bridge")) {
                            for (String direction : directions) {
                                if (actionsInLoop.get(idx).equals(direction) && !actionsInLoop.get(idx - 1).equals("dynamite") && !actionsInLoop.get(idx - 1).equals("bridge")) {
                                    if (commandCount.get(direction) > 0 && !loopHasRun) {
                                        boolean isDoable = robot.go(direction);
                                        if (isDoable && !commandHasRun) {
                                            int[] pos = currentLevel.getPos();
                                            commandCount.replace(direction, commandCount.get(direction) - 1);
                                            commandHasRun = true;
                                            loopHasRun = true;
                                            if (currentLevel.getIsWon() && !isInfinite) {
                                                stopBtn.getScene().setRoot(wonPage.getWonPane());
                                            } else if (currentLevel.getIsWon() && isInfinite) {
                                                setNewInfiniteMatrix();
                                                addNewRandomCommands();
                                            }
                                        } else if (robot.isGoToMenu()) {
                                            stopBtn.getScene().setRoot(menu.getRootPane());
                                        } else if (robot.getIsReset() && !isInfinite) {
                                            setOriginalCommandCount(currentLevelDifficulty);
                                        }
                                    } else if (loopHasRun) {
                                        robot.go(direction);
                                    }
                                }
                            }
                            if (actionsInLoop.get(idx).equals("dynamite")) {
                                if (commandCount.get("dynamite") > 0 && commandCount.get(actionsInLoop.get(idx + 1)) > 0 && !loopHasRun) {
                                    boolean isDoable = robot.throwDynamite(actionsInLoop.get(idx + 1));
                                    if (isDoable && !commandHasRun) {
                                        commandCount.replace("dynamite", commandCount.get("dynamite") - 1);
                                        commandCount.replace(actionsInLoop.get(idx + 1), (commandCount.get(actionsInLoop.get(idx + 1)) - 1));
                                        commandHasRun = true;
                                        loopHasRun = true;
                                    } else if (robot.isGoToMenu()) {
                                        stopBtn.getScene().setRoot(menu.getRootPane());
                                    } else if (robot.getIsReset() && !isInfinite) {
                                        setOriginalCommandCount(currentLevelDifficulty);
                                    }
                                } else if (loopHasRun) {
                                    robot.throwDynamite(actionsInLoop.get(idx + 1));
                                }
                            }
                            if (actionsInLoop.get(idx).equals("bridge")) {
                                if (commandCount.get("bridge") > 0 && commandCount.get(actionsInLoop.get(idx + 1)) > 0 && !loopHasRun) {
                                    boolean isDoable = robot.buildBridge(actionsInLoop.get(idx + 1));
                                    if (isDoable && !commandHasRun) {
                                        commandCount.replace("bridge", commandCount.get("bridge") - 1);
                                        commandCount.replace(actionsInLoop.get(idx + 1), commandCount.get(actionsInLoop.get(idx + 1)) - 1);
                                        commandHasRun = true;
                                        loopHasRun = true;
                                    } else if (robot.isGoToMenu()) {
                                        stopBtn.getScene().setRoot(menu.getRootPane());
                                    } else if (robot.getIsReset() && !isInfinite) {
                                        setOriginalCommandCount(currentLevelDifficulty);
                                    }
                                } else if (loopHasRun) {
                                    robot.buildBridge(actionsInLoop.get(idx + 1));
                                }
                            }
                        } else {
                            for (String direction : directions) {
                                if (actionsInLoop.get(idx).equals(direction)) {
                                    if (commandCount.get(direction) > 0 && !loopHasRun) {
                                        boolean isDoable = robot.go(direction);
                                        if (isDoable && !commandHasRun) {
                                            commandCount.replace(direction, commandCount.get(direction) - 1);
                                            commandHasRun = true;
                                            loopHasRun = true;
                                            if (currentLevel.getIsWon() && !isInfinite) {
                                                stopBtn.getScene().setRoot(wonPage.getWonPane());
                                            } else if (currentLevel.getIsWon() && isInfinite) {
                                                setNewInfiniteMatrix();
                                                addNewRandomCommands();
                                            }
                                        } else if (robot.isGoToMenu()) {
                                            stopBtn.getScene().setRoot(menu.getRootPane());
                                        } else if (robot.getIsReset() && !isInfinite) {
                                            setOriginalCommandCount(currentLevelDifficulty);
                                        }
                                    } else if (loopHasRun) {
                                        robot.go(direction);
                                    }
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

                commandCount.replace("east", 10);
                commandCount.replace("west", 10);
                commandCount.replace("south", 10);
                commandCount.replace("dynamite", 10);
                commandCount.replace("bridge", 10);

                loopCount = new int[][]{{10, 2}, {10, 3}};

            }
            case "infinite" -> {
                int maxRandomInt = 10;
                for (String direction : directions) {
                    commandCount.replace(direction, new Random().nextInt(maxRandomInt));
                }
                commandCount.replace("dynamite", new Random().nextInt(maxRandomInt));
                commandCount.replace("bridge", new Random().nextInt(maxRandomInt));

                loopCount = new int[][]{{new Random().nextInt(maxRandomInt), 2}, {new Random().nextInt(maxRandomInt), 3}, {new Random().nextInt(maxRandomInt), 4}};
            }
            case "custom" -> {
                commandCount = ((CustomLevel)currentLevel).getCommandCount();
                loopCount = ((CustomLevel)currentLevel).getLoopCount();
            }
            default -> throw new IllegalArgumentException("Level difficulty can only be novice/adept/expert/master/custom.");
        }
    }

    private void setNewInfiniteMatrix() {
        currentLevel.setMatrix(infiniteLevel.generateMatrix(currentLevel.getRow(), currentLevel.getCol(), currentLevel.getStationNumber()));
        currentMatrix = currentLevel.getMatrix();
        robot.goToStartingPos();
        currentLevel.setIsWon(false);
        colorMatrix(currentMatrix);
    }

    private void addNewRandomCommands() {

        int maxRandomInt = 7;

        for (String direction : directions) {
            commandCount.replace(direction, commandCount.get(direction) + new Random().nextInt(maxRandomInt));
        }

        commandCount.replace("dynamite", commandCount.get("dynamite") + new Random().nextInt(maxRandomInt));
        commandCount.replace("bridge", commandCount.get("bridge") + new Random().nextInt(maxRandomInt));

        for (int idx = 0; idx < loopCount.length; idx++) {
            loopCount[idx][0] += new Random().nextInt(maxRandomInt);
        }
    }

    public GridPane getLevelPane() {
        return levelPane;
    }
}
