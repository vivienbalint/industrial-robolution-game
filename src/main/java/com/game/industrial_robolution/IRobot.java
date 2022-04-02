package com.game.industrial_robolution;

public interface IRobot {

    int[] goToStartingPos(Level level);

    int[] go(int[] startingPos);

    void build();

    void throwDynamite();

    void startOver();
}
