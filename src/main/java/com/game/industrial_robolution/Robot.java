package com.game.industrial_robolution;

public class Robot implements IRobot {

    Level level;
    private Tile[][] levelMatrix;
    private int matrixRow;
    private int matrixCol;
    private final String msg = "You can't go that way :)";
    private boolean isDoable = false;
    private boolean isReset = false;
    private boolean isInfinite = false;
    private boolean goToMenu = false;

    public Robot(Level level) {
        this.level = level;
    }

    public boolean getIsReset() {
        return isReset;
    }

    public boolean isGoToMenu() {
        return goToMenu;
    }

    public void setReset(boolean reset) {
        isReset = reset;
    }

    /**
     * Megkeresi az adott mátrixban az első "station" típusú csempét, és beállítja a koordinátáit
     *
     * Ha az adott szint nem végtelen típusú, végig iterál az egész mátrixon,
     * ha végtelen, akkor pedig tudjuk, hogy az első oszlopban biztosan van egy "station" típus, ezért
     * csak azt az oszlopot figyeljük.
     * Beállítja a színét, beállítja a koordinátákat es inkrementálja a stationNumberBuiltOn változót a Level osztályban.
     */
    @Override
    public void goToStartingPos() {

        levelMatrix = level.getMatrix();
        matrixRow = level.getRow();
        matrixCol = level.getCol();
        isInfinite = level.isInfinite();
        int[] pos = {0, 0};

        if(!isInfinite) {
            outer:
            for (int row = 0; row < matrixRow; row++) {
                for (int col = 0; col < matrixCol; col++) {
                    if (levelMatrix[row][col].getType().equals("station")) {
                        pos[0] = row;
                        pos[1] = col;
                        levelMatrix[row][col].setColor("sienna");
                        level.setStationNumberBuiltOn(1);
                        level.setPos(pos);
                        break outer;
                    }
                }
            }
        } else {
            for(int row = 0; row < matrixRow; row++) {
                if(levelMatrix[row][0].getType().equals("station")) {
                    pos[0] = row;
                    pos[1] = 0;
                    levelMatrix[row][0].setColor("sienna");
                    level.setStationNumberBuiltOn(1);
                    level.setPos(pos);
                }
            }
        }
    }

