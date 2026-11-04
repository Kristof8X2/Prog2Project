package domain;

public class Medic extends Hero {

    public Medic(String name){
        super(name, 100, 10, 1);
    }

    @Override
    public int attack() {
        if (getCurrentHP() < 30) {
            return getBasedmg() / 2;
        }
        return getBasedmg();
    }
}