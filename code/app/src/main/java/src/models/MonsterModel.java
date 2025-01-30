package src.models;

import java.util.Arrays;

public class MonsterModel {
    private String name;
    private Types type;
    private Types subType;
    private int[] hp;
    private int[] speed;
    private int[] attack;
    private int[] defense;
    private double paralysis;
    private double flood;
    private double fall;

    private static final String ELECTRIC = "Electric";
    private static final String FIRE = "Fire";
    private static final String WATER = "Water";
    private static final String NATURE = "Nature";
    private static final String DIRT = "Dirt";
    private static final String PLANT = "Plant";
    private static final String INSECT = "Insect";
    
    

    /**
     * COnstructor for this represnetation of the parsed monster.
     * @param name The name of the monster.
     * @param type The type of the monster
     */
    public MonsterModel(String name, String type) {
        this.name = name;
        switch (type) {
            case ELECTRIC:
                this.type = Types.ELECTRIC;
                this.subType = this.type;
                break;
            case FIRE:
                this.type = Types.FIRE;
                this.subType = this.type;
                break;
            case WATER:
                this.type = Types.WATER;
                this.subType = this.type;
                break;
            case NATURE:
                this.type = Types.NATURE;
                this.subType = this.type;
                break;
            case DIRT:
                this.type = Types.DIRT;
                this.subType = this.type;
                break;
            case PLANT:
                this.type = Types.NATURE;
                this.subType = Types.PLANT;
                break;
            case INSECT:
                this.type = Types.NATURE;
                this.subType = Types.INSECT;
                break;
            default:
                break;
        }
    }

    /**
     * OverLoad to get the same result as the previous constructor but this time directly from Types and not string as type.
     * @param name The name of the monster.
     * @param type The type of the monster.
     */
    public MonsterModel(String name, Types type) {
        this.name = name;
        if (type == Types.PLANT || type == Types.INSECT) {
            this.type = Types.NATURE;
            this.subType = type;
        }
        else {
            this.type = type;
            this.subType = type;
        }
    }

    /**
     * Population method to fill monster stats.
     * @param hp The monster's hp
     * @param speed The monster's speed
     * @param attack The monster's attack
     * @param defense The monster's defense
     * @param paralysis The monster's paralysis chance
     * @param flood The monster's flood chance
     * @param fall The monster's fall chance
     */
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
    /**
     * Ovverided toString method;
     */
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

    /**
     * Get the monster's name.
     * @return The name.
     */
    public String getName() {
        return name;
    }

    /**
     * Get the monster's type.
     * @return The type.
     */
    public Types getType() {
        return type;
    }

    /**
     * Get the monster's subtype.
     * @return The subtype
     */
    public Types getSubType() {
        return subType;
    }

    /**
     * Get the monster's hp.
     * @return The hp.
     */
    public int[] getHp() {
        return hp;
    }

    /**
     * Get the monster's speed.
     * @return The speed.
     */
    public int[] getSpeed() {
        return speed;
    }

    /**
     * Get the monster's attack.
     * @return The attack.
     */
    public int[] getAttack() {
        return attack;
    }

    /**
     * Get the monster's defense.
     * @return The defense.
     */
    public int[] getDefense() {
        return defense;
    }

    /**
     * Get the monster's paralysis chance.
     * @return The paralysis chance.
     */
    public double getParalysis() {
        return paralysis;
    }

    /**
     * Get the monster's flood chance.
     * @return The flood chance.
     */
    public double getFlood() {
        return flood;
    }

    /**
     * Get the monster's fall chance.
     * @return The fall chance.
     */
    public double getFall() {
        return fall;
    }

    @Override
    /**
     * Overided method for junit testing purpose.
     */
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
    /**
     * Overided method for junit testing purpose.
     */
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