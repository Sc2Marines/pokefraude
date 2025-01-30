package src;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

import src.models.*;
import src.utils.Tuple;

class ObjectsTest {
    private PlayerModel player;
    private List<MonsterModel> availableMonsters;
    private List<AttackModel> availableAttacks;

    @BeforeEach
    void setUp() {
        availableMonsters = new ArrayList<>();
        availableAttacks = new ArrayList<>();

        // Add some dummy data for testing
        MonsterModel pikachu = new MonsterModel("Pikachu", "Electric");
        pikachu.populateStats(new int[]{100, 100}, new int[]{90, 90}, new int[]{55, 55}, new int[]{40, 40}, 0.05, 0.02, 0.01);
        
        availableMonsters.add(pikachu);

        MonsterModel cerapuca = new MonsterModel("Cerapuca", "Water");
        cerapuca.populateStats(new int[]{120, 120}, new int[]{70, 70}, new int[]{65, 65}, new int[]{65, 65}, 0.03, 0.04, 0.02);
        availableMonsters.add(cerapuca);

        availableAttacks.add(new AttackModel("Eclair", "Electric", 40, 10, 0.07));
        availableAttacks.add(new AttackModel("Charge", "Normal", 35, 15, 0.03));

        player = new PlayerModel("Joueur 1");
        player.setMonsters(availableMonsters, availableAttacks);
        
    }

    @Test
    void testObjectCreation() {
        List<ObjectModel> objects = player.getObjets();
        assertNotNull(objects, "La liste des objets ne devrait pas être nulle.");
        assertEquals(2, objects.size(), "Le joueur devrait avoir exactement 2 objets.");

        ObjectModel potion = objects.get(0);
        ObjectModel medicament = objects.get(1);

        assertEquals("Potion", potion.getNom(), "Le premier objet devrait être une Potion.");
        assertEquals(TypeObject.POTION, potion.getType(), "Le premier objet devrait être de type POTION.");

        assertEquals("Medicament", medicament.getNom(), "Le deuxième objet devrait être un Médicament.");
        assertEquals(TypeObject.MEDICAMENT, medicament.getType(), "Le deuxième objet devrait être de type MEDICAMENT.");
    }

    @Test
    void testObjectUsage() {
        List<ObjectModel> objects = player.getObjets();
        ObjectModel potion = objects.get(0);
        ObjectModel medicament = objects.get(1);

        Monster activeMonster = player.getMonstreActif();
        activeMonster.appliquerEtat();
        activeMonster.takeDamages(25);
        // Use Potion
        potion.use(activeMonster);
        assertEquals(100, activeMonster.getPV(), "The monster's HP should be 100 after using a Potion.");

        // Remove Potion
        player.retirerObject(potion);
        assertEquals(1, player.getObjets().size(), "The player should have exactly 1 object after removing the Potion.");

        // Use Medicament
        medicament.use(activeMonster);
        assertEquals(true, activeMonster.aAucunEtat(), "The monster must have no effect.");

        // Remove Medicament
        player.retirerObject(medicament);
        assertEquals(0, player.getObjets().size(), "The player should have exactly 0 objects after removing the Medicament.");
    }

    @Test
    void testGetObjectCount() {
        List<ObjectModel> objects = player.getObjets();
        objects.add(new ObjectModel("Potion", TypeObject.POTION));
        objects.add(new ObjectModel("Potion", TypeObject.POTION));
        objects.add(new ObjectModel("Medicament", TypeObject.MEDICAMENT));

        List<Tuple<ObjectModel, Integer>> objectCount = player.getObjectCount();
        assertNotNull(objectCount, "The list of object counts should not be null.");
        assertEquals(2, objectCount.size(), "The player should have exactly 2 types of objects.");

        for (Tuple<ObjectModel, Integer> tuple : objectCount) {
            if (tuple.getFirst().getType() == TypeObject.POTION) {
                assertEquals(3, tuple.getSecond(), "The player should have 3 Potions.");
            } else if (tuple.getFirst().getType() == TypeObject.MEDICAMENT) {
                assertEquals(2, tuple.getSecond(), "The player should have 2 Medicaments.");
            }
        }
    }
}