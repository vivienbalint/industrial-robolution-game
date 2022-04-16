package com.ui.industrial_robolution;

import com.game.industrial_robolution.*;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Random;

public class LevelsFX {
    private GridPane levelPane;
    private Level currentLevel;
    private Tile[][] currentMatrix;

    private LinkedHashMap<String, Integer> commandCount = new LinkedHashMap<String, Integer>() {{
        put("north", 0);
        put("east", 0);
        put("west", 0);
        put("south", 0);
        put("dynamite", 0);
        put("bridge", 0);
    }};

    private ArrayList<String> actionsInLoop;
    private String[] directions = new String[]{"north", "east", "west", "south"};
    private int[][] loopCount;
    private Label commandCountLabel;
    private String currentLevelDifficulty;

    private YouWonFX wonPage = new YouWonFX();
    private MenuFX menu = new MenuFX();
    private InfiniteLevel infiniteLevel = new InfiniteLevel();
    private CustomLevelList customLevelList = new CustomLevelList();
    private Robot robot;

    private int timeToUse;
    private int timeToLoop;
    private int loopTimeToUse;

    private boolean dynamiteIsClicked = false;
    private boolean bridgeIsClicked = false;

    private boolean isStopBtnClicked = false;
    private boolean isLoopBtnClicked = false;

    private boolean isInfinite;

    /**
     * Elkészíti a kiválasztott szintet
     *
     * A nehézségi szint alapján létrehozzuk a pályát, és elmentjük a Level típusú currentLevel változóba
     *
     * A fix pályák esetén meghívjuk a korábban létrehozott pályákat a FixedLevels osztály getterei segítégével.
     * A végtelen pálya esetében generálunk egy oszlopot az InfiniteLevel oszály generateSavedCol metódusa
     * segítségevel, és elmentjük azt a setterrel.
     * Utána csinálunk egy új pályát, amihez a generateMatrix() segítségével generálunk egy mátrixot, és
     * beállítjuk a Level osztály isInfinite boolean változóját
     *
     * Az egyedi pályák esetén megkeressük a savedCustomLevels listában a paraméterként megadott indexen
     * található szintet és elmentjük a currentLevel változóban.
     * Ha nincs ilyen szint, errort dobunk.
     *
     * @param levelDifficulty a választott szintnehézség
     * @param savedCustomLevelIndex az egyedi szintek esetében az elmentett szintek listájában az adott szint indexe
     */
    public void drawLevel(String levelDifficulty, int savedCustomLevelIndex) {

        //****** Layout ******

        levelPane = new GridPane();
        levelPane.setAlignment(Pos.CENTER);
        levelPane.setStyle("-fx-background-color: #343A40");

        //****** Get matrix ******


        switch (levelDifficulty) {
            case "novice" -> currentLevel = FixedLevels.getNoviceLevel();
            case "adept" -> currentLevel = FixedLevels.getAdeptLevel();
            case "expert" -> currentLevel = FixedLevels.getExpertLevel();
            case "master" -> currentLevel = FixedLevels.getMasterLevel();
            case "infinite" -> {
                infiniteLevel.setSavedCol(infiniteLevel.generateSavedCol(9));
                currentLevel = new Level(9, 9, 4, infiniteLevel.generateMatrix(9, 9, 4));
                currentLevel.setInfinite(true);
            }
            case "custom" -> {
                ArrayList<CustomLevel> savedLevels = customLevelList.getSavedCustomLevels();
                if (savedCustomLevelIndex > 0 && savedCustomLevelIndex <= savedLevels.size()) {
                    for (int idx = 0; idx < 5; idx++) {
                        if (savedCustomLevelIndex - 1 == idx) {
                            currentLevel = savedLevels.get(idx);
                        }
                    }
                } else throw new IllegalArgumentException("There is no level with this number!");
            }
            default -> throw new IllegalArgumentException("Level difficulty can only be novice/adept/expert/master/infinite/custom.");
        }

        currentMatrix = currentLevel.getMatrix();

        robot = new Robot(currentLevel);
        robot.goToStartingPos();

        setOriginalCommandCount(levelDifficulty);

        //****** Draw matrix ******

        drawMatrix(currentMatrix);

        //****** Add commands ******

        drawCommands();

        currentLevelDifficulty = levelDifficulty;
    }

