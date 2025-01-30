package src.models;

public class Terrain {
    private TypeTerrain etat;
    private int turnLeftBeforeNormal;

    /**
     * Terrain constructor
     */
    public Terrain() {
        this.etat = TypeTerrain.NORMAL;
        this.turnLeftBeforeNormal = 0;
    }

    /**
     * Modify the stat of the terrain.
     * @param newState The new state to apply.
     */
    public void modify(TypeTerrain newState){
        this.etat = newState;
    }

    /**
     * Modify the terrain state for a period of turns.
     * @param newState The new state to apply.
     * @param time The time where this state stays.
     */
    public void modify(TypeTerrain newState, int time) {
        this.etat = newState;
        this.turnLeftBeforeNormal = time;
    }

    /**
     * Counter to decrement the terrain counter before returning to his originam state.
     */
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

    /**
     * Get the terrain state.
     * @return The terrain state.
     */
    public TypeTerrain getEtat() {
        return etat;
    }
    /**
     * Enum of all the possible terrain states.
     */
    public enum TypeTerrain {
        NORMAL,
        INONDE
    }
}