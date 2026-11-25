package domain;

import java.io.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class HighScoreManager {
    private final String filePath;

    public HighScoreManager() {
        this("highscores.txt");
    }
    public HighScoreManager(String filePath) {
        this.filePath = filePath;
    }
    public void saveScore(HighScore entry) throws ScoreStorageException {
        if (entry == null || entry.getScore() < 0) {
            throw new ScoreStorageException("Invalid score data!");
        }
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath, true))) {
            writer.write(entry.toFileLine());
            writer.newLine();
        } catch (IOException e) {
            throw new ScoreStorageException("Failed to save score to file.", e);
        }
    }
    public List<HighScore> loadTopScores(int limit) throws ScoreStorageException {
        File file = new File(filePath);
        if (!file.exists()) {
            return new ArrayList<>();
        }
        List<HighScore> scores = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(";");
                if (parts.length == 4) {
                    String name = parts[0];
                    String heroClass = parts[1];
                    int score = Integer.parseInt(parts[2]);
                    int wave = Integer.parseInt(parts[3]);
                    scores.add(new HighScore(name, heroClass, score, wave));
                }
            }
        } catch (IOException | NumberFormatException e) {
            throw new ScoreStorageException("Error occurred while reading high scores.", e);
        }

        return scores.stream()
                .sorted(Comparator.comparingInt(HighScore::getScore).reversed())
                .limit(limit)
                .collect(Collectors.toList());
    }
}