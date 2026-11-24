package org.example;

import domain.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.Random;

public class GamePanel extends JPanel implements KeyListener {
    private Hero player;
    private ArrayList<Enemy> enemies = new ArrayList<>();
    private Random random = new Random();
    private int playerX = 350;
    private int playerY = 250;
    private int wave = 1;
    private int score = 0;
    private int enemyAttackCooldown = 0;
    private int playerAttackCooldown = 0;
    private int healCooldown = 0;
    private JButton restartButton;

    public GamePanel(Hero player) {
        this.player = player;
        spawnWave(wave);
        setFocusable(true);
        addKeyListener(this);
        setBackground(Color.DARK_GRAY);
        setLayout(null);
        restartButton = new JButton("Restart");
        restartButton.setBounds(335, 320, 100, 35);
        restartButton.setVisible(false);
        restartButton.setFocusable(false);
        restartButton.addActionListener(e -> restartGame());
        add(restartButton);

        Timer gameLoop = new Timer(30, e -> {
            if (playerAttackCooldown > 0) {
                playerAttackCooldown--;
            }
            if (healCooldown > 0) {
                healCooldown--;
            }
            if (enemyAttackCooldown > 0) {
                enemyAttackCooldown--;
            }
                if (player.getCurrentHP() > 0) {
                    boolean anyAlive = false;

                for (Enemy enemy : enemies) {
                    if (enemy.isAlive()) {
                        anyAlive = true;
                        enemy.moveTowards(playerX, playerY);

                        double distance = Math.hypot(playerX - enemy.getX(), playerY - enemy.getY());
                        if (distance <= 40 && enemyAttackCooldown == 0) {
                            player.takeDamage(enemy.getDamage());
                            enemyAttackCooldown = 30;
                        }
                    }
            }
            if (!anyAlive) {
                wave++;
                spawnWave(wave);
            }
        }
            if (player.getCurrentHP() <= 0 && !restartButton.isVisible()) {
                restartButton.setVisible(true);
            }
            repaint();
        });
        gameLoop.start();
    }
private void spawnWave(int count) {
    enemies.clear();
    for (int i = 0; i < count; i++) {
        int spawnX, spawnY;
        int side = random.nextInt(4);
        if (side == 0) {
            spawnX = random.nextInt(750);
            spawnY = 0;
        } else if (side == 1) {
            spawnX = random.nextInt(750);
            spawnY = 550;
        } else if (side == 2) {
            spawnX = 0;
            spawnY = random.nextInt(550);
        } else {
            spawnX = 750;
            spawnY = random.nextInt(550);
        }
        int type = random.nextInt(100);
        if (wave >= 3 && type < 25)
        {
            enemies.add(new Enemy("Orc", 100 + (wave * 15), spawnX, spawnY, 1, 20, Color.ORANGE));
        } else if (wave >= 2 && type < 55)
        {
            enemies.add(new Enemy("Skeleton", 30 + (wave * 5), spawnX, spawnY, 4, 5, Color.MAGENTA));
        } else
        {
            enemies.add(new Enemy("Goblin", 50 + (wave * 10), spawnX, spawnY, 2, 10, Color.BLACK));
        }
    }
    if (wave % 5 == 0) {
        enemies.add(new Enemy("BOSS", 250 + (wave * 20), 350, 0, 2, 25, Color.YELLOW));
    }
}

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (player instanceof Warrior) {
            g.setColor(Color.BLUE);
        } else if (player instanceof Archer) {
            g.setColor(Color.RED);
        } else if (player instanceof Medic) {
            g.setColor(Color.GREEN);
        } else {
            g.setColor(Color.WHITE);
        }
        g.fillRect(playerX, playerY, 40, 40);
        g.setColor(Color.WHITE);
        g.drawString(player.GetName() + " (" + player.getCurrentHP() + " HP)", playerX - 10, playerY - 10);

        for (Enemy enemy : enemies) {
            if (enemy.isAlive()) {
                g.setColor(enemy.getColor());
                g.fillRect(enemy.getX(), enemy.getY(), 40, 40);
                g.setColor(Color.WHITE);
                g.drawString(enemy.getName() + " (" + enemy.getCurrentHP() + " HP)", enemy.getX() - 10, enemy.getY() - 10);
            }
        }

        g.setColor(Color.WHITE);
        g.drawString("Player HP: " + player.getCurrentHP(), 20, 20);
            g.drawString("Wave: " + wave, 20, 40);
            g.drawString("Score: " + score, 20, 60);
            if (player instanceof Medic) {
            g.drawString("Heal CD: " + (healCooldown / 33) + "s", 20, 80);
        }

        if (player.getCurrentHP() <= 0) {
            g.setColor(Color.RED);
            g.drawString("GAME OVER", 350, 300);
        }
    }
        private void restartGame() {
            player.resetHP();
            playerX = 350;
            playerY = 250;
            wave = 1;
            score = 0;
            spawnWave(wave);
            enemyAttackCooldown = 0;
            playerAttackCooldown = 0;
            healCooldown = 0;
            restartButton.setVisible(false);
            requestFocusInWindow();
            repaint();
        }
        @Override
        public void keyPressed(KeyEvent e) {
            if (player.getCurrentHP() <= 0) return;
            int speed = 20;

            if (e.getKeyCode() == KeyEvent.VK_W) {
                playerY -= speed;
            } else if (e.getKeyCode() == KeyEvent.VK_S) {
                playerY += speed;
            } else if (e.getKeyCode() == KeyEvent.VK_A) {
                playerX -= speed;
            } else if (e.getKeyCode() == KeyEvent.VK_D) {
                playerX += speed;
            } else if (e.getKeyCode() == KeyEvent.VK_SPACE && playerAttackCooldown == 0) {
                Enemy closest = null;
                double minDistance = Double.MAX_VALUE;

                for (Enemy enemy : enemies) {
                    if (enemy.isAlive()) {
                        double dist = Math.hypot(playerX - enemy.getX(), playerY - enemy.getY());
                        if (dist <= player.GetAttackRange() && dist < minDistance) {
                            minDistance = dist;
                            closest = enemy;
                        }
                    }
                }

                if (closest != null) {
                    closest.takeDamage(player.attack());
                    if (!closest.isAlive()) {
                        score += 100;
                    }

                    if (player instanceof Warrior) {
                        playerAttackCooldown = 27;
                    } else if (player instanceof Archer) {
                        playerAttackCooldown = 40;
                    } else if (player instanceof Medic) {
                        playerAttackCooldown = 33;
                    }
                }
            } else if (e.getKeyCode() == KeyEvent.VK_H && player instanceof Medic && healCooldown == 0) {
                player.heal(20);
                healCooldown = 333;
            }

            repaint();
        }

        @Override
        public void keyTyped(KeyEvent e) {}

        @Override
        public void keyReleased(KeyEvent e) {}
    }