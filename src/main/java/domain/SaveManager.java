package domain;

import java.io.*;

public class SaveManager {
    private final String filePath;
    public SaveManager() {
        this("savegame.txt");
    }
    public SaveManager(String filePath) {
        this.filePath = filePath;
    }

    public static class SaveData {
        public final String heroName;
        public final String heroClass;
        public final int currentHP;
        public final int wave;
        public final int score;
        public final double damageMultiplier;

        public SaveData(String heroName, String heroClass, int currentHP, int wave, int score, double damageMultiplier) {
            this.heroName = heroName;
            this.heroClass = heroClass;
            this.currentHP = currentHP;
            this.wave = wave;
            this.score = score;
            this.damageMultiplier = damageMultiplier;
        }
        public Hero createFreshHero() {
            if ("Archer".equals(heroClass)) {
                return new Archer(heroName);
            } else if ("Medic".equals(heroClass)) {
                return new Medic(heroName);
            }
            return new Warrior(heroName);
        }
    }
    public boolean hasSaveFile() {
        File file = new File(filePath);
        return file.exists() && file.length() > 0;
    }
    public void saveGame(Hero player, int wave, int score, double damageMultiplier) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath, false))) {
            writer.write(player.GetName() + ";"
                    + player.getClass().getSimpleName() + ";"
                    + player.getCurrentHP() + ";"
                    + wave + ";"
                    + score + ";"
                    + damageMultiplier);
        }
    }
    public SaveData loadGame() throws IOException {
        File file = new File(filePath);
        if (!file.exists()) {
            return null;
        }
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line = reader.readLine();
            if (line == null || line.isEmpty()) {
                return null;
            }
            String[] parts = line.split(";");
            if (parts.length != 6) {
                throw new IOException("Corrupted save file.");
            }
            return new SaveData(
                    parts[0],
                    parts[1],
                    Integer.parseInt(parts[2]),
                    Integer.parseInt(parts[3]),
                    Integer.parseInt(parts[4]),
                    Double.parseDouble(parts[5])
            );
        } catch (NumberFormatException e) {
            throw new IOException("Invalid number format in save file.", e);
        }
    }
}
