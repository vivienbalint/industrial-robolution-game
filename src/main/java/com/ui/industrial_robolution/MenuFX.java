package com.ui.industrial_robolution;

import com.game.industrial_robolution.CustomLevel;
import com.game.industrial_robolution.CustomLevelList;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;

import java.util.ArrayList;

public class MenuFX {

    private final GridPane rootPane;
    private Button levelBtn;
    private final CustomLevelList customLevelList = new CustomLevelList();

    public MenuFX() {

        //****** Layout ******

        rootPane = new GridPane();
        rootPane.setAlignment(Pos.CENTER);
        rootPane.setStyle("-fx-background-color: #343A40");
        rootPane.setHgap(20);
        rootPane.setVgap(30);

        //****** Title ******

        Label levelLabel = new Label("Choose Your Level:");
        levelLabel.setFont(Font.font("Agency FB", 50));
        levelLabel.setStyle("-fx-text-fill: #eeeaa9");
        levelLabel.setPadding(new Insets(0, 0, 20, 0));
        rootPane.add(levelLabel, 1, 1);
        GridPane.setHalignment(levelLabel, HPos.CENTER);
        GridPane.setColumnSpan(levelLabel, 5);

        //****** Buttons ******

        getFixedLevelButtons();

        if (customLevelList.getSavedCustomLevels().isEmpty() || customLevelList.getSavedCustomLevels().size() < 5) {
            getMakeCustomLevelBtn();
        }

        if (!customLevelList.getSavedCustomLevels().isEmpty()) {
            getCustomLevelBtn();
        }
    }

    private void getFixedLevelButtons() {
        String[] levels = {"novice", "adept", "expert", "master", "infinite"};

        int levelCount = 0;

        for (String level : levels) {
            levelCount++;
            levelBtn = new Button();
            levelBtn.setText(level.toUpperCase());
            levelBtn.setFont(Font.font("Agency FB", 30));
            levelBtn.setStyle("-fx-background-color: #73688b; -fx-text-fill: #eeeaa9");
            rootPane.add(levelBtn, levelCount, 2);
            GridPane.setHalignment(levelBtn, HPos.CENTER);

            levelBtn.setOnAction(e -> {
                LevelsFX levelsFx = new LevelsFX();
                levelsFx.drawLevel(level, 0);
                levelBtn.getScene().setRoot(levelsFx.getLevelPane());
            });
        }
    }

    private void getMakeCustomLevelBtn() {
        Button makeCustomLevelBtn = new Button("MAKE YOUR OWN");
        makeCustomLevelBtn.setFont(Font.font("Agency FB", 30));
        makeCustomLevelBtn.setStyle("-fx-background-color: #73688b; -fx-text-fill: #eeeaa9");
        rootPane.add(makeCustomLevelBtn, 1, 3);
        GridPane.setColumnSpan(makeCustomLevelBtn, 5);
        GridPane.setHalignment(makeCustomLevelBtn, HPos.CENTER);

        makeCustomLevelBtn.setOnAction(e -> {
            SetCustomLevelSizeFX makeCustomLevel = new SetCustomLevelSizeFX();
            makeCustomLevelBtn.getScene().setRoot(makeCustomLevel.getSetCustomLevelSitePane());
        });
    }

    /**
     * Létrehozza az egyedi szintekhez tartozó gombokat
     *
     * A CustomLevelList osztály getSavedCustomLevels() gettere alapján megkapjuk az elmentett
     * egyedi szintek listáját.
     * Ezek alapján létrehozunk hozzájuk gombokat, és meghívjuk a LevelsFX osztályban
     * található drawLevel() metódust.
     */
    private void getCustomLevelBtn() {
        ArrayList<CustomLevel> levelList = customLevelList.getSavedCustomLevels();

        for (int savedCustomLevelIndex = 0; savedCustomLevelIndex < levelList.size(); savedCustomLevelIndex++) {
            Button customLevelBtn = new Button("SAVED " + (savedCustomLevelIndex + 1));
            customLevelBtn.setFont(Font.font("Agency FB", 30));
            customLevelBtn.setStyle("-fx-background-color: #73688b; -fx-text-fill: #eeeaa9");
            rootPane.add(customLevelBtn, savedCustomLevelIndex + 1, 4);

            int finalSavedCustomLevelIndex = savedCustomLevelIndex;
            customLevelBtn.setOnAction(e -> {
                LevelsFX levelsFX = new LevelsFX();
                levelsFX.drawLevel("custom", finalSavedCustomLevelIndex);
                customLevelBtn.getScene().setRoot(levelsFX.getLevelPane());
            });
        }
    }

    public GridPane getRootPane() {
        return rootPane;
    }
}
