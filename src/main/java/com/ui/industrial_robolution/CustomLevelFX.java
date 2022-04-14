package com.ui.industrial_robolution;

import com.game.industrial_robolution.*;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Font;

import java.util.ArrayList;
import java.util.LinkedHashMap;

public class CustomLevelFX {
    private final GridPane createLevelPane;
    private final int row;
    private final int col;
    private final String[] tileTypes = {"Station", "Field", "Rock", "Water"};
    private Button saveBtn;
    private Tile[][] matrix;
    private int stationNumber = 0;
    private final String[] commands = new String[]{"north", "east", "west", "south", "dynamite", "bridge"};
    private final LinkedHashMap<String, Integer> commandCount = new LinkedHashMap<>();
    private final int[][] loopCount = new int[3][2];


    public CustomLevelFX(int row, int col) {
        this.row = row;
        this.col = col;

        //****** Layout ******

        createLevelPane = new GridPane();
        createLevelPane.setAlignment(Pos.CENTER);
        createLevelPane.setStyle("-fx-background-color: #343A40");

        //****** Add commands label ******

        Label addCommandLabel = new Label("Type the number of commands");
        addCommandLabel.setFont(Font.font("Agency FB", 30));
        addCommandLabel.setStyle("-fx-text-fill: #eeeaa9");
        addCommandLabel.setPadding(new Insets(0, 20, 30, 0));
        createLevelPane.add(addCommandLabel, 0, 0);
        GridPane.setColumnSpan(addCommandLabel, 6);


        //****** Add matrix label ******

        Label addMatrixLabel = new Label("Select the type of tiles (must contain at least 2 and max 5 stations)");
        addMatrixLabel.setFont(Font.font("Agency FB", 30));
        addMatrixLabel.setStyle("-fx-text-fill: #eeeaa9");
        addMatrixLabel.setPadding(new Insets(0, 0, 30, 0));
        createLevelPane.add(addMatrixLabel, 6, 0);
        GridPane.setColumnSpan(addMatrixLabel, col);

        drawCommandTiles();

        drawMatrixComboBox();
    }


    private void drawCommandTiles() {
        CommandsFX commandsFX = new CommandsFX();
        StackPane commandTile;
        StackPane loopTile;
        int commandRow = 1;


        for (String command : commands) {
            commandTile = commandsFX.getCommandBtn(command);

            createLevelPane.add(commandTile, 0, commandRow);
            addTextFieldToCommands(1, commandRow);
            commandRow++;
        }

        commandRow = 1;

        for (int loopCount = 2; loopCount < 5; loopCount++) {
            loopTile = commandsFX.getLoopBtn(loopCount);
            createLevelPane.add(loopTile, 3, commandRow);
            addTextFieldToCommands(4, commandRow);
            commandRow++;
        }


        saveBtn = new Button("SAVE");
        createLevelPane.add(saveBtn, 3, 6);
        saveBtn.setFont(Font.font("Agency FB", 30));
        saveBtn.setStyle("-fx-background-color: #73688b; -fx-text-fill: #eeeaa9");
        GridPane.setColumnSpan(saveBtn, 3);
        GridPane.setHalignment(saveBtn, HPos.CENTER);
        handleSave();
    }


    private void addTextFieldToCommands(int col, int row) {
        TextField commandCount = new TextField();
        commandCount.setPrefSize(40, 40);
        createLevelPane.add(commandCount, col, row);

        Label x = new Label(" x");
        x.setFont(Font.font("Agency FB", 40));
        x.setStyle("-fx-text-fill: #eeeaa9");
        x.setPadding(new Insets(0, 20, 0, 0));
        GridPane.setHalignment(x, HPos.CENTER);
        createLevelPane.add(x, col + 1, row);
    }

    private void drawMatrixComboBox() {

        for (int rowCount = 0; rowCount < row; rowCount++) {
            for (int colCount = 0; colCount < col; colCount++) {
                ComboBox<String> typeOfTile = new ComboBox<>();
                for (String tileType : tileTypes) {
                    typeOfTile.getItems().add(tileType);
                }

                typeOfTile.setPrefSize(80, 80);
                createLevelPane.add(typeOfTile, colCount + 6, rowCount + 1);
            }
        }
    }

