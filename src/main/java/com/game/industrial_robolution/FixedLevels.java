package com.game.industrial_robolution;

public class FixedLevels {

    public static Tile getTile(String type) {
        switch(type) {
            case "r" -> {
                return new Tile("rock", false, "grey");
            }
            case "w" -> {
                return new Tile("water", false, "darkCyan");
            }
            case "f" -> {
                return new Tile("field", true, "lightGreen");
            }
            case "s" -> {
                return new Tile("station", true, "peru");
            }
            default -> throw new IllegalArgumentException("Type can only be r/w/f/s");
        }
    }

    private static final Tile[][] noviceLevelMatrix = new Tile[][]{{getTile("r"), getTile("r"), getTile("r"), getTile("s"), getTile("r"), getTile("r")},
            {getTile("r"), getTile("r"), getTile("r"), getTile("r"), getTile("r"), getTile("f")},
            {getTile("f"), getTile("f"), getTile("f"), getTile("f"), getTile("w"), getTile("r")},
            {getTile("r"), getTile("r"), getTile("r"), getTile("r"), getTile("w"), getTile("w")},
            {getTile("r"), getTile("f"), getTile("r"), getTile("w"), getTile("w"), getTile("w")},
            {getTile("s"), getTile("f"), getTile("r"), getTile("r"), getTile("r"), getTile("f")}};

    private static final Tile[][] adeptLevelMatrix = new Tile[][]{{getTile("s"), getTile("f"), getTile("f"), getTile("r"), getTile("r"), getTile("r")},
            {getTile("f"), getTile("f"), getTile("f"), getTile("r"), getTile("r"), getTile("r")},
            {getTile("w"), getTile("w"), getTile("w"), getTile("w"), getTile("r"), getTile("f")},
            {getTile("w"), getTile("w"), getTile("w"), getTile("w"), getTile("w"), getTile("f")},
            {getTile("f"), getTile("w"), getTile("w"), getTile("w"), getTile("f"), getTile("r")},
            {getTile("f"), getTile("f"), getTile("s"), getTile("r"), getTile("f"), getTile("r")}};

    private static final Tile[][] expertLevelMatrix = new Tile[][]{{getTile("s"), getTile("r"), getTile("r"), getTile("r"), getTile("r"), getTile("s"), getTile("r")},
            {getTile("f"), getTile("f"), getTile("r"), getTile("r"), getTile("r"), getTile("r"), getTile("r")},
            {getTile("w"), getTile("r"), getTile("r"), getTile("f"), getTile("r"), getTile("f"), getTile("f")},
            {getTile("w"), getTile("w"), getTile("f"), getTile("r"), getTile("f"), getTile("f"), getTile("f")},
            {getTile("w"), getTile("w"), getTile("f"), getTile("r"), getTile("f"), getTile("r"), getTile("w")},
            {getTile("w"), getTile("s"), getTile("f"), getTile("r"), getTile("w"), getTile("r"), getTile("f")},
            {getTile("w"), getTile("f"), getTile("f"), getTile("f"), getTile("r"), getTile("r"), getTile("f")}};

    private static final Tile[][] masterLevelMatrix = new Tile[][]{{getTile("f"), getTile("s"), getTile("w"), getTile("f"), getTile("f"), getTile("w"), getTile("w"), getTile("w")},
            {getTile("f"), getTile("f"), getTile("w"), getTile("f"), getTile("f"), getTile("w"), getTile("w"), getTile("f")},
            {getTile("f"), getTile("f"), getTile("f"), getTile("w"), getTile("w"), getTile("w"), getTile("w"), getTile("w")},
            {getTile("f"), getTile("r"), getTile("w"), getTile("w"), getTile("s"), getTile("w"), getTile("w"), getTile("w")},
            {getTile("r"), getTile("f"), getTile("w"), getTile("w"), getTile("w"), getTile("f"), getTile("w"), getTile("w")},
            {getTile("r"), getTile("r"), getTile("w"), getTile("r"), getTile("r"), getTile("f"), getTile("w"), getTile("w")},
            {getTile("s"), getTile("f"), getTile("w"), getTile("r"), getTile("r"), getTile("r"), getTile("f"), getTile("f")},
            {getTile("f"), getTile("f"), getTile("w"), getTile("f"), getTile("f"), getTile("r"), getTile("r"), getTile("s")}};




  /*  private static final Tile[][] noviceLevelMatrix = new Tile[][]{{r, r, r, s, r, r},
            {r, r, r, r, r, f},
            {f, f, f, f, w, r},
            {r, r, r, r, w, w},
            {r, f, r, w, w, w},
            {s, f, r, r, r, f}};

    private static final Tile[][] adeptLevelMatrix = new Tile[][]{{s, f, f, r, r, r},
            {f, f, f, r, r, r},
            {w, w, w, w, r, f},
            {w, w, w, w, w, f},
            {f, w, w, w, f, r},
            {f, f, s, r, f, r}};

    private static final Tile[][] expertLevelMatrix = new Tile[][]{{s, r, r, r, r, s, r},
            {f, f, r, r, r, r, r},
            {w, r, r, f, r, f, f},
            {w, w, f, r, f, f, f},
            {w, w, f, r, f, r, w},
            {w, s, f, r, w, r, f},
            {w, f, f, f, r, r, f}};

    private static final Tile[][] masterLevelMatrix = new Tile[][]{{f, s, w, f, f, w, w, w},
            {f, f, w, f, f, w, w, f},
            {f, f, f, w, w, w, w, w},
            {f, r, w, w, s, w, w, w},
            {r, f, w, w, w, f, w, w},
            {r, r, w, r, r, f, w, w},
            {s, f, w, r, r, r, f, f},
            {f, f, w, f, f, r, r, s}}; */

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