    /**
     * Megrajzolja és a GridPane típusú levelPane-hez adja a megadott mátrixot
     *
     * @param matrix egy Tile típusú csempékből álló mátrix
     */
    private void drawMatrix(Tile[][] matrix) {
        for (int rowCounter = 0; rowCounter < currentLevel.getRow(); rowCounter++) {
            for (int colCounter = 0; colCounter < currentLevel.getCol(); colCounter++) {
                Rectangle tile = new Rectangle(80, 80);
                tile.setArcWidth(20);
                tile.setArcHeight(20);
                tile.setStroke(Color.BLACK);
                levelPane.add(tile, colCounter + 4, rowCounter);
            }
        }
        colorMatrix(matrix);
        drawMenuBtn();
    }

    /**
     * Megrajzolja a menüre visszanavigáló gombot, és megnyomásakor visszaállítja az eredeti szintet
     */
    private void drawMenuBtn() {
        Button menuBtn = new Button("MENU");
        menuBtn.setFont(Font.font("Agency FB", 30));
        menuBtn.setStyle("-fx-background-color: #73688b; -fx-text-fill: #eeeaa9");
        levelPane.add(menuBtn, 2, 5);

        menuBtn.setOnAction(e -> {
            robot.setOriginalColor();
            robot.goToStartingPos();
            menuBtn.getScene().setRoot(menu.getRootPane());
        });
    }

    /**
     * A megadott koordináták alapján megkeresi a GridPane-ben a Rectangle típusú gyerekét, amint létezik
     *
     * @param row sorszám
     * @param col oszlopszám
     * @return az adott koordinátán található Rectangle, ha létezik, ellenkező esetben null
     */
    private Rectangle getNodeByPos(int row, int col) {
        for (Node node : levelPane.getChildren()) {
            if (GridPane.getRowIndex(node) == row && GridPane.getColumnIndex(node) == col && node instanceof Rectangle) {
                return (Rectangle) node;
            }
        }
        return null;
    }

    /**
     * Kiszínezi a megadott mátrixot a Tile-ok getColor() metódusa segítségével
     *
     * @param matrix egy Tile típusú objectekből álló mátrix
     */
    private void colorMatrix(Tile[][] matrix) {
        for (int rowCounter = 0; rowCounter < currentLevel.getRow(); rowCounter++) {
            for (int colCounter = 0; colCounter < currentLevel.getCol(); colCounter++) {
                Rectangle node = getNodeByPos(rowCounter, colCounter + 4);
                String color = matrix[rowCounter][colCounter].getColor();
                node.setFill(Color.valueOf(color));
            }
        }
    }

