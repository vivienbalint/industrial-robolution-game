package com.ui.industrial_robolution;

import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;

public class SetCustomLevelSizeFX {
    private final GridPane setCustomLevelSitePane;


    public SetCustomLevelSizeFX() {

        //****** Layout ******

        setCustomLevelSitePane = new GridPane();
        setCustomLevelSitePane.setAlignment(Pos.CENTER);
        setCustomLevelSitePane.setStyle("-fx-background-color: #343A40");
        setCustomLevelSitePane.setHgap(20);
        setCustomLevelSitePane.setVgap(30);

        for (int colCounter = 0; colCounter < 7; colCounter++) {
            setCustomLevelSitePane.getColumnConstraints().add(new ColumnConstraints(40));
        }


        //****** Label ******

        Label title = new Label("Set the size: ");
        title.setFont(Font.font("Agency FB", 50));
        title.setStyle("-fx-text-fill: #eeeaa9");
        setCustomLevelSitePane.add(title, 0, 0);
        GridPane.setHalignment(title, HPos.CENTER);
        GridPane.setColumnSpan(title, 7);

        //****** Text Field ******

        TextField row = new TextField();
        row.setPrefSize(40,40);
        Label x = new Label("x");
        x.setFont(Font.font("Agency FB", 40));
        x.setStyle("-fx-text-fill: #eeeaa9");
        GridPane.setHalignment(x, HPos.CENTER);
        TextField col = new TextField();
        col.setPrefSize(40,40);
        setCustomLevelSitePane.add(row, 2, 1);
        setCustomLevelSitePane.add(x, 3, 1);
        setCustomLevelSitePane.add(col, 4, 1);


        //****** Continue Button ******

        Button ctnBtn = new Button("CONTINUE");
        ctnBtn.setFont(Font.font("Agency FB", 30));
        ctnBtn.setStyle("-fx-background-color: #73688b; -fx-text-fill: #eeeaa9");
        setCustomLevelSitePane.add(ctnBtn, 0, 2);
        GridPane.setColumnSpan(ctnBtn, 7);
        GridPane.setHalignment(ctnBtn, HPos.CENTER);

        ctnBtn.setOnAction(e -> {
            if(row.getText() != null && col.getText() != null && !row.getText().isEmpty() && !col.getText().isEmpty()) {
                if(row.getText().matches("^[0-9]*$") && col.getText().matches("^[0-9]*$")) {
                    int rowCount = Integer.parseInt(row.getText());
                    int colCount = Integer.parseInt(col.getText());
                    if(rowCount > 5 && rowCount < 11 && colCount > 5 && colCount < 11) {
                        CustomLevelFX customLevel = new CustomLevelFX(rowCount, colCount);
                        ctnBtn.getScene().setRoot(customLevel.getCreateLevelPane());
                    } else throw new Error("Row and Col must be between 6 and 10");
                } else throw new Error("Row and Col must be between 6 and 10");
            } else throw new Error("Input can not be empty!");


        });

    }

    public GridPane getSetCustomLevelSitePane() {
        return setCustomLevelSitePane;
    }
}
