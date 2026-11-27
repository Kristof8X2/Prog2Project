package domain;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class HeroTest {

    @Test
    void warriorNormalAndLowHpAttack() {
        Warrior warrior = new Warrior("Conan");
        assertEquals("Conan", warrior.GetName());
        assertEquals(250, warrior.getMaxHP());
        assertEquals(45, warrior.attack());
        warrior.takeDamage(225);
        assertEquals(25, warrior.getCurrentHP());
        assertEquals(70, warrior.attack());
    }

    @Test
    void archerAttackIncludesRangeBonus() {
        Archer archer = new Archer("Legolas");
        assertEquals(175, archer.getMaxHP());
        assertEquals(250, archer.GetAttackRange());
        assertEquals(28, archer.attack());
    }

    @Test
    void medicNormalAndLowHpAttack() {
        Medic medic = new Medic("Mercy");
        assertEquals(130, medic.getMaxHP());
        assertEquals(20, medic.attack());
        medic.takeDamage(110);
        assertEquals(10, medic.attack());
    }

    @Test
    void takeDamageHealAndResetHpAreClamped() {
        Warrior warrior = new Warrior("Tank");
        warrior.takeDamage(999);
        assertEquals(0, warrior.getCurrentHP());
        warrior.heal(50);
        assertEquals(50, warrior.getCurrentHP());
        warrior.heal(500);
        assertEquals(warrior.getMaxHP(), warrior.getCurrentHP());
        warrior.takeDamage(100);
        warrior.resetHP();
        assertEquals(warrior.getMaxHP(), warrior.getCurrentHP());
    }
}