    /**
     * Megrajzolja a parancsokhoz tartozó gombokat és címkéket
     */
    private void drawCommands() {
        CommandsFX commandsFX = new CommandsFX();
        int row = 0;
        for (String key : commandCount.keySet()) {
            commandCountLabel = new Label();
            commandCountLabel.setFont(Font.font("Agency FB", 18));
            commandCountLabel.setStyle("-fx-text-fill: #eeeaa9");
            commandCountLabel.setPadding(new Insets(0, 40, 0, 0));

            StackPane commandBtn = commandsFX.getCommandBtn(key);

            levelPane.add(commandBtn, 0, row);
            commandCountLabel.setText(commandCount.get(key) + " x");
            levelPane.add(commandCountLabel, 1, row);
            row++;

            bindActionToCommandBtn(key, commandBtn, commandCountLabel);

        }
        row = 0;

        for (int arrayCount = 0; arrayCount < loopCount.length; arrayCount++) {
            commandCountLabel = new Label();
            commandCountLabel.setFont(Font.font("Agency FB", 18));
            commandCountLabel.setStyle("-fx-text-fill: #eeeaa9");
            commandCountLabel.setPadding(new Insets(0, 40, 0, 0));

            StackPane loopBtn = commandsFX.getLoopBtn(loopCount[arrayCount][1]);

            levelPane.add(loopBtn, 2, row);
            commandCountLabel.setText(loopCount[arrayCount][0] + " x");
            levelPane.add(commandCountLabel, 3, row++);

            int currentBtnTimeToLoop = loopCount[arrayCount][1];

            bindActionToLoopBtn(loopBtn, commandCountLabel, currentBtnTimeToLoop);
        }
        StackPane stopBtn = commandsFX.getCommandBtn("stop");
        levelPane.add(stopBtn, 2, row++);

        bindActionToStopBtn(stopBtn);
    }