    private void handleSave() {

        saveBtn.setOnAction(e -> {
                    matrix = generateMatrixFromSelectedFields();
                    if (stationNumber > 1 && stationNumber < 6) {
                        storeCommandCount();
                        createCustomLevel();

                        MenuFX menu = new MenuFX();
                        saveBtn.getScene().setRoot(menu.getRootPane());
                    } else throw new Error("Selected number of stations must be between 2 and 5.");

                }
        );
    }

    private ComboBox<String> getComboBoxByPos(int row, int col) {
        for (Node node : createLevelPane.getChildren()) {
            if (GridPane.getRowIndex(node) == row && GridPane.getColumnIndex(node) == col && node instanceof ComboBox) {
                return (ComboBox<String>) node;
            }
        }
        return null;
    }

    private TextField getTextFieldByPos(int row, int col) {
        for (Node node : createLevelPane.getChildren()) {
            if (GridPane.getRowIndex(node) == row && GridPane.getColumnIndex(node) == col && node instanceof TextField) {
                return (TextField) node;
            }
        }
        return null;
    }

    private Tile[][] generateMatrixFromSelectedFields() {
        matrix = new Tile[row][col];
        for (int rowCount = 0; rowCount < row; rowCount++) {
            for (int colCount = 0; colCount < col; colCount++) {
                ComboBox<String> selectedTile = getComboBoxByPos(rowCount + 1, colCount + 6);
                assert selectedTile != null;
                if (!selectedTile.getSelectionModel().isEmpty()) {
                    if (selectedTile.getValue().equals("Station")) {
                        matrix[rowCount][colCount] = FixedLevels.getTile("s");
                        stationNumber++;
                    } else if (selectedTile.getValue().equals("Field")) {
                        matrix[rowCount][colCount] = FixedLevels.getTile("f");
                    } else if (selectedTile.getValue().equals("Rock")) {
                        matrix[rowCount][colCount] = FixedLevels.getTile("r");
                    } else if (selectedTile.getValue().equals("Water")) {
                        matrix[rowCount][colCount] = FixedLevels.getTile("w");
                    }

                } else throw new NullPointerException("You must select a type on every box!");
            }
        }
        return matrix;
    }

    private void storeCommandCount() {
        TextField selectedField;
        int selectedCommandCount;
        int timeToLoop = 2;
        int rowCount = 1;

        for (String command : commands) {
                selectedField = getTextFieldByPos(rowCount, 1);
                if (selectedField.getText() != null && !selectedField.getText().isEmpty()) {
                    if (selectedField.getText().matches("^[0-9]*$")) {
                        selectedCommandCount = Integer.parseInt(selectedField.getText());
                        if (selectedCommandCount >= 0 && selectedCommandCount <= 10) {
                            commandCount.put(command, selectedCommandCount);
                        } else throw new Error("Number of commands must be between 0 and 10");

                    } else throw new Error("Number of commands must be between 0 and 10");
                } else throw new Error("Input can not be empty!");
            rowCount++;
        }

        for (rowCount = 1; rowCount < 4; rowCount++) {
            selectedField = getTextFieldByPos(rowCount, 4);
            if (selectedField.getText() != null && !selectedField.getText().isEmpty()) {
                if (selectedField.getText().matches("^[0-9]*$")) {
                    selectedCommandCount = Integer.parseInt(selectedField.getText());
                    if (selectedCommandCount >= 0 && selectedCommandCount <= 10) {
                        loopCount[rowCount - 1][0] = selectedCommandCount;
                        loopCount[rowCount - 1][1] = timeToLoop;
                        timeToLoop++;
                    } else throw new Error("Number of commands must be between 0 and 10");
                } else throw new Error("Number of commands must be between 0 and 10");
            } else throw new Error("Input can not be empty!");
        }
    }

    private void createCustomLevel() {
        CustomLevelList custom = new CustomLevelList();
        custom.addSavedCustomLevel(new CustomLevel(row, col, stationNumber, matrix, commandCount, loopCount));
    }

    public GridPane getCreateLevelPane() {
        return createLevelPane;
    }
}