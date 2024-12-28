package src;

import java.util.ArrayList;
import java.util.List;

public class ModeleJoueur {
    private String nom;
    private List<Monstre> monstres;
    private int monstreActifIndex;
    private List<ModeleObjet> objets;

    public ModeleJoueur(String nom, List<ModeleMonstre> monstresDisponibles, List<ModeleAttaque> attaquesDisponibles) {
        this.nom = nom;
        this.monstres = new ArrayList<>();
         for (ModeleMonstre monstreDispo : monstresDisponibles){
            this.monstres.add(new Monstre(monstreDispo, attaquesDisponibles));
        }
        this.monstreActifIndex = 0;
        this.objets = List.of(new ModeleObjet("Potion", TypeObjet.POTION),
                new ModeleObjet("Medicament", TypeObjet.MEDICAMENT));
    }

    public String getNom() {
        return nom;
    }

    public List<Monstre> getMonstres() {
        return monstres;
    }

    public Monstre getMonstreActif() {
        return monstres.get(monstreActifIndex);
    }

    public void changerMonstreActif(int monstreIndex) {
        if (monstreIndex >= 0 && monstreIndex < monstres.size()) {
            this.monstreActifIndex = monstreIndex;
        }
    }

    public void attaquer(ModeleJoueur defenseur, ModeleAttaque attaque, Terrain terrain) {
        getMonstreActif().attaquer(defenseur.getMonstreActif(), attaque, terrain);
    }

    public boolean estVaincu() {
        return monstres.stream().allMatch(monstre -> monstre.getPV() <= 0);
    }

    public List<ModeleObjet> getObjets() {
        return objets;
    }
}