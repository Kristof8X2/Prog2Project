package org.example;

import domain.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

public class GamePanel extends JPanel implements KeyListener {
    private Hero player;
    private int playerMaxHP;
    private ArrayList<Enemy> enemies = new ArrayList<>();
    private ArrayList<PowerUp> powerUps = new ArrayList<>();
    private Random random = new Random();
    private int playerX = 350;
    private int playerY = 250;
    private boolean up, down, left, right;
    private int wave = 1;
    private int score = 0;
    private double damageMultiplier = 1.0;
    private int enemyAttackCooldown = 0;
    private int playerAttackCooldown = 0;
    private int healCooldown = 0;
    private int attackEffectTimer = 0;
    private int attackTargetX, attackTargetY;
    private JButton restartButton;
    private HighScoreManager highScoreManager = new HighScoreManager();
    private ArrayList<HighScore> topScores = new ArrayList<>();
    private boolean scoreSaved = false;

    public GamePanel(Hero player) {
        this.player = player;
        this.playerMaxHP = player.getCurrentHP();
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
            if (attackEffectTimer > 0) {
                attackEffectTimer--;
            }
                if (player.getCurrentHP() > 0) {
                    int speed = 4;
                    if (up) playerY -= speed;
                    if (down) playerY += speed;
                    if (left) playerX -= speed;
                    if (right) playerX += speed;

                    if (playerX < 0) playerX = 0;
                    if (playerY < 0) playerY = 0;
                    if (getWidth() > 0 && playerX > getWidth() - 40) playerX = getWidth() - 40;
                    if (getHeight() > 0 && playerY > getHeight() - 40) playerY = getHeight() - 40;

                    Iterator<PowerUp> it = powerUps.iterator();
                    while (it.hasNext()) {
                        PowerUp p = it.next();
                        double dist = Math.hypot((playerX + 20) - (p.getX() + 12), (playerY + 20) - (p.getY() + 12));
                        if (dist <= 35) {
                            if (p.getType() == PowerUp.Type.HEALTH) {
                                player.heal(20);
                            } else if (p.getType() == PowerUp.Type.SCORE) {
                                score += 15;
                            } else if (p.getType() == PowerUp.Type.DAMAGE_BOOST) {
                                damageMultiplier += 0.05;
                            }
                            it.remove();
                        }
                    }
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
            if (player.getCurrentHP() <= 0 && !restartButton.isVisible()) {
                restartButton.setVisible(true);
                if (!scoreSaved) {
                    scoreSaved = true;
                    try {
                        HighScore entry = new HighScore(
                                player.GetName(),
                                player.getClass().getSimpleName(),
                                score,
                                wave
                        );
                        highScoreManager.saveScore(entry);
                        topScores = new ArrayList<>(highScoreManager.loadTopScores(5));
                    } catch (ScoreStorageException ex) {
                        System.err.println("High score error: " + ex.getMessage());
                    }
                }
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
            enemies.add(EnemyFactory.createRandomEnemy(wave, spawnX, spawnY));
        }
        if (wave % 5 == 0) {
            enemies.add(EnemyFactory.createEnemy(EnemyFactory.EnemyType.BOSS, wave, 350, 0));
        }
    }

    private void spawnPowerUp(Enemy enemy) {
        if (enemy.getName().equals("BOSS") || random.nextInt(100) < 50) {
            PowerUp.Type[] types = PowerUp.Type.values();
            PowerUp.Type chosen = types[random.nextInt(types.length)];
            powerUps.add(new PowerUp(chosen, enemy.getX() + 8, enemy.getY() + 8));
        }
    }
    private void drawHealthBar(Graphics g, int x, int y, int currentHP, int maxHP) {
        int barWidth = 40;
        int barHeight = 6;
        double ratio = maxHP > 0 ? Math.max(0, Math.min(1.0, (double) currentHP / maxHP)) : 0;
        g.setColor(Color.RED);
        g.fillRect(x, y, barWidth, barHeight);
        g.setColor(Color.GREEN);
        g.fillRect(x, y, (int) (barWidth * ratio), barHeight);
        g.setColor(Color.BLACK);
        g.drawRect(x, y, barWidth, barHeight);
    }
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        for (PowerUp p : powerUps) {
            g.setColor(p.getColor());
            g.fillOval(p.getX(), p.getY(), 24, 24);
            g.setColor(Color.WHITE);
            g.drawOval(p.getX(), p.getY(), 24, 24);
            g.drawString(p.getLabel(), p.getX() + 1, p.getY() - 4);
        }
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
        drawHealthBar(g, playerX, playerY - 10, player.getCurrentHP(), playerMaxHP);
        g.setColor(Color.WHITE);
        g.drawString(player.GetName() + " (" + player.getCurrentHP() + " HP)", playerX - 10, playerY - 16);

        for (Enemy enemy : enemies) {
            if (enemy.isAlive()) {
                g.setColor(enemy.getColor());
                g.fillRect(enemy.getX(), enemy.getY(), 40, 40);
                drawHealthBar(g, enemy.getX(), enemy.getY() - 10, enemy.getCurrentHP(), enemy.getMaxHP());
                g.setColor(Color.WHITE);
                g.drawString(enemy.getName() + " (" + enemy.getCurrentHP() + " HP)", enemy.getX() - 10, enemy.getY() - 16);
            }
        }
        if (attackEffectTimer > 0) {
            Graphics2D g2 = (Graphics2D) g;
            g2.setStroke(new BasicStroke(3));
            if (player instanceof Archer) {
                g2.setColor(Color.YELLOW);
                g2.drawLine(playerX + 20, playerY + 20, attackTargetX, attackTargetY);
            } else {
                g2.setColor(Color.WHITE);
                g2.drawOval(playerX - 20, playerY - 20, 80, 80);
                g2.setColor(Color.ORANGE);
                g2.drawOval(attackTargetX - 15, attackTargetY - 15, 30, 30);
            }
            g2.setStroke(new BasicStroke(1));
        }
        g.setColor(Color.WHITE);
        g.drawString("Player HP: " + player.getCurrentHP() + " / " + playerMaxHP, 20, 20);
        g.drawString("Wave: " + wave, 20, 40);
        g.drawString("Score: " + score, 20, 60);
        g.drawString("DMG Boost: +" + Math.round((damageMultiplier - 1.0) * 100) + "%", 20, 80);
        if (player instanceof Medic) {
            g.drawString("Heal CD: " + (healCooldown / 33) + "s", 20, 100);
        }

        if (player.getCurrentHP() <= 0) {
            g.setColor(Color.RED);
            g.drawString("GAME OVER", 350, 300);
        }
        if (player.getCurrentHP() <= 0) {
            g.setColor(Color.RED);
            g.drawString("GAME OVER", 350, 300);

            g.setColor(Color.YELLOW);
            g.drawString(" TOP 5 HIGH SCORES ", 305, 385);
            g.setColor(Color.WHITE);
            int y = 405;
            for (int i = 0; i < topScores.size(); i++) {
                HighScore s = topScores.get(i);
                g.drawString((i + 1) + ". " + s.toDisplayString(), 270, y);
                y += 20;
            }
        }
    }
        private void restartGame() {
            player.resetHP();
            playerX = 350;
            playerY = 250;
            up = down = left = right = false;
            wave = 1;
            score = 0;
            damageMultiplier = 1.0;
            powerUps.clear();
            attackEffectTimer = 0;
            spawnWave(wave);
            enemyAttackCooldown = 0;
            playerAttackCooldown = 0;
            healCooldown = 0;
            restartButton.setVisible(false);
            requestFocusInWindow();
            scoreSaved = false;
            repaint();
        }
        @Override
        public void keyPressed(KeyEvent e) {
            if (player.getCurrentHP() <= 0) return;

            if (e.getKeyCode() == KeyEvent.VK_W) {
                up = true;
            } else if (e.getKeyCode() == KeyEvent.VK_S) {
                down = true;
            } else if (e.getKeyCode() == KeyEvent.VK_A) {
                left = true;
            } else if (e.getKeyCode() == KeyEvent.VK_D) {
                right = true;
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
                    int finalDamage = (int) Math.round(player.attack() * damageMultiplier);
                    closest.takeDamage(finalDamage);

                    attackTargetX = closest.getX() + 20;
                    attackTargetY = closest.getY() + 20;
                    attackEffectTimer = 6;

                    if (!closest.isAlive()) {
                        score += 100;
                        spawnPowerUp(closest);
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
        }

    @Override
    public void keyTyped(KeyEvent e) {}

    @Override
    public void keyReleased(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_W) up = false;
        if (e.getKeyCode() == KeyEvent.VK_S) down = false;
        if (e.getKeyCode() == KeyEvent.VK_A) left = false;
        if (e.getKeyCode() == KeyEvent.VK_D) right = false;
    }
}