    /**
     * A parancsgombokhoz hozzárendeli a tényleges végrehajtható parancsokat
     *
     * Ha egy irányra rákattintunk, és előtte nem kattintottunk sem a dinamit, sem a híd, sem az ismétlés parancsra,
     * akkor a timeToUse segéd változóban elmentjük a commandCount-ban eltárolt adott parancshoz tartozó
     * végrehajtható számot.
     * Ha ez a szám nagyobb nullánál, akkor használhatjuk a parancsot, ellenkező esetben nem történik semmi.
     * Ezután ha a parancs végrehajtható, meghívjuk a Robot osztályból az adott irányra vonatkozó paraméterrel
     * ellátott go() parancsot, és annak a visszatérési értékétől függően haladunk tovább.
     * Ha a visszatérési értéke (a boolean isDoable változó) igaz, vagyis a parancs sikeresen végrehajtható,
     * akkor a parancs lehetséges végrehajtási számából (a timeToUse változó) kivonunk eggyet,
     * kicseréljük ezt a számot a commandCount változóban is, és a parancshoz tartozó címkét is frissítjük.
     *
     * Ha a parancs végrehajtása után a getIsWon() getter lekérésével megkapott boolean érték igaz, vagyis
     * érintettünk a szinten minden "station" típusú csempét, akkor, ha a szintünk nem végtelen szint,
     * átnavigálunk a YouWonFX osztályból meghívott oldalra, a játékot sikeresen megnyertük.
     *
     * Ha a szint végtelen típusú, akkor viszont generálunk egy új mátrixot a setNewInfiniteMatrix()
     * metódus segítségével, és a megmaradt parancsok számához generálunk még újakat az addNewRandomCommands()
     * metódus segítségével.
     *
     * Hogyha a parancs nem végrehajtható (az isDoable változó értéke false), akkor ha "water" típusú csempére
     * sikerült lépnünk és a szint nem végtelen típusú, akkor a játékszabályoknak megfelelően újra kezdjük
     * a pályát.
     * Ha "water" típusú csempére léptünk és a szint végtelen típusú, akkor vége a játéknak, visszanavigálunk
     * a menübe.
     *
     *
     * Hogyha a dinamit, vagy a híd parancsra kattintottunk, de előtte nem kattintottunk egyik ismétlés
     * parancsra sem, akkor, ha a lehetséges végrehajtási száma nagyobb, mint nulla, beállítjuk az adott
     * parancshoz tartozó boolean változót igazra, és kivonunk eggyet a végrehajtási számából, és frissítjük
     * a címkét.
     *
     * Ha valamelyik irányra kattintottunk, és előtte a dinamit vagy a híd is meg lett nyomva, akkor
     * az adott parancshoz tartozó boolean változó értéket hamisra állítjuk, és végrehajtjuk a parancsot
     * a go() metódus helyett a throwDynamite() vagy a buildBridge() metódussal.
     *
     * Ha bármelyik parancs gombra úgy kattintottunk, hogy előtte az ismétlés parancsot benyomtuk, akkor
     * az adott parancsot hozzáadjuk az actionInLoop Stringekből álló ArrayList típusú változóhoz.
     *
     * @param command egy parancs
     * @param commandBtn egy gombként funkcionáló StackPane az adott parancshoz
     * @param commandCountLabel az adott parancshoz tartozó címke
     */
    private void bindActionToCommandBtn(String command, StackPane commandBtn, Label commandCountLabel) {

        robot = new Robot(currentLevel);
        isInfinite = currentLevel.isInfinite();

        for (String direction : directions) {
            if (command.equals(direction)) {
                commandBtn.setOnMouseClicked(e -> {
                    {
                        if (!dynamiteIsClicked && !bridgeIsClicked) {
                            if (!isLoopBtnClicked) {
                                timeToUse = commandCount.get(direction);
                                if (timeToUse > 0) {
                                    boolean isDoable = robot.go(direction);
                                    if (isDoable) {
                                        commandCount.replace(direction, timeToUse - 1);
                                        commandCountLabel.setText(commandCount.get(direction) + " x");
                                        if (currentLevel.getIsWon() && !isInfinite) {
                                            commandBtn.getScene().setRoot(wonPage.getWonPane());
                                        } else if (currentLevel.getIsWon() && isInfinite) {
                                            setNewInfiniteMatrix();
                                            addNewRandomCommands();
                                        }
                                    } else if (robot.getIsReset() && !isInfinite) {
                                        setOriginalCommandCount(currentLevelDifficulty);
                                        commandCountLabel.setText(commandCount.get(direction) + " x");
                                    } else if (robot.isGoToMenu()) {
                                        commandBtn.getScene().setRoot(menu.getRootPane());
                                    }
                                }
                            } else {
                                actionsInLoop.add(direction);
                            }
                        }
                    }

                    if (dynamiteIsClicked) {
                        if (!isLoopBtnClicked) {
                            dynamiteIsClicked = false;
                            timeToUse = commandCount.get("dynamite");
                            if (timeToUse >= 0) {
                                boolean isDoable = robot.throwDynamite(direction);
                                if (isDoable) {
                                    int timetoUseDirection = commandCount.get(direction);
                                    commandCount.replace(direction, timetoUseDirection - 1);
                                    commandCountLabel.setText(commandCount.get(direction) + " x");
                                } else if (robot.getIsReset() && !isInfinite) {
                                    setOriginalCommandCount(currentLevelDifficulty);
                                    commandCountLabel.setText(commandCount.get(direction) + " x");
                                } else if (robot.isGoToMenu()) {
                                    commandBtn.getScene().setRoot(menu.getRootPane());
                                }
                            }
                        } else actionsInLoop.add(direction);
                    }

                    if (bridgeIsClicked) {
                        if (!isLoopBtnClicked) {
                            bridgeIsClicked = false;
                            timeToUse = commandCount.get("bridge");
                            if (timeToUse >= 0) {
                                boolean isDoable = robot.buildBridge(direction);
                                if (isDoable) {
                                    int timetoUseDirection = commandCount.get(direction);
                                    commandCount.replace(direction, timetoUseDirection - 1);
                                    commandCountLabel.setText(commandCount.get(direction) + " x");
                                } else if (robot.getIsReset() && !isInfinite) {
                                    setOriginalCommandCount(currentLevelDifficulty);
                                    commandCountLabel.setText(commandCount.get(direction) + " x");
                                } else if (robot.isGoToMenu()) {
                                    commandBtn.getScene().setRoot(menu.getRootPane());
                                }
                            }
                        } else actionsInLoop.add(direction);
                    }
                    colorMatrix(currentMatrix);
                });
            }
            ;
        }
        if (command.equals("dynamite")) {
            commandBtn.setOnMouseClicked(e -> {
                if (!dynamiteIsClicked) {
                    if (!isLoopBtnClicked) {
                        int timeToUse = commandCount.get("dynamite");
                        if (timeToUse > 0) {
                            dynamiteIsClicked = true;
                            commandCount.replace("dynamite", timeToUse - 1);
                            commandCountLabel.setText(commandCount.get("dynamite") + " x");
                        }
                    } else actionsInLoop.add("dynamite");
                } else dynamiteIsClicked = false;
            });
        }
        if (command.equals("bridge")) {
            commandBtn.setOnMouseClicked(e -> {
                if (!bridgeIsClicked) {
                    if (!isLoopBtnClicked) {
                        int timeToUse = commandCount.get("bridge");
                        if (timeToUse > 0) {
                            bridgeIsClicked = true;
                            commandCount.replace("bridge", timeToUse - 1);
                            commandCountLabel.setText(commandCount.get("bridge") + " x");
                        }
                    } else actionsInLoop.add("bridge");
                } else bridgeIsClicked = false;
            });
        }
    }

