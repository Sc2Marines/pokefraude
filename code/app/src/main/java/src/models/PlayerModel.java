package src.models;

import java.util.ArrayList;
import java.util.List;

import src.models.Terrain.TypeTerrain;
import src.utils.Tuple;

public class PlayerModel {
    private String name;
    private List<Monster> monsters;
    private int monstreActifIndex;
    private List<ObjectModel> objets;

    /**
     * The default constructor for a player.
     * @param name The name of the player.
     */
    public PlayerModel(String name) {
        this.name = name;
        this.monsters = new ArrayList<>();
        this.monstreActifIndex = 0;
        this.objets = new ArrayList<>(List.of(
                new ObjectModel("Potion", TypeObject.POTION),
                new ObjectModel("Medicament", TypeObject.MEDICAMENT)
        ));
    }

    /**
     * set the player's monsters.
     * @param monsters The list of monsters.
     * @param availableAttacks The list of attacks.
     */
     public void setMonsters(List<MonsterModel> monsters, List<AttackModel> availableAttacks) {
        for (MonsterModel monsterModel : monsters) {
            this.monsters.add(new Monster(monsterModel, availableAttacks));
        }
    }


    /**
     * Get the player's name.
     * @return The player's name.
     */
    public String getNom() {
        return name;
    }

    /**
     * The list of player's monsters
     * @return The list of monsters
     */
    public List<Monster> getMonstres() {
        return monsters;
    }

    /**
     * Get the currente active player's monster. 
     * @return A monster
     */
    public Monster getMonstreActif() {
        if (monsters.isEmpty())
            return null;
        
        if (monsters.get(monstreActifIndex).getPV() <= 0) {
            for (int i = 0; i < monsters.size(); i++) {
                if (monsters.get(i).getPV() > 0) {
                    monstreActifIndex = i;
                    break;
                }
            }
        }
        return monsters.get(monstreActifIndex);
    }

    /**
     * Change the current player's active monster.
     * @param monstreIndex The index of the monster to set as the current monster.
     * @param terrain The current terrain.
     * @param actualEnnemyMonster The actual ennemy monster.
     */
    public void changerMonstreActif(Monster monstre, Terrain terrain, Monster actualEnnemyMonster) {
        int monstreIndex = 0;
        for (int i = 0; i < this.getMonstres().size(); i++) {
            if (this.getMonstres().get(i).equals(monstre)) {
                monstreIndex = i;
            }
        }
        if (monstreIndex >= 0 && monstreIndex < monsters.size() && monsters.get(monstreIndex).getPV() > 0) {
            Monster activeMonster = this.getMonstreActif(); 
            activeMonster.setTriggerFlood(false);
            if (activeMonster.haveTriggerFlood() && !actualEnnemyMonster.haveTriggerFlood()){
                terrain.modify(TypeTerrain.NORMAL);
            }
            this.monstreActifIndex = monstreIndex;
        }
    }

    /**
     * Attack method
     * @param defender The defensive player
     * @param attack The attack
     * @param terrain The current terrain
     * @return The attack result string.
     */
    public String attack(PlayerModel defender, AttackModel attack, Terrain terrain) {
        return getMonstreActif().attack(defender.getMonstreActif(), attack, terrain);
    }

    /**
     * Get if the player is vainquish.
     * @return If all the monsters are dead.
     */
    public boolean estVaincu() {
        return monsters.stream().allMatch(monstre -> monstre.getPV() <= 0);
    }

    /**
     * Remove an object from the inventory.
     * @param objet The object to remove.
     */
    public void retirerObject(ObjectModel objet) {
        for (ObjectModel obj : this.objets) {
            if (obj.getType() == objet.getType()) {
                this.objets.remove(obj);
                break; // Exit the loop after removing the first matching object
            }
        }
    }

    /**
     * Get all the objects count, must be used ro simplify the actions in any interface.
     * @return The list of all objects aggregated by theirs types stored in a list of tuples.
     */
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

    /**
     * Return the list of all objects owned by the player.
     * @return The list of objects 
     */
    public List<ObjectModel> getObjets() {
        return objets;
    }
}