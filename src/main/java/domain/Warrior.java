package domain;

public class Warrior extends Hero {

    public Warrior(String name){
        super(name, 150, 20, 70);
    }

    @Override
    public int attack() {
        if (getCurrentHP() < 30) {
            return getBasedmg() * 2;
        }
        return getBasedmg() + 10;
    }
}