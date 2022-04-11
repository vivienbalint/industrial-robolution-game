package com.game.industrial_robolution;

public interface IRobot {

    void goToStartingPos();

    boolean go(String direction);

    boolean throwDynamite(String direction);

    boolean buildBridge(String direction);
}
