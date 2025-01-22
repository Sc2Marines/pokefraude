package src.models;

public class Terrain {
    private TypeTerrain etat;

    public Terrain() {
        this.etat = TypeTerrain.NORMAL;
    }

    public void modifier(TypeTerrain nouvelEtat) {
        this.etat = nouvelEtat;
    }

    public TypeTerrain getEtat() {
        return etat;
    }
     public enum TypeTerrain {
        NORMAL,
        INONDE
    }
}