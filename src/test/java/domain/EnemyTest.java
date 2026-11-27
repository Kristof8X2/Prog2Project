package domain;

import org.junit.jupiter.api.Test;
import java.awt.Color;
import static org.junit.jupiter.api.Assertions.*;

class EnemyTest {

    @Test
    void enemyFactoryScalesHpWithWave() {
        Enemy goblin = EnemyFactory.createEnemy(EnemyFactory.EnemyType.GOBLIN, 2, 10, 20);
        assertEquals("Goblin", goblin.getName());
        assertEquals(70, goblin.getMaxHP());
        assertEquals(10, goblin.getDamage());
        assertEquals(Color.BLACK, goblin.getColor());
        Enemy skeleton = EnemyFactory.createEnemy(EnemyFactory.EnemyType.SKELETON, 2, 0, 0);
        assertEquals(40, skeleton.getMaxHP());
        Enemy orc = EnemyFactory.createEnemy(EnemyFactory.EnemyType.ORC, 3, 0, 0);
        assertEquals(145, orc.getMaxHP());
        Enemy boss = EnemyFactory.createEnemy(EnemyFactory.EnemyType.BOSS, 5, 50, 60);
        assertEquals("BOSS", boss.getName());
        assertEquals(350, boss.getMaxHP());
        assertEquals(25, boss.getDamage());
    }

    @Test
    void waveOneRandomEnemyIsAlwaysGoblin() {
        Enemy enemy = EnemyFactory.createRandomEnemy(1, 15, 25);
        assertEquals("Goblin", enemy.getName());
        assertEquals(15, enemy.getX());
        assertEquals(25, enemy.getY());
    }

    @Test
    void enemyDamageAndAliveStatus() {
        Enemy enemy = EnemyFactory.createEnemy(EnemyFactory.EnemyType.GOBLIN, 1, 0, 0);
        assertTrue(enemy.isAlive());
        enemy.takeDamage(20);
        assertEquals(40, enemy.getCurrentHP());
        assertTrue(enemy.isAlive());
        enemy.takeDamage(100);
        assertEquals(0, enemy.getCurrentHP());
        assertFalse(enemy.isAlive());
    }

    @Test
    void enemyMovesTowardsTarget() {
        Enemy enemy = new Enemy("Test", 50, 10, 10, 2, 5, Color.BLACK);
        enemy.moveTowards(100, 100);
        assertEquals(12, enemy.getX());
        assertEquals(12, enemy.getY());
        enemy.moveTowards(0, 0);
        assertEquals(10, enemy.getX());
        assertEquals(10, enemy.getY());
    }
}