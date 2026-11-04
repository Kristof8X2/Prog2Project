package domain;

public abstract class Hero {
    private String name;
    private int HP;
    private  int CurrentHP;
    private  int basedmg;
    private int Attackrange;

public Hero(String name,int HP,int basedmg,int Attackrange) {
        this.name = name;
        this.HP = HP;
        this.CurrentHP = HP;
        this.basedmg = basedmg;
        this.Attackrange = Attackrange;
    }

public  abstract  int attack();


public String GetName(){
    return name;
    }
public int getCurrentHP(){
    return CurrentHP;
    }
public int getBasedmg(){
    return  basedmg;
    }
    public int GetAttackRange(){
        return Attackrange;
    }
}
