package com.game.industrial_robolution;

public class FixedLevels {

    private static Tile r = new Tile("rock", false, "grey");
    private static Tile w = new Tile("water", false, "lightBlue");
    private static Tile f = new Tile("field", true, "lightGreen");
    private static Tile s = new Tile("station", true, "peru");

    private static Tile[][] noviceLevelMatrix = new Tile[][]{{r, r, r, s, r, r},
            {r, r, r, r, r, f},
            {f, f, f, f, w, r},
            {r, r, r, r, w, w},
            {r, f, r, w, w, w},
            {s, f, r, r, r, f}};

    private static Tile[][] adeptLevelMatrix = new Tile[][]{{s, f, f, r, r, r},
            {f, f, f, r, r, r},
            {w, w, w, w, r, f},
            {w, w, w, w, w, f},
            {f, w, w, w, f, r},
            {f, f, s, r, f, r}};

    private static Tile[][] expertLevelMatrix = new Tile[][]{{s, r, r, r, r, s, r},
            {f, f, r, r, r, r, r},
            {w, r, r, f, r, f, f},
            {w, w, f, r, f, f, f},
            {w, w, f, r, f, r, w},
            {w, s, f, r, w, r, f},
            {w, f, f, f, r, r, f}};

    private static Tile[][] masterLevelMatrix = new Tile[][]{{f, s, w, f, f, w, w, w},
            {f, f, w, f, f, w, w, f},
            {f, f, f, w, w, w, w, w},
            {f, r, w, w, s, w, w, w},
            {r, f, w, w, w, f, w, w},
            {r, r, w, r, r, f, w, w},
            {s, f, w, r, r, r, f, f},
            {f, f, w, f, f, r, r, s}};

    public static Level getNoviceLevel() {
        return new Level(6, 6, 2, noviceLevelMatrix);
    }

    public static Level getAdeptLevel() {
        return new Level(6, 6, 2, adeptLevelMatrix);
    }

    public static Level getExpertLevel() {
        return new Level(7, 7, 3, expertLevelMatrix);
    }

    public static Level getMasterLevel() {
        return new Level(8, 8, 4, masterLevelMatrix);
    }


}
