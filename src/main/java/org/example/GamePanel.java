package org.example;

import domain.Hero;
import domain.Warrior;
import domain.Archer;
import domain.Medic;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.Timer;

public class GamePanel extends JPanel implements KeyListener {
    private Hero player;
    private Enemy enemy;
    private int playerX = 350;
    private int playerY = 250;
    private int enemyAttackCooldown = 0;

    public GamePanel(Hero player) {
        this.player = player;
        this.enemy = new Enemy("Goblin", 100, 500, 250);
        setFocusable(true);
        addKeyListener(this);
        setBackground(Color.DARK_GRAY);
        Timer gameLoop = new Timer(30, e -> {
            if (enemy.isAlive() && player.getCurrentHP() > 0) {
                enemy.moveTowards(playerX, playerY);

                if (enemyAttackCooldown > 0) {
                    enemyAttackCooldown--;
                }
                double distance = Math.hypot(playerX - enemy.getX(), playerY - enemy.getY());
                if (distance <= 40 && enemyAttackCooldown == 0) {
                    player.takeDamage(10);
                    enemyAttackCooldown = 30;
                }
            }
            repaint();
        });
        gameLoop.start();
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

        if (enemy.isAlive()) {
            g.setColor(Color.BLACK);
            g.fillRect(enemy.getX(), enemy.getY(), 40, 40);
            g.setColor(Color.WHITE);
            g.drawString(enemy.getName() + " (" + enemy.getCurrentHP() + " HP)", enemy.getX() - 10, enemy.getY() - 10);
        }
            g.setColor(Color.WHITE);
            g.drawString("Player HP: " + player.getCurrentHP(), 20, 20);

        if (player.getCurrentHP() <= 0) {
            g.setColor(Color.RED);
            g.drawString("GAME OVER", 350, 300);
        }
    }
    @Override
    public void keyPressed(KeyEvent e) {
        if (player.getCurrentHP() <= 0)
            return;
        int speed = 20;

        if (e.getKeyCode() == KeyEvent.VK_W) {
            playerY -= speed;
        } else if (e.getKeyCode() == KeyEvent.VK_S) {
            playerY += speed;
        } else if (e.getKeyCode() == KeyEvent.VK_A) {
            playerX -= speed;
        } else if (e.getKeyCode() == KeyEvent.VK_D) {
            playerX += speed;
        } else if (e.getKeyCode() == KeyEvent.VK_SPACE && enemy.isAlive()) {
            double distance = Math.hypot(playerX - enemy.getX(), playerY - enemy.getY());
            if (distance <= 80) {
                int damage = player.attack();
                enemy.takeDamage(damage);
            }
        }

        repaint();
    }

    @Override
    public void keyTyped(KeyEvent e) {}

    @Override
    public void keyReleased(KeyEvent e) {}
}