package domain;

public class Archer extends Hero {

    public Archer(String name){
        super(name, 175, 13, 250);
    }

    @Override
    public int attack() {
        if (GetAttackRange() > 4) {
            return getBasedmg() + 15;
        }
        return getBasedmg();
    }
}