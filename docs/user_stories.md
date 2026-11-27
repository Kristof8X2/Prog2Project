# Felhasználói történetek – RPG Játék (Prog2 Beadandó)

Az alábbi felhasználói történetek foglalják össze a rendszer fő funkcionális követelményeit és azok elfogadási kritériumait.

---

## Karakterkaszt választása
**Mint** játékos, **szeretnék** több különböző hős kaszt (`Warrior`, `Archer`, `Medic`) közül választani a játék elején, **hogy** a saját játékstílusomnak megfelelő képességekkel vághassak neki a harcnak.

**Elfogadási kritériumok:**
- A játék indításakor választható a Harcos (Warrior), Íjász (Archer) és Gyógyító (Medic) kaszt.
- Mindegyik kaszt a közös `Hero` ősosztályból származik, de eltérő alapértékekkel és egyedi speciális képességgel rendelkezik.

---

## Körökre osztott harc és ellenfelek generálása
**Mint** játékos, **szeretnék** egyre erősebb ellenfelekkel megküzdeni, **hogy** a játék folyamatos kihívást nyújtson.

**Elfogadási kritériumok:**
- Az ellenfeleket az `EnemyFactory` (Factory tervezési minta) hozza létre az aktuális szint/hullám alapján.
- A játékos támadhat, használhatja a speciális képességét, az ellenfelek pedig visszatámadnak.
- A harc addig tart, amíg a hős vagy az ellenfél életereje el nem éri a 0-t.

---

## Power-Upok gyűjtése
**Mint** játékos, **szeretnék** a harcok során vagy után bónuszokat (`PowerUp`) kapni, **hogy** növelhessem a hősöm életerejét vagy támadóerejét.

**Elfogadási kritériumok:**
- A `PowerUp` objektumok alkalmazhatók a `Hero` példányra.
- A bónusz hatása (pl. gyógyítás vagy sebzésnövelés) azonnal érvényesül a karakter statisztikáin.

---

## Játékállás mentése és betöltése
**Mint** játékos, **szeretném** elmenteni az aktuális játékállásomat, majd később onnan folytatni, **hogy** ne vesszen el az elért haladásom.

**Elfogadási kritériumok:**
- A `SaveManager` képes a hős állapotát fájlba menteni (`savegame.txt`) és onnan visszaolvasni.
- Hibás vagy hiányzó mentésfájl esetén a program nem omlik össze, hanem kezelten jelzi a hibát.

---

## Toplista (High Score) kezelése
**Mint** játékos, **szeretném** látni a legjobb eredményeket pontszám szerint rendezve, **hogy** összehasonlíthassam a teljesítményemet a korábbi futásokkal.

**Elfogadási kritériumok:**
- A `HighScoreManager` kollekcióban tárolja és pontszám szerint csökkenő sorrendbe rendezi a `HighScore` rekordokat.
- A toplista fájlba menthető és visszatölthető; I/O hiba esetén saját kivétel (`ScoreStorageException`) dobódik.

---

## Konzolos és grafikus (Swing) játékmód
**Mint** játékos, **szeretném** eldönteni, hogy szöveges konzolon vagy grafikus ablakban játszom, **hogy** mindkét felületen kipróbálhassam a játékot.

**Elfogadási kritériumok:**
- A `ConsoleGame` teljes értékű szöveges játékmenetet biztosít.
- A `GamePanel` (Java Swing) grafikus felületen jeleníti meg a játékot és kezeli a felhasználói interakciókat.