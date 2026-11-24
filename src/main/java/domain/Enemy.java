package domain;

import java.awt.Color;

public class Enemy {
    private String name;
    private int currentHP;
    private int x;
    private int y;
    private int speed;
    private int damage;
    private Color color;

    public Enemy(String name, int maxHP, int x, int y, int speed, int damage, Color color) {
        this.name = name;
        this.currentHP = maxHP;
        this.x = x;
        this.y = y;
        this.speed = speed;
        this.damage = damage;
        this.color = color;
    }

    public void takeDamage(int damage) {
        this.currentHP -= damage;
        if (this.currentHP < 0) {
            this.currentHP = 0;
        }
    }

    public boolean isAlive() {

        return this.currentHP > 0;
    }

    public String getName() {

        return name;
    }

    public int getCurrentHP() {
        return currentHP;
    }

    public int getX() {

        return x;
    }

    public int getY() {
        return y;
    }
    public int getDamage() {
        return damage;
    }
    public Color getColor() {
        return color;
    }
    public void moveTowards(int targetX, int targetY) {
        if (x < targetX) x += speed;
        if (x > targetX) x -= speed;
        if (y < targetY) y += speed;
        if (y > targetY) y -= speed;
    }
}