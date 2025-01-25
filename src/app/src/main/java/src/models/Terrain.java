package src.models;

public class Terrain {
    private TypeTerrain etat;
    private int turnLeftBeforeNormal;

    public Terrain() {
        this.etat = TypeTerrain.NORMAL;
        this.turnLeftBeforeNormal = 0;
    }

    public void modify(TypeTerrain newState){
        this.etat = newState;
    }

    public void modify(TypeTerrain newState, int time) {
        this.etat = newState;
        this.turnLeftBeforeNormal = time;
    }

    public void decrement(){
        if (this.turnLeftBeforeNormal > 0) {
            this.turnLeftBeforeNormal --;
        }
        else {
            if (this.etat != TypeTerrain.NORMAL) {
                this.etat = TypeTerrain.NORMAL;
            }
        }
    }

    public TypeTerrain getEtat() {
        return etat;
    }
     public enum TypeTerrain {
        NORMAL,
        INONDE
    }
}