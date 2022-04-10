package com.game.industrial_robolution;

public interface IRobot {

    void goToStartingPos();

    void go(String direction);

    void throwDynamite(String direction);

    void buildBridge(String direction);
}
