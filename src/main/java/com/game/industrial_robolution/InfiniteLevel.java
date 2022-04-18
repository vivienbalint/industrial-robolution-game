package com.game.industrial_robolution;

import java.util.Random;

public class InfiniteLevel {

    private final String[] tileTypes = new String[]{"r", "w", "f", "s"};
    private String[] savedCol;

    public void setSavedCol(String[] savedCol) {
        this.savedCol = savedCol;
    }

    /**
     * Generál egy megadott tagszámú Stringekből álló tömböt.
     * <p>
     * A tömb lehetséges értékeit a tileTypes változó tartalmazza.
     * Pontosan egy "s" karaktert kell tartalmazzon a végső tömb, vagyis
     * generálunk egy random indexet, ami értéke az "s" lesz.
     * Utána végig iterálunk a megadott tagszámon, és feltöltjük a tömböt.
     * Hogyha a getRandomIndex() metódus segítségével generált index a tileTypes-on belül
     * "s" értéket vesz fel, akkor egészen addig generáljon új indexet, amíg mást nem dob.
     * Ez az érték lesz a végső tömb rowCount indexen felvett értéke.
     *
     * @param row megadott tagszám, ilyen hosszú tömböt fogunk generálni
     * @return egy Stringekből álló tömb
     */

    public String[] generateSavedCol(int row) {
        if (row > 5 && row < 11) {
            String[] col = new String[row];
            int randomInt = new Random().nextInt(row);
            col[randomInt] = "s";

            for (int rowCount = 0; rowCount < row; rowCount++) {
                int randomIdx = getRandomIndex(tileTypes);
                if (rowCount == randomInt) {
                    continue;
                }
                while (tileTypes[randomIdx].equals("s")) {
                    randomIdx = getRandomIndex(tileTypes);
                }
                col[rowCount] = tileTypes[randomIdx];
            }
            return col;
        } else throw new IllegalArgumentException("Row must be between 6 and 10.");
    }

    /**
     * Generál egy Tile típusú értékekből álló mátrixot
     * <p>
     * A LevelsFX osztály drawLevel() metódusában generálunk egy tömböt, ami el lesz mentve a savedCol változóban.
     * Ennek a tömbnek az értékeivel meghívjuk a FixedLevels osztály getTile() metódusát, ami visszaad egy Tile típusú csempét,
     * ezeket a csempéket bemásoljuk a végső mátrix bal szélére.
     * Generálunk a generateSavedCol() metódus segítségével egy a mátrix soraival megegyező tömböt.
     * A kapott csempéket bemásoljuk a végső mátrix jobb szélére.
     * A mátrix további celláiba random generálunk további csempéket, figyelembe véve, hogy
     * a "station" típusú csempék száma ne haladja meg a stationNumber paraméter által megadott számot.
     * A mátrix jobb oldalára generált tömböt beállítjuk a savedCol változó helyére.
     *
     * @param row           a mátrix sorainak száma
     * @param col           a mátrix oszlopainak száma
     * @param stationNumber hány "station" típusú csempét tartalmazzon
     * @return egy Tile típusú értékekből álló random mátrix
     */

    public Tile[][] generateMatrix(int row, int col, int stationNumber) {
        FixedLevels fixedLevels = new FixedLevels();
        int currentStationNumber = 2;
        Tile[][] matrix = new Tile[row][col];
        int idx1 = 0;
        int idx2 = 0;
        String[] rightCol = generateSavedCol(row);

        for (int rowCount = 0; rowCount < row; rowCount++) {
            for (int colCount = 0; colCount < col; colCount++) {
                String randomTileType = tileTypes[getRandomIndex(tileTypes)];
                if (colCount == 0) {
                    matrix[rowCount][colCount] = fixedLevels.getTile(savedCol[idx1]);
                    idx1++;
                } else if (colCount == col - 1) {
                    matrix[rowCount][colCount] = fixedLevels.getTile(rightCol[idx2]);
                    idx2++;
                } else if (randomTileType.equals("s") && currentStationNumber < stationNumber) {
                    matrix[rowCount][colCount] = fixedLevels.getTile(randomTileType);
                    currentStationNumber++;
                } else if (!randomTileType.equals("s")) {
                    matrix[rowCount][colCount] = fixedLevels.getTile(randomTileType);
                } else {
                    while (randomTileType.equals("s")) {
                        randomTileType = tileTypes[getRandomIndex(tileTypes)];
                    }
                    matrix[rowCount][colCount] = fixedLevels.getTile(randomTileType);
                }
            }
        }
        setSavedCol(rightCol);

        return matrix;
    }

    private int getRandomIndex(String[] array) {
        return new Random().nextInt(array.length);
    }
}
