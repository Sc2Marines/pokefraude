package src.models.bots.models;

import src.models.GameModel;
import src.models.Monster;
import src.models.PlayerModel;
import src.models.Terrain;
import src.models.Types;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public class GameReportModel {
    private PlayerModel myself;
    private GameModel gameModel;
    private PlayerModel ennemy;

    private Monster actualMonster;
    private List<Monster> deckMonsters;
    private Monster ennemyActualMonster;
    private List<Monster> ennemyDeckMonsters;
    private Terrain actualTerrain;
    private Map<Types, Map<Types, Double>> buffs;

    /**
     * constructor for the game report (the goal is treat some informations to make them more accessible).
     * @param myself My player instance
     * @param gameState The current game state
     */
    public GameReportModel(PlayerModel myself, GameModel gameState) {
        this.myself = myself;
        this.gameModel  = gameState;
        int myIndex = gameState.getPlayers().indexOf(myself);
        if (myIndex > 0){
            this.ennemy = gameState.getJoueur(0);
        } else {
            this.ennemy = gameState.getJoueur(1);
        }
        this.buffs = Monster.TYPE_ADVANTAGES;
        this.generateReport();
    }

    /**
     * Generate the game report.
     */
    public void generateReport() {
        this.actualMonster = myself.getMonstreActif();

        List<Monster> allMonsters = new ArrayList<>();
        for (Monster monster : myself.getMonstres()) {
            if (monster.getPV() > 0) {
                allMonsters.add(monster);
            }
        }
        allMonsters.remove(actualMonster);
        this.deckMonsters = allMonsters;

        this.ennemyActualMonster = this.ennemy.getMonstreActif();

        List<Monster> allEnnemyMonsters = new ArrayList<>(); 
        for (Monster monster : this.ennemy.getMonstres()) {
            if (monster.getPV() > 0) {
                allEnnemyMonsters.add(monster);
            }
        }
        allEnnemyMonsters.remove(this.ennemyActualMonster);
        ennemyDeckMonsters = allEnnemyMonsters;

        this.actualTerrain = this.gameModel.getTerrain();
    }

    // Getters for all properties

    public PlayerModel getMyself() {
        return myself;
    }

    public GameModel getGameModel() {
        return gameModel;
    }

    public PlayerModel getEnnemy() {
        return ennemy;
    }

    public Monster getActualMonster() {
        return actualMonster;
    }

    public List<Monster> getDeckMonsters() {
        return deckMonsters;
    }

    public Monster getEnnemyActualMonster() {
        return ennemyActualMonster;
    }

    public List<Monster> getEnnemyDeckMonsters() {
        return ennemyDeckMonsters;
    }

    public Terrain getActualTerrain() {
        return actualTerrain;
    }

    public Map<Types, Map<Types, Double>> getBuffs() {
        return buffs;
    }
}