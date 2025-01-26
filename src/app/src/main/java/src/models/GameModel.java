package src.models;


import java.util.ArrayList;
import java.util.List;

public class GameModel {
    private List<PlayerModel> joueurs;
    private List<MonsterModel> availableMonsters;
    private List<AttackModel> availableAttacks;
    private Terrain terrain;

    /**
     * Constructor for the main game model.
     * @param availableMonsters List of available monsters to pick.
     * @param availableAttacks List of all attacks.
     */
    public GameModel(List<MonsterModel> availableMonsters, List<AttackModel> availableAttacks) {
        this.joueurs = new ArrayList<>();
        this.availableMonsters = availableMonsters;
        this.availableAttacks = availableAttacks;
        if (availableMonsters.size() < 6) {
            System.err.println("Not enough monsters to create a game !");
            return;
        }
        terrain = new Terrain();
    }

    /**
     * Add a player to the game.
     * @param player The player to add.
     * @return The result's string.
     */
    public String addJoueur(PlayerModel player)
    {
        this.joueurs.add(player);
        return "Joueur " + player.getNom() + " ajouté \n";
    }

    /**
     * Get a player from his index.
     * @param index The index of the player.
     * @return The player instance.
     */
    public PlayerModel getJoueur(int index) {
        return joueurs.get(index);
    }

    /**
     * Execute the pre-turn actions.
     * @return The result's string.
     */
    private String executeAnteActions(){
        String result = "";
        this.terrain.decrement();
        for (PlayerModel player : joueurs) {
            Monster monster = player.getMonstreActif();
            result = monster.beforeTurnEffects(terrain);
        }
        return result;
    }

    /**
     * Execute the after-turn actions.
     * @return The result's string.
     */
    private String executePostActions(){
        String result = "";
        for (PlayerModel player : joueurs) {
            Monster monster = player.getMonstreActif();
            result = monster.afterTurnEffects(terrain);
        }
        return result;
    }

    /**
     * Execute the turn actions.
     * @param actionJoueur1 The action selected by the player 1.
     * @param actionJoueur2 The action selected by the player 2.
     * @return  The result's string.
     */
    public String executerActions(Action actionJoueur1, Action actionJoueur2) {
        
        StringBuilder result = new StringBuilder();
        result.append(this.executeAnteActions());
        // Execute changes of monsters
        if (actionJoueur1.getType() == ActionType.CHANGER_MONSTRE) {
            Monster player2Monster = joueurs.get(1).getMonstreActif();
            joueurs.get(0).changerMonstreActif(actionJoueur1.getIndex(), terrain, player2Monster);
        }
        if (actionJoueur2.getType() == ActionType.CHANGER_MONSTRE) {
            Monster player1Monster = joueurs.get(0).getMonstreActif();
            joueurs.get(1).changerMonstreActif(actionJoueur2.getIndex(), terrain, player1Monster);
        }
        // Execute objets
        if (actionJoueur1.getType() == ActionType.OBJET && actionJoueur1.getObjet() != null) {
                result.append(actionJoueur1.getObjet().use(joueurs.get(0).getMonstreActif()));
                joueurs.get(0).retirerObject(actionJoueur1.getObjet());
            }
        
        if (actionJoueur2.getType() == ActionType.OBJET && actionJoueur2.getObjet() != null) {
                result.append(actionJoueur2.getObjet().use(joueurs.get(1).getMonstreActif()));
                joueurs.get(1).retirerObject(actionJoueur2.getObjet());
            }
        
        //Execute attacks
        if (actionJoueur1.getType() == ActionType.ATTAQUE && actionJoueur2.getType() == ActionType.ATTAQUE) {
            int initJoueur = getInitiative(joueurs.get(0), joueurs.get(1));
            if (initJoueur == 0) {
                result.append(attack(joueurs.get(0), joueurs.get(1), actionJoueur1.getAttaque(), actionJoueur2.getAttaque()));
            } else {
                result.append(attack(joueurs.get(1), joueurs.get(0), actionJoueur2.getAttaque(), actionJoueur1.getAttaque()));
            }
        } else if (actionJoueur1.getType() == ActionType.ATTAQUE) {
            result.append(attack(joueurs.get(0), joueurs.get(1), actionJoueur1.getAttaque(), null));
        } else if (actionJoueur2.getType() == ActionType.ATTAQUE) {
            result.append(attack(joueurs.get(1), joueurs.get(0), actionJoueur2.getAttaque(), null));
        }
        result.append(this.executePostActions());
        return result.toString();
    }

    /**
     * Attack method of the player.
     * @param attaquant The attacking player.
     * @param defender The defensive player.
     * @param attackAttaquant The attacking player's attack.
     * @param attackDefenseur The defensive player's attack.
     * @return The result's string.
     */
    private String attack(PlayerModel attaquant, PlayerModel defender, AttackModel attackAttaquant, AttackModel attackDefenseur) {
        StringBuilder result = new StringBuilder();
        if (attackAttaquant != null) {
            result.append(attaquant.attack(defender, attackAttaquant, terrain));
        } else {
            result.append(attaquant.getMonstreActif().takeDamages(10));
        }
        if (attackDefenseur != null) {
            result.append(defender.attack(attaquant, attackDefenseur, terrain));
        } else {
            result.append(defender.getMonstreActif().takeDamages(10));
        }
        return result.toString();
    }

    /**
     * Determine wich player should take the initative.
     * @param player1 The first player.
     * @param player2 The second player.
     * @return An int wich repesent wich player take the initiative.
     */
    private int getInitiative(PlayerModel player1, PlayerModel player2) {
        int speedJoueur1 = player1.getMonstreActif().getVitesse();
        int speedJoueur2 = player2.getMonstreActif().getVitesse();

        return speedJoueur1 > speedJoueur2 ? 0 : 1;
    }

    /**
     * Determine if the game is finished
     * @return A boolean.
     */
    public boolean estTermine() {
        for (PlayerModel joueur : joueurs) {
            if (joueur.estVaincu()) {
                return true;
            }
        }
        return false;
    }

    /**
     * Get the winner of the fight.
     * @return The player or null if none of them have win.
     */
    public PlayerModel getVainqueur() {
        if (joueurs.get(0).estVaincu()) {
            return joueurs.get(1);
        } else if (joueurs.get(1).estVaincu()) {
            return joueurs.get(0);
        } else {
            return null;
        }
    }

    /**
     * Get the current terrain.
     * @return The Terrain.
     */
    public Terrain getTerrain() {
        return terrain;
    }

    /**
     * Get the list of available monsters.
     * @return The list of available monsters.
     */
    public List<MonsterModel> getAvailableMonsters() {
        return availableMonsters;
    }

    /**
     * Get the list of available attacks.
     * @return The list of available attacks.
     */
    public List<AttackModel> getAvailableAttacks() {
        return availableAttacks;
    }

    /**
     * Get the list of players in the game.
     * @return The list of players.
     */
    public List<PlayerModel> getPlayers() {
        return joueurs;
    }
}