    /**
     * A megadott irányban lép egyet
     *
     * A kezdő koordinátákat lekérjük a Level osztályból a getPos() getter segítségével.
     * A megadott irány szerint figyeljük a következő csempe állapotát, ha létezik.
     * Ha a csempére lehet építeni, és a színe nem "forestGreen" (vagyis még nem építettünk rá),
     * akkor beállítjuk a koordinátáit, beállítjuk az isDoable változót true-ra, vagyis hogy a lépés lehetséges
     * (amire majd a parancsok számolásánál lesz szükség), illetve
     * a színét beállítjuk "forestGreen"-re, vagyis hogy építünk rá.
     * Viszont ha a csempére lehet építeni, de emellett "station" típusú is, akkor a színét átállítjuk "sienna"-ra,
     * vagyis hogy arra a station-re építettünk, és inkrementáljuk a stationNumber változót.
     *
     * Hogyha a Level osztályból lekért getStationNumberBuiltOn() getter megegyezik a getStationNumber() getter
     * számával, akkor a játékot megnyertük, ezért beállítjuk az isWon() boolean változó értékét true-ra.
     *
     * Hogyha a csempe típusa "water", akkor a játékszabályoknak megfelelően előlről kezdjük a szintet.
     * Visszaállítjuk a csempék eredeti színeit a setOriginalColor() metódussal, és beállítjuk az eredeti
     * kezdeti koordinátákat a goToStartingPos() metódussal.
     *
     * Ha a megadott szint végtelen típusú, akkor pedig vége a játéknak, és beállítjuk a goToMenu változót
     * true-ra, ami segítségével a LevelsFX osztályban visszanavigálunk a menübe.
     *
     * Ha a lépés nem lehetséges, akkor kiíratjuk a msg változóban eltárolt üzenetet a konzolon.
     * @param direction az irány amelyben lépnie kell a robotunknak, ha az irány nem megfelelő, errort dobunk.
     * @return az isDoable boolean változó, vagyis hogy a lépés lehetséges-e vagy sem.
     */
    @Override
    public boolean go(String direction) {

        levelMatrix = level.getMatrix();
        matrixRow = level.getRow();
        matrixCol = level.getCol();
        isInfinite = level.isInfinite();
        int[] pos = level.getPos();
        int row = pos[0];
        int col = pos[1];

        switch (direction) {
            case "north" -> {
                if (row - 1 >= 0) {
                    if (levelMatrix[row - 1][col].getIsBuildable() && !levelMatrix[row - 1][col].getColor().equals("forestGreen")) {
                        pos[0] = row - 1;
                        level.setPos(pos);
                        isDoable = true;
                        if (levelMatrix[row - 1][col].getType().equals("station")) {
                            levelMatrix[row - 1][col].setColor("sienna");
                            incrementStationNumber();
                            if (level.getStationNumberBuiltOn() == level.getStationNumber()) {
                                level.setIsWon(true);
                            }
                        } else levelMatrix[row - 1][col].setColor("forestGreen");
                    } else if (levelMatrix[row - 1][col].getType().equals("water")) {
                        if(!isInfinite) {
                            setOriginalColor();
                            goToStartingPos();
                            isDoable = false;
                            setReset(true);
                        } else {
                            goToStartingPos();
                            isDoable = false;
                            goToMenu = true;
                        }
                    }else {
                        System.out.println(msg);
                        isDoable = false;
                    }
                } else {
                    System.out.println(msg);
                    isDoable = false;
                }
            }
            case "east" -> {
                if (col + 1 < matrixCol) {
                    if (levelMatrix[row][col + 1].getIsBuildable() && !levelMatrix[row][col + 1].getColor().equals("forestGreen")) {
                        pos[1] = col + 1;
                        level.setPos(pos);
                        isDoable = true;
                        if (levelMatrix[row][col + 1].getType().equals("station")) {
                            incrementStationNumber();
                            levelMatrix[row][col + 1].setColor("sienna");
                            if (level.getStationNumberBuiltOn() == level.getStationNumber()) {
                                level.setIsWon(true);
                            }
                        } else levelMatrix[row][col + 1].setColor("forestGreen");
                    } else if (levelMatrix[row][col + 1].getType().equals("water")) {
                        if(!isInfinite) {
                            setOriginalColor();
                            goToStartingPos();
                            isDoable = false;
                            setReset(true);
                        } else {
                            goToStartingPos();
                            isDoable = false;
                            goToMenu = true;
                        }
                    }else {
                        System.out.println(msg);
                        isDoable = false;
                    }
                } else {
                    System.out.println(msg);
                    isDoable = false;
                }
            }
            case "west" -> {
                if (col - 1 >= 0) {
                    if (levelMatrix[row][col - 1].getIsBuildable() && !levelMatrix[row][col - 1].getColor().equals("forestGreen")) {
                        pos[1] = col - 1;
                        level.setPos(pos);
                        isDoable = true;
                        if (levelMatrix[row][col - 1].getType().equals("station")) {
                            incrementStationNumber();
                            levelMatrix[row][col - 1].setColor("sienna");
                            if (level.getStationNumberBuiltOn() == level.getStationNumber()) {
                                level.setIsWon(true);
                            }
                        } else levelMatrix[row][col - 1].setColor("forestGreen");
                    } else if (levelMatrix[row][col - 1].getType().equals("water")) {
                        if(!isInfinite) {
                            setOriginalColor();
                            goToStartingPos();
                            isDoable = false;
                            setReset(true);
                        } else {
                            goToStartingPos();
                            isDoable = false;
                            goToMenu = true;
                        }
                    } else {
                        System.out.println(msg);
                        isDoable = false;
                    }
                } else {
                    System.out.println(msg);
                    isDoable = false;
                }
            }
            case "south" -> {
                if (row + 1 < level.getRow()) {
                    if (levelMatrix[row + 1][col].getIsBuildable() && !levelMatrix[row + 1][col].getColor().equals("forestGreen")) {
                        pos[0] = row + 1;
                        level.setPos(pos);
                        isDoable = true;
                        if (levelMatrix[row + 1][col].getType().equals("station")) {
                            incrementStationNumber();
                            levelMatrix[row + 1][col].setColor("sienna");
                            if (level.getStationNumberBuiltOn() == level.getStationNumber()) {
                                level.setIsWon(true);
                            }
                        } else levelMatrix[row + 1][col].setColor("forestGreen");
                    } else if (levelMatrix[row + 1][col].getType().equals("water")) {
                        if(!isInfinite) {
                            setOriginalColor();
                            goToStartingPos();
                            isDoable = false;
                            setReset(true);
                        } else {
                            goToStartingPos();
                            isDoable = false;
                            goToMenu = true;
                        }
                    } else {
                        System.out.println(msg);
                        isDoable = false;
                    }
                } else {
                    System.out.println(msg);
                    isDoable = false;
                }
            }
            default -> throw new IllegalArgumentException("Direction must be north/east/west/south");
        }
        return isDoable;
    }

