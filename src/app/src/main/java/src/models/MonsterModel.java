package src.models;

public class MonsterModel {
    private String name;
    private String type;
    private int[] hp;
    private int[] speed;
    private int[] attack;
    private int[] defense;
    private double paralysis;
    private double flood;
    private double fall;

    public MonsterModel(String name, String type) {
        this.name = name;
        this.type = type;
    }

    public void populateStats( int[] hp, int[] speed, int[] attack, int[] defense, double paralysis, double flood, double fall){
        this.hp = hp;
        this.speed = speed;
        this.attack = attack;
        this.defense = defense;
        this.paralysis = paralysis;
        this.flood = flood;
        this.fall = fall;
    }

    @Override
    public String toString() {
        return "ModeleMonstre{" +
                "name='" + name + '\'' +
                ", type='" + type + '\'' +
                ", hp=" + hp[0] +"-"+ hp[1] +
                ", speed=" + speed[0] +"-"+speed[1] +
                ", attack=" + attack[0] +"-"+attack[1] +
                ", defense=" + defense[0] +"-"+defense[1] +
                ", paralysis=" + paralysis +
                ", flood=" + flood +
                ", fall=" + fall +
                '}';
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public int[] getHp() {
        return hp;
    }

    public int[] getSpeed() {
        return speed;
    }

    public int[] getAttack() {
        return attack;
    }

    public int[] getDefense() {
        return defense;
    }

    public double getParalysis() {
        return paralysis;
    }

    public double getFlood() {
        return flood;
    }

    public double getFall() {
        return fall;
    }
}