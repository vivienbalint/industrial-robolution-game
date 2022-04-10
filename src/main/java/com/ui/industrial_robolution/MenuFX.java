package com.ui.industrial_robolution;

import com.game.industrial_robolution.FixedLevels;
import com.game.industrial_robolution.Level;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;

public class MenuFX {

    private final GridPane rootPane;
    private final Label levelLabel;
    private Button levelBtn;
    private Button customLevelBtn;

    public MenuFX() {

        //****** Layout ******

        rootPane = new GridPane();
        rootPane.setAlignment(Pos.CENTER);
        rootPane.setStyle("-fx-background-color: #343A40");
        rootPane.setHgap(20);
        rootPane.setVgap(30);

        //****** Title ******

        levelLabel = new Label("Choose Your Level:");
        levelLabel.setFont(Font.font("Agency FB", 50));
        levelLabel.setStyle("-fx-text-fill: #eeeaa9");
        levelLabel.setPadding(new Insets(0, 0, 20, 0));
        rootPane.add(levelLabel, 1, 1);
        rootPane.setHalignment(levelLabel, HPos.CENTER);
        rootPane.setColumnSpan(levelLabel, 5);

        //****** Buttons ******

        String[] levels = {"novice", "adept", "expert", "master", "infinite"};

        int levelCount = 0;


        for (String level : levels) {

            levelCount++;
            levelBtn = new Button();
            levelBtn.setText(level.toUpperCase());
            levelBtn.setFont(Font.font("Agency FB", 30));
            levelBtn.setStyle("-fx-background-color: #73688b; -fx-text-fill: #eeeaa9");
            rootPane.add(levelBtn, levelCount, 2);
            rootPane.setHalignment(levelBtn, HPos.CENTER);

            levelBtn.setOnAction(e -> {
                FixedLevelsFX fixedLevels = new FixedLevelsFX();
                fixedLevels.drawLevel(level);
                levelBtn.getScene().setRoot(fixedLevels.getLevelPane());
            });
        }

        customLevelBtn = new Button("MAKE YOUR OWN");
        customLevelBtn.setFont(Font.font("Agency FB", 30));
        customLevelBtn.setStyle("-fx-background-color: #73688b; -fx-text-fill: #eeeaa9");
        rootPane.add(customLevelBtn, 1, 3);
        rootPane.setColumnSpan(customLevelBtn, 5);
        rootPane.setHalignment(customLevelBtn, HPos.CENTER);

    }

    public GridPane getRootPane() {
        return rootPane;
    }
}
