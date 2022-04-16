package com.ui.industrial_robolution;

import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;

public class CommandsFX {

    private StackPane commandBtn;

    /**
     * Visszaad egy StackPane-t a megadott paranccsal ellátott címkével
     *
     * A getEmptyBtn() metódus segítségével kapunk egy üres StackPane-t, amelyre a parancsot
     * nagybetűvel ellátott Label-t teszünk.
     *
     * @param command egy parancs
     * @return egy gombként funkcionáló StackPane
     */
    public StackPane getCommandBtn(String command) {
        commandBtn = getEmptyBtn();
        Label commandBtnLabel = new Label();
        commandBtnLabel.setFont(Font.font("Agency FB", 18));
        commandBtnLabel.setStyle("-fx-text-fill: #343A40");
        commandBtnLabel.setText(command.toUpperCase());
        commandBtn.getChildren().add(commandBtnLabel);
        return commandBtn;
    }

    /**
     * Visszaad egy az ismétlés számával ellátott StackPane-t
     *
     * @param sizeOfLoop az ismétlés száma
     * @return egy gombként funkciónáló StackPane
     */
    public StackPane getLoopBtn(int sizeOfLoop) {
        StackPane loopBtn = getEmptyBtn();
        Label loopBtnLabel = new Label();
        loopBtnLabel.setFont(Font.font("Agency FB", 18));
        loopBtnLabel.setStyle("-fx-text-fill: #343A40");
        loopBtnLabel.setText("(" + sizeOfLoop + " x) LOOP");
        loopBtn.getChildren().add(loopBtnLabel);
        return loopBtn;
    }

    /**
     *
     * @return egy üres StackPane
     */
    private StackPane getEmptyBtn() {
            commandBtn = new StackPane();
            Rectangle rectangle = new Rectangle(80, 80, Color.valueOf("#eeeaa9"));
            commandBtn.getChildren().add(rectangle);
            commandBtn.setPadding(new Insets(0, 10, 0, 0));
            rectangle.setArcWidth(80);
            rectangle.setArcHeight(80);
            rectangle.setStroke(Color.BLACK);
            return commandBtn;
    }

}
