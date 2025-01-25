package src.models;

import java.util.Arrays;

public class MonsterModel {
    private String name;
    private String type;
    private String subType;
    private int[] hp;
    private int[] speed;
    private int[] attack;
    private int[] defense;
    private double paralysis;
    private double flood;
    private double fall;

    public MonsterModel(String name, String type) {
        this.name = name;
        this.subType = type;
        if (this.subType.equals(Monster.PLANT) || this.subType.equals(Monster.INSECT)){
            this.type = Monster.NATURE;
        }
        else {
            this.type = type;
        }
    }

    public void populateStats(int[] hp, int[] speed, int[] attack, int[] defense, double paralysis, double flood,
            double fall) {
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
                ", hp=" + hp[0] + "-" + hp[1] +
                ", speed=" + speed[0] + "-" + speed[1] +
                ", attack=" + attack[0] + "-" + attack[1] +
                ", defense=" + defense[0] + "-" + defense[1] +
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

    public String getSubType() {
        return subType;
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

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null || getClass() != obj.getClass())
            return false;

        MonsterModel that = (MonsterModel) obj;

        if (Double.compare(that.paralysis, paralysis) != 0)
            return false;
        if (Double.compare(that.flood, flood) != 0)
            return false;
        if (Double.compare(that.fall, fall) != 0)
            return false;
        if (!name.equals(that.name))
            return false;
        if (!type.equals(that.type))
            return false;
        if (!Arrays.equals(hp, that.hp))
            return false;
        if (!Arrays.equals(speed, that.speed))
            return false;
        if (!Arrays.equals(attack, that.attack))
            return false;
        return Arrays.equals(defense, that.defense);
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = name.hashCode();
        result = 31 * result + type.hashCode();
        result = 31 * result + Arrays.hashCode(hp);
        result = 31 * result + Arrays.hashCode(speed);
        result = 31 * result + Arrays.hashCode(attack);
        result = 31 * result + Arrays.hashCode(defense); 
        temp = Double.doubleToLongBits(paralysis);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(flood);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(fall);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        return result;
    }
}