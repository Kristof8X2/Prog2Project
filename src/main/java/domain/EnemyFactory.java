package domain;

import java.awt.Color;
import java.util.Random;

public class EnemyFactory {
    public enum EnemyType {
        GOBLIN, SKELETON, ORC, BOSS
    }

    private static final Random random = new Random();

    public static Enemy createEnemy(EnemyType type, int wave, int x, int y) {
        switch (type) {
            case ORC:
                return new Enemy("Orc", 100 + (wave * 15), x, y, 1, 20, Color.ORANGE);
            case SKELETON:
                return new Enemy("Skeleton", 30 + (wave * 5), x, y, 4, 5, Color.MAGENTA);
            case BOSS:
                return new Enemy("BOSS", 250 + (wave * 20), x, y, 2, 25, Color.YELLOW);
            case GOBLIN:
            default:
                return new Enemy("Goblin", 50 + (wave * 10), x, y, 2, 10, Color.BLACK);
        }
    }
    public static Enemy createRandomEnemy(int wave, int x, int y) {
        int roll = random.nextInt(100);
        if (wave >= 3 && roll < 25) {
            return createEnemy(EnemyType.ORC, wave, x, y);
        } else if (wave >= 2 && roll < 55) {
            return createEnemy(EnemyType.SKELETON, wave, x, y);
        } else {
            return createEnemy(EnemyType.GOBLIN, wave, x, y);
        }
    }
}