    /**
     * Az ismétlés parancshoz tartozó gombokhoz rendeli a parancsot
     *
     * Attól függően melyik ismétlés gombra kattintunk, elmentjük a végrehajtási számát a loopTimeToUse segédváltozóban.
     * Ha a végrehajtási száma nagyobb, mint nulla, akkor csinálnunk egy új Strinekből állő ArrayListet az actionsInLoop
     * változóban.
     * Az ismétlés parancshoz tartozó boolean változó értékét igazra állítjuk, a paraméterként megadott ismétlés számát
     * elmentjük a timeToLoop segédváltozóban, a loopTimeToUse értékét csökkentjük eggyel.
     * A loopCountban, az adott parancshoz tartozó végrehajtási értéket frissítjük a loopTimeToUse változó értékével,
     * frissítjük a hozzátartozó címkét is.
     *
     * @param loopBtn egy gombként funkcionáló StackPane az adott ismétlés parancshoz
     * @param commandCountLabel a parancshoz tartozó végrehajtási érték címkéje
     * @param currentBtnTimeToLoop az adott ismétléshez tartozó ismétlés száma
     */
    private void bindActionToLoopBtn(StackPane loopBtn, Label commandCountLabel, int currentBtnTimeToLoop) {

        loopBtn.setOnMouseClicked(e -> {
            for (int idx = 0; idx < loopCount.length; idx++) {
                if (loopCount[idx][1] == currentBtnTimeToLoop) {
                    loopTimeToUse = loopCount[idx][0];
                }
            }
            if (loopTimeToUse > 0) {
                actionsInLoop = new ArrayList<>();
                isLoopBtnClicked = true;
                timeToLoop = currentBtnTimeToLoop;
                loopTimeToUse--;

                for (int idx = 0; idx < loopCount.length; idx++) {
                    if (loopCount[idx][1] == currentBtnTimeToLoop) {
                        loopCount[idx][0] = loopTimeToUse;
                    }
                }
                commandCountLabel.setText(loopTimeToUse + " x");
            }
        });
    }

