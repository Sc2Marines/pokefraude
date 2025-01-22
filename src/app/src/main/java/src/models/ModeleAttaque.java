package src.models;

public class ModeleAttaque {
    private String name;
    private String type;
    private int power;
    private int nbUse;
    private double fail;


    public ModeleAttaque(String name, String type, int power, int nbUse, double fail) {
        this.name = name;
        this.type = type;
        this.power = power;
        this.nbUse = nbUse;
        this.fail = fail;
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
}