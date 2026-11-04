package domain;

public class Archer extends Hero {

    public Archer(String name){
        super(name, 120, 13, 5);
    }

    @Override
    public int attack() {
        if (GetAttackRange() > 4) {
            return getBasedmg() + 15;
        }
        return getBasedmg();
    }
}