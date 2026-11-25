package domain;

public abstract class Hero {
    private String name;
    private int maxHP;
    private int currentHP;
    private int baseDmg;
    private int attackRange;

    public Hero(String name, int maxHP, int baseDmg, int attackRange) {
        this.name = name;
        this.maxHP = maxHP;
        this.currentHP = maxHP;
        this.baseDmg = baseDmg;
        this.attackRange = attackRange;
    }
    public abstract int attack();

    public String GetName() {
        return name;
    }
    public int getMaxHP() {
        return maxHP;
    }
    public int getCurrentHP() {
        return currentHP;
    }
    public int getBasedmg() {
        return baseDmg;
    }
    public int GetAttackRange() {
        return attackRange;
    }
    public void takeDamage(int damage) {
        this.currentHP -= damage;
        if (this.currentHP < 0) {
            this.currentHP = 0;
        }
    }
    public void heal(int amount) {
        this.currentHP += amount;
        if (this.currentHP > this.maxHP) {
            this.currentHP = this.maxHP;
        }
    }
    public void resetHP() {
        this.currentHP = this.maxHP;
    }
}