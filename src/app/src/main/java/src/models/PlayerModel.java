package src.models;

import java.util.ArrayList;
import java.util.List;

import src.utils.Tuple;

public class PlayerModel {
    private String nom;
    private List<Monster> monstres;
    private int monstreActifIndex;
    private List<ObjectModel> objets;

    public PlayerModel(String nom, List<MonsterModel> monstresDisponibles, List<AttackModel> attaquesDisponibles) {
        this.nom = nom;
        this.monstres = new ArrayList<>();
        for (MonsterModel monstreDispo : monstresDisponibles) {
            this.monstres.add(new Monster(monstreDispo, attaquesDisponibles));
        }
        this.monstreActifIndex = 0;
        this.objets = new ArrayList<>(List.of(
                new ObjectModel("Potion", TypeObject.POTION),
                new ObjectModel("Medicament", TypeObject.MEDICAMENT)
        ));
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

    public String attaquer(PlayerModel defenseur, AttackModel attaque, Terrain terrain) {
        return getMonstreActif().attaquer(defenseur.getMonstreActif(), attaque, terrain);
    }

    public boolean estVaincu() {
        return monstres.stream().allMatch(monstre -> monstre.getPV() <= 0);
    }

    public void retirerObject(ObjectModel objet) {
        for (ObjectModel obj : this.objets) {
            if (obj.getType() == objet.getType()) {
                this.objets.remove(obj);
                break; // Exit the loop after removing the first matching object
            }
        }
    }

    public List<Tuple<ObjectModel, Integer>> getObjectCount() {
        List<ObjectModel> playerObjects = this.getObjets();
        List<Tuple<ObjectModel, Integer>> listElements = new ArrayList<>();
        for (ObjectModel objectModel : playerObjects) {
            boolean present = false;
            for (Tuple<ObjectModel, Integer> tuple : listElements) {
                if (tuple.getFirst().getType() == objectModel.getType()) {
                    present = true;
                    tuple.setSecond(tuple.getSecond() + 1);
                    break;
                }
            }
            if (!present) {
                Tuple<ObjectModel, Integer> nouvelleEntree = new Tuple<>(objectModel, 1);
                listElements.add(nouvelleEntree);
            }
        }
        return listElements;
    }

    public List<ObjectModel> getObjets() {
        return objets;
    }
}