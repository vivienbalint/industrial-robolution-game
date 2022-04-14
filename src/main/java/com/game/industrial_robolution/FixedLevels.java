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
