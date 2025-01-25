package src.models;

import java.util.ArrayList;
import java.util.List;

import src.utils.Tuple;

public class PlayerModel {
    private String name;
    private List<Monster> monsters;
    private int monstreActifIndex;
    private List<ObjectModel> objets;

    public PlayerModel(String name) {
        this.name = name;
        this.monsters = new ArrayList<>();
        this.monstreActifIndex = 0;
        this.objets = new ArrayList<>(List.of(
                new ObjectModel("Potion", TypeObject.POTION),
                new ObjectModel("Medicament", TypeObject.MEDICAMENT)
        ));
    }

    public void setMonsters(List<MonsterModel> monsters, List<AttackModel> availableAttacks) {
        for (MonsterModel monster : monsters) {
            this.monsters.add(new Monster(monster, availableAttacks));
        }
    }

    public String getNom() {
        return name;
    }

    public List<Monster> getMonstres() {
        return monsters;
    }

    public Monster getMonstreActif() {
        return monsters.get(monstreActifIndex);
    }

    public void changerMonstreActif(int monstreIndex) {
        if (monstreIndex >= 0 && monstreIndex < monsters.size()) {
            this.monstreActifIndex = monstreIndex;
        }
    }

    public String attack(PlayerModel defenseur, AttackModel attack, Terrain terrain) {
        return getMonstreActif().attack(defenseur.getMonstreActif(), attack, terrain);
    }

    public boolean estVaincu() {
        return monsters.stream().allMatch(monstre -> monstre.getPV() <= 0);
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