package domain;

public class HighScore {
    private String playerName;
    private String heroClass;
    private int score;
    private int wave;

    public HighScore(String playerName, String heroClass, int score, int wave) {
        this.playerName = playerName;
        this.heroClass = heroClass;
        this.score = score;
        this.wave = wave;
    }

    public String getPlayerName() {
        return playerName;
    }

    public String getHeroClass() {
        return heroClass;
    }

    public int getScore() {
        return score;
    }

    public int getWave() {
        return wave;
    }

    public String toFileLine() {
        return playerName + ";" + heroClass + ";" + score + ";" + wave;
    }

    public String toDisplayString() {
        return playerName + " (" + heroClass + ") - " + score + " pts (Wave " + wave + ")";
    }
}