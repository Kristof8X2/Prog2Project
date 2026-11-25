package org.example;

import domain.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ConsoleGame {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("=== SURVIVAL ARENA (CONSOLE MODE) ===");
        System.out.print("Enter your name: ");
        String playerName = scanner.nextLine().trim();
        if (playerName.isEmpty()) {
            playerName = "Player";
        }

        System.out.println("Choose your hero: 1 - Warrior | 2 - Archer | 3 - Medic");
        System.out.print("> ");
        int choice = scanner.nextInt();

        Hero player;
        if (choice == 1) {
            player = new Warrior(playerName);
        } else if (choice == 2) {
            player = new Archer(playerName);
        } else if (choice == 3) {
            player = new Medic(playerName);
        } else {
            System.out.println("Invalid choice, defaulting to Warrior.");
            player = new Warrior(playerName);
        }

        int maxHP = player.getCurrentHP();
        int wave = 1;
        int score = 0;

        while (player.getCurrentHP() > 0) {
            System.out.println("\n--- WAVE " + wave + " ---");
            List<Enemy> enemies = new ArrayList<>();
            for (int i = 0; i < wave; i++) {
                enemies.add(EnemyFactory.createRandomEnemy(wave, 0, 0));
            }
            if (wave % 5 == 0) {
                enemies.add(EnemyFactory.createEnemy(EnemyFactory.EnemyType.BOSS, wave, 0, 0));
            }

            for (Enemy enemy : enemies) {
                while (enemy.isAlive() && player.getCurrentHP() > 0) {
                    System.out.println("\nHero: " + player.GetName() + " [" + player.getCurrentHP() + "/" + maxHP + " HP] | Score: " + score);
                    System.out.println("Enemy: " + enemy.getName() + " [" + enemy.getCurrentHP() + " HP, Damage: " + enemy.getDamage() + "]");

                    if (player instanceof Medic) {
                        System.out.print("Action (1 - Attack, 2 - Heal +20): ");
                    } else {
                        System.out.print("Action (1 - Attack): ");
                    }

                    int action = scanner.nextInt();
                    if (action == 2 && player instanceof Medic) {
                        player.heal(20);
                        System.out.println("You healed! Current HP: " + player.getCurrentHP());
                    } else {
                        int dmg = player.attack();
                        enemy.takeDamage(dmg);
                        System.out.println("You dealt " + dmg + " damage!");
                    }

                    if (!enemy.isAlive()) {
                        score += 100;
                        System.out.println(enemy.getName() + " defeated! (+100 pts)");
                    } else {
                        player.takeDamage(enemy.getDamage());
                        System.out.println(enemy.getName() + " struck back for " + enemy.getDamage() + " damage!");
                    }
                }

                if (player.getCurrentHP() <= 0) {
                    break;
                }
            }

            if (player.getCurrentHP() > 0) {
                System.out.print("\nWave " + wave + " cleared! Continue? (1 - Yes, 0 - Exit and save): ");
                if (scanner.nextInt() == 0) {
                    break;
                }
                wave++;
            }
        }

        System.out.println("\n=== GAME OVER | Final Score: " + score + " (Wave " + wave + ") ===");

        HighScoreManager manager = new HighScoreManager();
        try {
            manager.saveScore(new HighScore(player.GetName(), player.getClass().getSimpleName(), score, wave));
            System.out.println("\n--- TOP 5 HIGH SCORES ---");
            List<HighScore> top = manager.loadTopScores(5);
            for (int i = 0; i < top.size(); i++) {
                System.out.println((i + 1) + ". " + top.get(i).toDisplayString());
            }
        } catch (ScoreStorageException e) {
            System.err.println("High score error: " + e.getMessage());
        }

        scanner.close();
    }
}