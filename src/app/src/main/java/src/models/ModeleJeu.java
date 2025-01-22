package src.models;


import java.util.ArrayList;
import java.util.List;

public class ModeleJeu {
    private List<ModeleJoueur> joueurs;
    private List<ModeleMonstre> monstresDisponibles;
    private List<ModeleAttaque> attaquesDisponibles;
    private Terrain terrain;


    public ModeleJeu(List<ModeleMonstre> monstresDisponibles, List<ModeleAttaque> attaquesDisponibles) {
        this.joueurs = new ArrayList<>();
        this.monstresDisponibles = monstresDisponibles;
        this.attaquesDisponibles = attaquesDisponibles;

        // Create 2 players with a default set of 3 random monsters
        List<ModeleMonstre> monstresJoueur1 = new ArrayList<>();
        List<ModeleMonstre> monstresJoueur2 = new ArrayList<>();
        if (monstresDisponibles.size() < 6) {
            System.err.println("Not enough monsters to create a game !");
            return;
        }
        for (int i = 0; i < 3; i++) {
            monstresJoueur1.add(monstresDisponibles.get(i));
            monstresJoueur2.add(monstresDisponibles.get(i + 3));
        }


        joueurs.add(new ModeleJoueur("Joueur 1", monstresJoueur1, attaquesDisponibles));
        joueurs.add(new ModeleJoueur("Joueur 2", monstresJoueur2, attaquesDisponibles));
        terrain = new Terrain();

    }

    public ModeleJoueur getJoueur(int index) {
        return joueurs.get(index);
    }

    public void executerActions(Action actionJoueur1, Action actionJoueur2) {
        // Execute changes of monsters
        if (actionJoueur1.getType() == ActionType.CHANGER_MONSTRE) {
            joueurs.get(0).changerMonstreActif(actionJoueur1.getIndex());
        }
        if (actionJoueur2.getType() == ActionType.CHANGER_MONSTRE) {
            joueurs.get(1).changerMonstreActif(actionJoueur2.getIndex());
        }
        // Execute objets
        if (actionJoueur1.getType() == ActionType.OBJET && actionJoueur1.getObjet() != null) {
                actionJoueur1.getObjet().utiliser(joueurs.get(0).getMonstreActif());
            }
        
        if (actionJoueur2.getType() == ActionType.OBJET && actionJoueur2.getObjet() != null) {
                actionJoueur2.getObjet().utiliser(joueurs.get(1).getMonstreActif());
            }
        
        //Execute attacks
        if (actionJoueur1.getType() == ActionType.ATTAQUE && actionJoueur2.getType() == ActionType.ATTAQUE) {
            int initJoueur = getInitiative(joueurs.get(0), joueurs.get(1));
            if (initJoueur == 0) {
                attaquer(joueurs.get(0), joueurs.get(1), actionJoueur1.getAttaque(), actionJoueur2.getAttaque());
            } else {
                attaquer(joueurs.get(1), joueurs.get(0), actionJoueur2.getAttaque(), actionJoueur1.getAttaque());
            }
        } else if (actionJoueur1.getType() == ActionType.ATTAQUE) {
            attaquer(joueurs.get(0), joueurs.get(1), actionJoueur1.getAttaque(), null);
        } else if (actionJoueur2.getType() == ActionType.ATTAQUE) {
            attaquer(joueurs.get(1), joueurs.get(0), actionJoueur2.getAttaque(), null);
        }
    }

    private void attaquer(ModeleJoueur attaquant, ModeleJoueur defenseur, ModeleAttaque attaqueAttaquant, ModeleAttaque attaqueDefenseur) {
        if (attaqueAttaquant != null) {
            attaquant.attaquer(defenseur, attaqueAttaquant, terrain);
        } else {
            attaquant.getMonstreActif().subirDegats(10);
        }
        if (attaqueDefenseur != null) {
            defenseur.attaquer(attaquant, attaqueDefenseur, terrain);
        } else {
            defenseur.getMonstreActif().subirDegats(10);
        }
    }

    private int getInitiative(ModeleJoueur joueur1, ModeleJoueur joueur2) {
        int speedJoueur1 = joueur1.getMonstreActif().getVitesse();
        int speedJoueur2 = joueur2.getMonstreActif().getVitesse();

        return speedJoueur1 > speedJoueur2 ? 0 : 1;
    }

    public boolean estTermine() {
        for (ModeleJoueur joueur : joueurs) {
            if (joueur.estVaincu()) {
                return true;
            }
        }
        return false;
    }

    public ModeleJoueur getVainqueur() {
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

    public List<ModeleMonstre> getMonstresDisponibles() {
        return monstresDisponibles;
    }

    public List<ModeleAttaque> getAttaquesDisponibles() {
        return attaquesDisponibles;
    }

    public List<ModeleJoueur> getJoueurs() {
        return joueurs;
    }
}