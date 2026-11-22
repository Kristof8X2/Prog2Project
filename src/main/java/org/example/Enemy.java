package domain;

public class Enemy {
    private String name;
    private int currentHP;
    private int x;
    private int y;

    public Enemy(String name, int maxHP, int x, int y) {
        this.name = name;
        this.currentHP = maxHP;
        this.x = x;
        this.y = y;
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
}