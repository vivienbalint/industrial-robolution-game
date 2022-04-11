package com.ui.industrial_robolution;

import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;

public class CommandsFX {

    private StackPane commandBtn;

    public StackPane getCommandBtn(String command, int timeToUse) {
        commandBtn = getEmptyBtn(timeToUse);
        Label commandBtnLabel = new Label();
        commandBtnLabel.setFont(Font.font("Agency FB", 18));
        commandBtnLabel.setStyle("-fx-text-fill: #343A40");
        switch (command) {
            case "north" -> commandBtnLabel.setText("NORTH");
            case "east" -> commandBtnLabel.setText("EAST");
            case "west" -> commandBtnLabel.setText("WEST");
            case "south" -> commandBtnLabel.setText("SOUTH");
            case "dynamite" -> commandBtnLabel.setText("DYNAMITE");
            case "bridge" -> commandBtnLabel.setText("BRIDGE");
            case "stop" -> commandBtnLabel.setText("STOP");
            default -> throw new IllegalArgumentException("Command must be north/east/west/south/dynamite/bridge/loop/stop");
        }
        commandBtn.getChildren().add(commandBtnLabel);
        return commandBtn;
    }

    public StackPane getLoopBtn(int timeToUse, int sizeOfLoop) {
        StackPane loopBtn = getEmptyBtn(timeToUse);
        Label loopBtnLabel = new Label();
        loopBtnLabel.setFont(Font.font("Agency FB", 18));
        loopBtnLabel.setStyle("-fx-text-fill: #343A40");
        loopBtnLabel.setText("(" + sizeOfLoop + " x) LOOP");
        loopBtn.getChildren().add(loopBtnLabel);
        return loopBtn;
    }

    private StackPane getEmptyBtn(int timeToUse) {
        if (timeToUse >= 0 && timeToUse <= 20) {
            commandBtn = new StackPane();
            Rectangle rectangle = new Rectangle(80, 80, Color.valueOf("#eeeaa9"));
            commandBtn.getChildren().add(rectangle);
            commandBtn.setPadding(new Insets(0, 10, 0, 0));
            rectangle.setArcWidth(80);
            rectangle.setArcHeight(80);
            rectangle.setStroke(Color.BLACK);
            return commandBtn;
        } else throw new IllegalArgumentException("TimeToUse can only be between 0 and 20");
    }

}
