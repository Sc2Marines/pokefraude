package src.models;


import java.util.ArrayList;
import java.util.List;

public class GameModel {
    private List<PlayerModel> joueurs;
    private List<MonsterModel> monstresDisponibles;
    private List<AttackModel> availableAttacks;
    private Terrain terrain;


    public GameModel(List<MonsterModel> monstresDisponibles, List<AttackModel> availableAttacks) {
        this.joueurs = new ArrayList<>();
        this.monstresDisponibles = monstresDisponibles;
        this.availableAttacks = availableAttacks;


        if (monstresDisponibles.size() < 6) {
            System.err.println("Not enough monsters to create a game !");
            return;
        }
        terrain = new Terrain();
    }

    public String addJoueur(PlayerModel player)
    {
        this.joueurs.add(player);
        return "Joueur " + player.getNom() + " ajouté \n";
    }

    public PlayerModel getJoueur(int index) {
        return joueurs.get(index);
    }

    private String executeAnteActions(){
        String result = "";
        this.terrain.decrement();
        for (PlayerModel player : joueurs) {
            Monster monster = player.getMonstreActif();
            result = monster.beforeTurnEffects(terrain);
        }
        return result;
    }

    private String executePostActions(){
        String result = "";
        for (PlayerModel player : joueurs) {
            Monster monster = player.getMonstreActif();
            result = monster.afterTurnEffects(terrain);
        }
        return result;
    }

    public String executerActions(Action actionJoueur1, Action actionJoueur2) {
        
        StringBuilder result = new StringBuilder();
        result.append(this.executeAnteActions());
        // Execute changes of monsters
        if (actionJoueur1.getType() == ActionType.CHANGER_MONSTRE) {
            joueurs.get(0).changerMonstreActif(actionJoueur1.getIndex());
        }
        if (actionJoueur2.getType() == ActionType.CHANGER_MONSTRE) {
            joueurs.get(1).changerMonstreActif(actionJoueur2.getIndex());
        }
        // Execute objets
        if (actionJoueur1.getType() == ActionType.OBJET && actionJoueur1.getObjet() != null) {
                result.append(actionJoueur1.getObjet().utiliser(joueurs.get(0).getMonstreActif()));
                joueurs.get(0).retirerObject(actionJoueur1.getObjet());
            }
        
        if (actionJoueur2.getType() == ActionType.OBJET && actionJoueur2.getObjet() != null) {
                result.append(actionJoueur2.getObjet().utiliser(joueurs.get(1).getMonstreActif()));
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

    private String attack(PlayerModel attaquant, PlayerModel defenseur, AttackModel attackAttaquant, AttackModel attackDefenseur) {
        StringBuilder result = new StringBuilder();
        if (attackAttaquant != null) {
            result.append(attaquant.attack(defenseur, attackAttaquant, terrain));
        } else {
            result.append(attaquant.getMonstreActif().takeDamages(10));
        }
        if (attackDefenseur != null) {
            result.append(defenseur.attack(attaquant, attackDefenseur, terrain));
        } else {
            result.append(defenseur.getMonstreActif().takeDamages(10));
        }
        return result.toString();
    }

    private int getInitiative(PlayerModel joueur1, PlayerModel joueur2) {
        int speedJoueur1 = joueur1.getMonstreActif().getVitesse();
        int speedJoueur2 = joueur2.getMonstreActif().getVitesse();

        return speedJoueur1 > speedJoueur2 ? 0 : 1;
    }

    public boolean estTermine() {
        for (PlayerModel joueur : joueurs) {
            if (joueur.estVaincu()) {
                return true;
            }
        }
        return false;
    }

    public PlayerModel getVainqueur() {
        if (joueurs.get(0).estVaincu()) {
            return joueurs.get(1);
        } else if (joueurs.get(1).estVaincu()) {
            return joueurs.get(0);
        } else {
            return null;
        }
    }

    public Terrain getTerrain() {
        return terrain;
    }

    public List<MonsterModel> getMonstresDisponibles() {
        return monstresDisponibles;
    }

    public List<AttackModel> getAttacksDisponibles() {
        return availableAttacks;
    }

    public List<PlayerModel> getPlayers() {
        return joueurs;
    }
}