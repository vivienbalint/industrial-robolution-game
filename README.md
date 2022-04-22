Indítás
====
Szükséges hozzá a legújabb JavaFX sdk.

A futtatáshoz be kell állítani a VM optionst:

* Run
* Edit configurations
* Modify options 
* Add VM options
* Bemásolni:
```bash
--module-path "javafx sdk/lib mappa útvonala" --add-modules javafx.controls
```
* Save configurations
* Main függvény indítása

Játékszabályok
===

Négy darab fix nehézségű pálya van, illetve egy végtelen pálya, ahol a
parancsokat random generálja a játék, nincs ellenőrizve, hogy teljesíthető-e a pálya velük.

A mátrixok csempéin kell lépkedni a megadott parancsok segítségével,
cél, hogy kiépítsük a síneket az állomások között.

A különböző csempék színekkel vannak megkülönböztetve egymástól, 
az alábbiak szerint:
* Barna = állomás
* Szürke = szikla
* Kék = víz
* Világoszöld = mező
* Sötétbarna = sínekkel már összekötött állomás
* Sötétzöld = kiépített sínek

A szikla és a víz típusú csempékre nem lehet lépni, csak a
dinamit és a híd paranccsal, amihez ki kell választani egy irányt is.
Ha sziklára próbálunk lépni nem történik semmi, ha viszont vízre, akkor
fix pályák esetén újra kezdjük a pályát, végtelen pálya esetén game over.

Továbbá van még ismétlés parancs is, aminek megnyomásával ki tudunk
választani több parancsot, és a stop gomb megnyomásával ezeket a parancsokat
annyiszor hajtja végre, amilyen szám az ismétlés parancs gombján szerepel.

Nem működő funkció:
-----

ismétlés parancs ismétlés parancson belül.
Ha ismétlés parancs kiadása után újabb ismétlést szeretnénk beágyazni, azt
a parancsot, és az azután kiadott parancsokat a játék figyelmen kívül hagyja,
és csak a beágyazott ismétlés parancs előttieket hajtja végre.

Bug:
----

Az ismétlés parancsban belül kiadott parancsok számához tartozó címke nem
frissül azonnal, csak újboli megnyomásuk után már az ismétlésen kívül.
A szám mögötte attól helyes.

Az ismétlés parancs lehetséges használatainak száma akkor is csökken, ha
a parancsokat benne nem lehet sikeresen végrehajtani.

Windows-on rendben működik a beállított betűtípus, linuxon nálam nem volt jó.
Sajnos egyelőre megoldást sem találtam rá.