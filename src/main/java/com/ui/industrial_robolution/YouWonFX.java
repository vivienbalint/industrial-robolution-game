package com.ui.industrial_robolution;

import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;

public class YouWonFX {
    private final GridPane wonPane;

    public YouWonFX() {
        //****** Layout ******

        wonPane = new GridPane();
        wonPane.setAlignment(Pos.CENTER);
        wonPane.setStyle("-fx-background-color: #343A40");

        //****** Text ******

        Label wonLabel = new Label("YOU WON!");
        wonLabel.setFont(Font.font("Agency FB", 60));
        wonLabel.setStyle("-fx-text-fill: #eeeaa9");
        wonPane.add(wonLabel, 0, 0);

        //****** Menu Button ******

        Button menuBtn = new Button("MENU");
        menuBtn.setFont(Font.font("Agency FB", 30));
        menuBtn.setStyle("-fx-background-color: #73688b; -fx-text-fill: #eeeaa9");
        wonPane.add(menuBtn, 0, 1);
        GridPane.setHalignment(menuBtn, HPos.CENTER);
        MenuFX menu = new MenuFX();
        menuBtn.setOnAction(e -> {
            menuBtn.getScene().setRoot(menu.getRootPane());
        });
    }

    public GridPane getWonPane() {
        return wonPane;
    }
}