    private void bindActionToStopBtn(StackPane stopBtn) {
        robot = new Robot(currentLevel);

        stopBtn.setOnMouseClicked(e -> {
            isStopBtnClicked = true;
            int currentLoop = 0;
            boolean loopHasRun = false;
            boolean commandHasRun = false;
            if (isLoopBtnClicked) {
                isLoopBtnClicked = false;
                while (currentLoop < timeToLoop) {
                    for (int idx = 0; idx < actionsInLoop.size(); idx++) {
                        if (idx > 0 || actionsInLoop.get(idx).equals("dynamite") || actionsInLoop.get(idx).equals("bridge")) {
                            for (String direction : directions) {
                                if (actionsInLoop.get(idx).equals(direction) && !actionsInLoop.get(idx - 1).equals("dynamite") && !actionsInLoop.get(idx - 1).equals("bridge")) {
                                    if (commandCount.get(direction) > 0 && !loopHasRun) {
                                        boolean isDoable = robot.go(direction);
                                        if (isDoable) {
                                            if (!commandHasRun) {
                                                commandCount.replace(direction, commandCount.get(direction) - 1);
                                                commandHasRun = true;
                                                loopHasRun = true;
                                            }
                                            if (currentLevel.getIsWon() && !isInfinite) {
                                                stopBtn.getScene().setRoot(wonPage.getWonPane());
                                            } else if (currentLevel.getIsWon() && isInfinite) {
                                                setNewInfiniteMatrix();
                                                addNewRandomCommands();
                                            }
                                        } else if (robot.isGoToMenu()) {
                                            stopBtn.getScene().setRoot(menu.getRootPane());
                                        } else if (robot.getIsReset() && !isInfinite) {
                                            setOriginalCommandCount(currentLevelDifficulty);
                                        }
                                    } else if (loopHasRun) {
                                        robot.go(direction);
                                    }
                                }
                            }
                            if (actionsInLoop.get(idx).equals("dynamite")) {
                                if (commandCount.get("dynamite") > 0 && commandCount.get(actionsInLoop.get(idx + 1)) > 0 && !loopHasRun) {
                                    boolean isDoable = robot.throwDynamite(actionsInLoop.get(idx + 1));
                                    if (isDoable && !commandHasRun) {
                                        commandCount.replace("dynamite", commandCount.get("dynamite") - 1);
                                        commandCount.replace(actionsInLoop.get(idx + 1), (commandCount.get(actionsInLoop.get(idx + 1)) - 1));
                                        commandHasRun = true;
                                        loopHasRun = true;
                                    } else if (robot.isGoToMenu()) {
                                        stopBtn.getScene().setRoot(menu.getRootPane());
                                    } else if (robot.getIsReset() && !isInfinite) {
                                        setOriginalCommandCount(currentLevelDifficulty);
                                    }
                                } else if (loopHasRun) {
                                    robot.throwDynamite(actionsInLoop.get(idx + 1));
                                }
                            }
                            if (actionsInLoop.get(idx).equals("bridge")) {
                                if (commandCount.get("bridge") > 0 && commandCount.get(actionsInLoop.get(idx + 1)) > 0 && !loopHasRun) {
                                    boolean isDoable = robot.buildBridge(actionsInLoop.get(idx + 1));
                                    if (isDoable && !commandHasRun) {
                                        commandCount.replace("bridge", commandCount.get("bridge") - 1);
                                        commandCount.replace(actionsInLoop.get(idx + 1), commandCount.get(actionsInLoop.get(idx + 1)) - 1);
                                        commandHasRun = true;
                                        loopHasRun = true;
                                    } else if (robot.isGoToMenu()) {
                                        stopBtn.getScene().setRoot(menu.getRootPane());
                                    } else if (robot.getIsReset() && !isInfinite) {
                                        setOriginalCommandCount(currentLevelDifficulty);
                                    }
                                } else if (loopHasRun) {
                                    robot.buildBridge(actionsInLoop.get(idx + 1));
                                }
                            }
                        } else {
                            for (String direction : directions) {
                                if (actionsInLoop.get(idx).equals(direction)) {
                                    if (commandCount.get(direction) > 0 && !loopHasRun) {
                                        boolean isDoable = robot.go(direction);
                                        if (isDoable) {
                                            if (!commandHasRun) {
                                                commandCount.replace(direction, commandCount.get(direction) - 1);
                                                commandHasRun = true;
                                                loopHasRun = true;
                                            }
                                            if (currentLevel.getIsWon() && !isInfinite) {
                                                stopBtn.getScene().setRoot(wonPage.getWonPane());
                                            } else if (currentLevel.getIsWon() && isInfinite) {
                                                setNewInfiniteMatrix();
                                                addNewRandomCommands();
                                            }
                                        } else if (robot.isGoToMenu()) {
                                            stopBtn.getScene().setRoot(menu.getRootPane());
                                        } else if (robot.getIsReset() && !isInfinite) {
                                            setOriginalCommandCount(currentLevelDifficulty);
                                        }
                                    } else if (loopHasRun) {
                                        robot.go(direction);
                                    }
                                }
                            }
                        }
                    }
                    colorMatrix(currentMatrix);
                    currentLoop++;
                }
            }
        });
    }