    @Override
    public boolean throwDynamite(String direction) {
        return isDoable = setToBuildable("rock", direction);
    }

    @Override
    public boolean buildBridge(String direction) {
        return isDoable = setToBuildable("water", direction);
    }

    private void incrementStationNumber() {
        level.setStationNumberBuiltOn(level.getStationNumberBuiltOn() + 1);
    }

    public void setOriginalColor() {
        levelMatrix = level.getMatrix();
        matrixRow = level.getRow();
        matrixCol = level.getCol();

        for (int row = 0; row < matrixRow; row++) {
            for (int col = 0; col < matrixCol; col++) {
                if (levelMatrix[row][col].getType().equals("station")) {
                    levelMatrix[row][col].setColor("peru");
                }
                if (levelMatrix[row][col].getType().equals("rock")) {
                    levelMatrix[row][col].setColor("grey");
                }
                if (levelMatrix[row][col].getType().equals("water")) {
                    levelMatrix[row][col].setColor("darkCyan");
                }
                if (levelMatrix[row][col].getType().equals("field")) {
                    levelMatrix[row][col].setColor("lightGreen");
                }
            }
        }
    }

    /**
     * Beállítja az iránynak megfelelő következő csempe isBuildable változóját igazra és odalép
     *
     * @param type a csempe típusa, amit építhetőre akarunk állítani
     * @param direction az irány amelybe lépni szeretnénk
     * @return az isDoable boolean változó, vagyis hogy a lépés lehetséges-e vagy sem.
     */
    private boolean setToBuildable(String type, String direction) {

        levelMatrix = level.getMatrix();
        matrixRow = level.getRow();
        matrixCol = level.getCol();
        int[] pos = level.getPos();

        int row = pos[0];
        int col = pos[1];

        switch (direction) {
            case ("north") -> {
                if (row - 1 >= 0) {
                    if (levelMatrix[row - 1][col].getType().equals(type)) {
                        levelMatrix[row - 1][col].setBuildable(true);
                        isDoable = go(direction);
                    } else {
                        System.out.println(msg);
                        isDoable = false;
                    }
                } else {
                    System.out.println(msg);
                    isDoable = false;
                }
            }
            case "east" -> {
                if (col + 1 < matrixCol) {
                    if (levelMatrix[row][col + 1].getType().equals(type)) {
                        levelMatrix[row][col + 1].setBuildable(true);
                        isDoable = go(direction);
                    } else {
                        System.out.println(msg);
                        isDoable = false;
                    }
                } else {
                    System.out.println(msg);
                    isDoable = false;
                }
            }
            case "west" -> {
                if (col - 1 >= 0) {
                    if (levelMatrix[row][col - 1].getType().equals(type)) {
                        levelMatrix[row][col - 1].setBuildable(true);
                        isDoable = go(direction);
                    } else {
                        System.out.println(msg);
                        isDoable = false;
                    }
                } else {
                    System.out.println(msg);
                    isDoable = false;
                }
            }
            case "south" -> {
                if (row + 1 < level.getRow()) {
                    if (levelMatrix[row + 1][col].getType().equals(type)) {
                        levelMatrix[row + 1][col].setBuildable(true);
                        isDoable = go(direction);
                    } else {
                        System.out.println(msg);
                        isDoable = false;
                    }
                } else {
                    System.out.println(msg);
                    isDoable = false;
                }
            }
            default -> throw new IllegalArgumentException("Direction must be north/east/west/south");
        }
        return isDoable;
    }

}
