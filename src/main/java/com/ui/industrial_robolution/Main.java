package com.ui.industrial_robolution;

import com.game.industrial_robolution.FixedLevels;
import com.game.industrial_robolution.Level;
import com.game.industrial_robolution.Tile;
import javafx.application.Application;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class Main extends Application {
    @Override
    public void start(Stage primaryStage) {

        //****** Layout ******

        GridPane layout = new GridPane();
        layout.setAlignment(Pos.CENTER);
        layout.setStyle("-fx-background-color: #343A40");

        //****** Scene ******

        Scene scene = new Scene(layout, 800, 700);

        //****** Title ******

        Label title = new Label("Industrial Robolution\n");
        layout.add(title, 1, 1);
        title.setPadding(new Insets(0, 0, 50, 0));
        title.setFont(Font.font("Agency FB", 50));
        title.setStyle("-fx-text-fill: #eeeaa9");

        //****** Start Button ******

        Button startBtn = new Button("START");
        layout.add(startBtn, 1, 2);
        startBtn.setFont(Font.font("Agency FB", 30));

        startBtn.setStyle("-fx-background-color: #73688b; -fx-text-fill: #eeeaa9");
        layout.setHalignment(startBtn, HPos.CENTER);

        startBtn.setOnAction(e -> {
            MenuFX menu = new MenuFX();
            startBtn.getScene().setRoot(menu.getRootPane());
        });


        primaryStage.setTitle("Industrial Robolution");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch();

    }
}