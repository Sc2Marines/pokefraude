package src.models;

public class AttackModel {
    private String name;
    private String type;
    private int power;
    private int nbUse;
    private double fail;


    public AttackModel(String name, String type, int power, int nbUse, double fail) {
        this.name = name;
        this.type = type;
        this.power = power;
        this.nbUse = nbUse;
        this.fail = fail;
    }

    // deep copy constructor
    public AttackModel(AttackModel other) {
        this.name = other.name;
        this.type = other.type;
        this.power = other.power;
        this.fail = other.fail;
        this.nbUse = other.nbUse;
    }


    @Override
    public String toString() {
        return "ModeleAttaque{" +
                "name='" + name + '\'' +
                ", type='" + type + '\'' +
                ", power=" + power +
                ", nbUse=" + nbUse +
                ", fail=" + fail +
                '}';
    }


    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public int getPower() {
        return power;
    }

    public int getNbUse() {
        return nbUse;
    }

    public double getFail() {
        return fail;
    }

    public boolean isAttackAvailable() {
        return (this.nbUse > 0);
    }

    public void use(int nbUse) {
        this.nbUse -= nbUse;
    }

    @Override
    public boolean equals(Object obj){
        if (this == obj)
            return true;
        if (obj == null || getClass() != obj.getClass())
            return false;
        AttackModel that = (AttackModel) obj;
        if (!name.equals(that.name))
            return false;
        if (!type.equals(that.type))
            return false;
        if (Double.compare(that.fail, fail) != 0)
            return false;
        if (power != that.power)
            return false;
        return nbUse == that.nbUse;
    }

    @Override
    public int hashCode(){
        int result;
        long temp;
        result = name.hashCode();
        result = 31 * result + type.hashCode();
        result = 31 * result + power;
        result = 31 * result + nbUse;
        temp = Double.doubleToLongBits(fail);
        result = 31 * result + (int) (temp ^(temp >>> 32));
        return result;
    }
}