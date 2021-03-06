package com.game.industrial_robolution;

public class FixedLevels {
    public FixedLevels() {
    }

    /**
     * Létrehoz egy új Tile típusú csempét
     * <p>
     * A megadott típusok között switchel, hogyha nem a megadott típusok közül adtunk meg
     * paraméterként, errort dob.
     *
     * @param type egy String típus, értéke lehet "r/w/f/s"
     * @return egy új Tile típusú object
     */
    public Tile getTile(String type) {
        switch (type) {
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

    private final Tile[][] noviceLevelMatrix = new Tile[][]{{getTile("r"), getTile("r"), getTile("r"), getTile("s"), getTile("r"), getTile("r")},
            {getTile("r"), getTile("r"), getTile("r"), getTile("r"), getTile("r"), getTile("f")},
            {getTile("f"), getTile("f"), getTile("f"), getTile("f"), getTile("w"), getTile("r")},
            {getTile("r"), getTile("r"), getTile("r"), getTile("r"), getTile("w"), getTile("w")},
            {getTile("r"), getTile("f"), getTile("r"), getTile("w"), getTile("w"), getTile("w")},
            {getTile("s"), getTile("f"), getTile("r"), getTile("r"), getTile("r"), getTile("f")}};

    private final Tile[][] adeptLevelMatrix = new Tile[][]{{getTile("s"), getTile("f"), getTile("f"), getTile("r"), getTile("r"), getTile("r")},
            {getTile("f"), getTile("f"), getTile("f"), getTile("r"), getTile("r"), getTile("r")},
            {getTile("w"), getTile("w"), getTile("w"), getTile("w"), getTile("r"), getTile("f")},
            {getTile("w"), getTile("w"), getTile("w"), getTile("w"), getTile("w"), getTile("f")},
            {getTile("f"), getTile("w"), getTile("w"), getTile("w"), getTile("f"), getTile("r")},
            {getTile("f"), getTile("f"), getTile("s"), getTile("r"), getTile("f"), getTile("r")}};

    private final Tile[][] expertLevelMatrix = new Tile[][]{{getTile("s"), getTile("r"), getTile("r"), getTile("r"), getTile("r"), getTile("s"), getTile("r")},
            {getTile("f"), getTile("f"), getTile("r"), getTile("r"), getTile("r"), getTile("r"), getTile("r")},
            {getTile("w"), getTile("r"), getTile("r"), getTile("f"), getTile("r"), getTile("f"), getTile("f")},
            {getTile("w"), getTile("w"), getTile("f"), getTile("r"), getTile("f"), getTile("f"), getTile("f")},
            {getTile("w"), getTile("w"), getTile("f"), getTile("r"), getTile("f"), getTile("r"), getTile("w")},
            {getTile("w"), getTile("s"), getTile("f"), getTile("r"), getTile("w"), getTile("r"), getTile("f")},
            {getTile("w"), getTile("f"), getTile("f"), getTile("f"), getTile("r"), getTile("r"), getTile("f")}};

    private final Tile[][] masterLevelMatrix = new Tile[][]{{getTile("f"), getTile("s"), getTile("f"), getTile("f"), getTile("f"), getTile("w"), getTile("w"), getTile("w")},
            {getTile("f"), getTile("f"), getTile("w"), getTile("f"), getTile("f"), getTile("w"), getTile("w"), getTile("f")},
            {getTile("f"), getTile("f"), getTile("f"), getTile("w"), getTile("w"), getTile("w"), getTile("w"), getTile("w")},
            {getTile("f"), getTile("r"), getTile("w"), getTile("w"), getTile("s"), getTile("w"), getTile("w"), getTile("w")},
            {getTile("r"), getTile("f"), getTile("w"), getTile("w"), getTile("w"), getTile("f"), getTile("w"), getTile("w")},
            {getTile("r"), getTile("r"), getTile("w"), getTile("r"), getTile("r"), getTile("f"), getTile("w"), getTile("w")},
            {getTile("s"), getTile("f"), getTile("w"), getTile("r"), getTile("r"), getTile("r"), getTile("f"), getTile("f")},
            {getTile("f"), getTile("f"), getTile("w"), getTile("f"), getTile("f"), getTile("r"), getTile("r"), getTile("s")}};

    public Level getNoviceLevel() {
        return new Level(6, 6, 2, noviceLevelMatrix);
    }

    public Level getAdeptLevel() {
        return new Level(6, 6, 2, adeptLevelMatrix);
    }

    public Level getExpertLevel() {
        return new Level(7, 7, 3, expertLevelMatrix);
    }

    public Level getMasterLevel() {
        return new Level(8, 8, 4, masterLevelMatrix);
    }


}