    private void setOriginalCommandCount(String levelDifficulty) {
        switch (levelDifficulty) {
            case "novice" -> {

                commandCount.replace("north", 4);
                commandCount.replace("east", 2);
                commandCount.replace("west", 3);
                commandCount.replace("south", 4);
                commandCount.replace("dynamite", 2);

                loopCount = new int[][]{{1, 2}, {1, 3}};

            }
            case "adept" -> {

                commandCount.replace("east", 4);
                commandCount.replace("west", 2);
                commandCount.replace("south", 4);
                commandCount.replace("dynamite", 2);
                commandCount.replace("bridge", 1);

                loopCount = new int[][]{{1, 2}, {1, 3}};

            }
            case "expert" -> {

                commandCount.replace("east", 4);
                commandCount.replace("west", 2);
                commandCount.replace("south", 4);
                commandCount.replace("dynamite", 2);
                commandCount.replace("bridge", 1);

                loopCount = new int[][]{{1, 2}, {1, 3}};

            }
            case "master" -> {

                commandCount.replace("east", 10);
                commandCount.replace("west", 10);
                commandCount.replace("south", 10);
                commandCount.replace("dynamite", 10);
                commandCount.replace("bridge", 10);

                loopCount = new int[][]{{10, 2}, {10, 3}};

            }
            case "infinite" -> {
                int maxRandomInt = 15;
                for (String direction : directions) {
                    commandCount.replace(direction, new Random().nextInt(maxRandomInt));
                }
                commandCount.replace("dynamite", new Random().nextInt(maxRandomInt));
                commandCount.replace("bridge", new Random().nextInt(maxRandomInt));

                loopCount = new int[][]{{new Random().nextInt(maxRandomInt), 2}, {new Random().nextInt(maxRandomInt), 3}, {new Random().nextInt(maxRandomInt), 4}};
            }
            case "custom" -> {
                commandCount = ((CustomLevel) currentLevel).getCommandCount();
                loopCount = ((CustomLevel) currentLevel).getLoopCount();
            }
            default -> throw new IllegalArgumentException("Level difficulty can only be novice/adept/expert/master/custom.");
        }
    }

    private void setNewInfiniteMatrix() {
        currentLevel.setMatrix(infiniteLevel.generateMatrix(currentLevel.getRow(), currentLevel.getCol(), currentLevel.getStationNumber()));
        currentMatrix = currentLevel.getMatrix();
        robot.goToStartingPos();
        currentLevel.setIsWon(false);
        colorMatrix(currentMatrix);
    }

    private void addNewRandomCommands() {

        int maxRandomInt = 7;

        for (String direction : directions) {
            commandCount.replace(direction, commandCount.get(direction) + new Random().nextInt(maxRandomInt));
        }

        commandCount.replace("dynamite", commandCount.get("dynamite") + new Random().nextInt(maxRandomInt));
        commandCount.replace("bridge", commandCount.get("bridge") + new Random().nextInt(maxRandomInt));

        for (int idx = 0; idx < loopCount.length; idx++) {
            loopCount[idx][0] += new Random().nextInt(maxRandomInt);
        }
    }

    public GridPane getLevelPane() {
        return levelPane;
    }
}
