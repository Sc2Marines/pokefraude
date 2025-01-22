package src.models;

import java.util.ArrayList;
import java.util.List;

public class PlayerModel {
    private String nom;
    private List<Monster> monstres;
    private int monstreActifIndex;
    private List<ObjectModel> objets;

    public PlayerModel(String nom, List<MonsterModel> monstresDisponibles, List<AttackModel> attaquesDisponibles) {
        this.nom = nom;
        this.monstres = new ArrayList<>();
         for (MonsterModel monstreDispo : monstresDisponibles){
            this.monstres.add(new Monster(monstreDispo, attaquesDisponibles));
        }
        this.monstreActifIndex = 0;
        this.objets = List.of(new ObjectModel("Potion", TypeObject.POTION),
                new ObjectModel("Medicament", TypeObject.MEDICAMENT));
    }

    public String getNom() {
        return nom;
    }

    public List<Monster> getMonstres() {
        return monstres;
    }

    public Monster getMonstreActif() {
        return monstres.get(monstreActifIndex);
    }

    public void changerMonstreActif(int monstreIndex) {
        if (monstreIndex >= 0 && monstreIndex < monstres.size()) {
            this.monstreActifIndex = monstreIndex;
        }
    }

    public void attaquer(PlayerModel defenseur, AttackModel attaque, Terrain terrain) {
        getMonstreActif().attaquer(defenseur.getMonstreActif(), attaque, terrain);
    }

    public boolean estVaincu() {
        return monstres.stream().allMatch(monstre -> monstre.getPV() <= 0);
    }

    public List<ObjectModel> getObjets() {
        return objets;
    }
}