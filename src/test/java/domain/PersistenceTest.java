package domain;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class PersistenceTest {

    @TempDir
    Path tempDir;

    @Test
    void highScoreFormattingWorks() {
        HighScore hs = new HighScore("Player1", "Warrior", 500, 4);
        assertEquals("Player1;Warrior;500;4", hs.toFileLine());
        assertEquals("Player1 (Warrior) - 500 pts (Wave 4)", hs.toDisplayString());
    }

    @Test
    void highScoreManagerSavesSortsAndLimits() throws ScoreStorageException {
        Path file = tempDir.resolve("scores.txt");
        HighScoreManager manager = new HighScoreManager(file.toString());
        assertTrue(manager.loadTopScores(5).isEmpty());
        manager.saveScore(new HighScore("Low", "Medic", 100, 1));
        manager.saveScore(new HighScore("High", "Warrior", 500, 5));
        manager.saveScore(new HighScore("Mid", "Archer", 300, 3));
        List<HighScore> top2 = manager.loadTopScores(2);
        assertEquals(2, top2.size());
        assertEquals("High", top2.get(0).getPlayerName());
        assertEquals(500, top2.get(0).getScore());
        assertEquals("Mid", top2.get(1).getPlayerName());
    }

    @Test
    void highScoreManagerRejectsInvalidDataAndCorruptedFile() throws IOException {
        Path file = tempDir.resolve("bad_scores.txt");
        HighScoreManager manager = new HighScoreManager(file.toString());

        assertThrows(ScoreStorageException.class, () -> manager.saveScore(null));
        assertThrows(ScoreStorageException.class,
                () -> manager.saveScore(new HighScore("Bad", "Warrior", -10, 1)));

        Files.writeString(file, "Bad;Warrior;not_a_number;1\n");
        assertThrows(ScoreStorageException.class, () -> manager.loadTopScores(5));
    }

    @Test
    void saveManagerSavesAndLoadsGameState() throws IOException {
        Path file = tempDir.resolve("save.txt");
        SaveManager manager = new SaveManager(file.toString());
        assertFalse(manager.hasSaveFile());
        assertNull(manager.loadGame());
        Archer archer = new Archer("Robin");
        archer.takeDamage(50);
        manager.saveGame(archer, 3, 420, 1.5);
        assertTrue(manager.hasSaveFile());
        SaveManager.SaveData loaded = manager.loadGame();
        assertNotNull(loaded);
        assertEquals("Robin", loaded.heroName);
        assertEquals("Archer", loaded.heroClass);
        assertEquals(125, loaded.currentHP);
        assertEquals(3, loaded.wave);
        assertEquals(420, loaded.score);
        assertEquals(1.5, loaded.damageMultiplier, 0.001);
        assertInstanceOf(Archer.class, loaded.createFreshHero());
    }

    @Test
    void saveManagerThrowsOnCorruptedSaveFile() throws IOException {
        Path file = tempDir.resolve("corrupted.txt");
        Files.writeString(file, "only;two;parts");
        SaveManager manager = new SaveManager(file.toString());
        assertThrows(IOException.class, manager::loadGame);
    }
}