package src;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import src.models.*;
import src.view.InterfaceConsole;

class MonsterSelectionTest {
    private GameModel gameModel;
    private PlayerModel player;
    private List<MonsterModel> availableMonsters;
    private List<MonsterModel> availableMonstersCopy;
    private List<AttackModel> availableAttacks;

    @BeforeEach
    void setUp() {
        availableMonsters = new ArrayList<>();
        availableAttacks = new ArrayList<>();

        // Add some dummy data for testing
        MonsterModel pikachu = new MonsterModel("Pikachu", "Electric");
        pikachu.populateStats(new int[]{100, 100}, new int[]{90, 90}, new int[]{55, 55}, new int[]{40, 40}, 0.05, 0.02, 0.01);

        MonsterModel cerapuca = new MonsterModel("Cerapuca", "Water");
        cerapuca.populateStats(new int[]{120, 120}, new int[]{70, 70}, new int[]{65, 65}, new int[]{65, 65}, 0.03, 0.04, 0.02);

        MonsterModel charizard = new MonsterModel("Charizard", "Fire");
        charizard.populateStats(new int[]{150, 150}, new int[]{80, 80}, new int[]{75, 75}, new int[]{85, 85}, 0.04, 0.03, 0.02);

        availableMonsters.add(pikachu);
        availableMonsters.add(cerapuca);
        availableMonsters.add(charizard);

        // Create a deep copy of the availableMonsters list
        availableMonstersCopy = new ArrayList<>();
        for (MonsterModel monster : availableMonsters) {
            MonsterModel copy = new MonsterModel(monster.getName(), monster.getType());
            copy.populateStats(monster.getHp(), monster.getSpeed(), monster.getAttack(), monster.getDefense(), monster.getParalysis(), monster.getFlood(), monster.getFall());
            availableMonstersCopy.add(copy);
        }

        availableAttacks.add(new AttackModel("Eclair", "Electric", 40, 10, 0.07));
        availableAttacks.add(new AttackModel("Charge", "Normal", 35, 15, 0.03));

        gameModel = new GameModel(availableMonsters, availableAttacks);
        player = new PlayerModel("Joueur 1");
    }

    @Test
    void testMonsterSelection() {
        String input = "0\n1\n0\n";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        Scanner scanner = new Scanner(in);
        InterfaceConsole interfaceConsole = new InterfaceConsole(scanner);

        // Simulate user input for selecting monsters
        List<MonsterModel> selectedMonsters = interfaceConsole.chooseMonsters(gameModel, player);

        assertNotNull(selectedMonsters, "The list of selected monsters should not be null.");
        assertEquals(3, selectedMonsters.size(), "The player should select exactly 3 monsters.");

        // Verify that the selected monsters are part of the available monsters
        for (MonsterModel selectedMonster : selectedMonsters) {
            assertTrue(availableMonstersCopy.contains(selectedMonster), "The selected monster should be in the list of available monsters.");
        }
    }

    @Test
    void testSetMonsters() {
        String input = "2\n0\n0\n";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        Scanner scanner = new Scanner(in);
        InterfaceConsole interfaceConsole = new InterfaceConsole(scanner);

        // Simulate user input for selecting monsters
        List<MonsterModel> selectedMonsters = interfaceConsole.chooseMonsters(gameModel, player);

        // Set the selected monsters to the player
        player.setMonsters(selectedMonsters, availableAttacks);

        // Verify that the player's monsters are set correctly
        List<Monster> playerMonsters = player.getMonstres();
        assertNotNull(playerMonsters, "The list of player's monsters should not be null.");
        assertEquals(3, playerMonsters.size(), "The player should have exactly 3 monsters.");

        // Verify that the monsters have the correct attacks
        for (Monster monster : playerMonsters) {
            List<AttackModel> listAttacks = new ArrayList<>();
            for (AttackModel atk : availableAttacks) {
                if (atk.getType().equals(monster.getType()) || atk.getType().equals("Normal")){
                    listAttacks.add(atk);
                }
            }
            assertEquals(listAttacks, monster.getAttacks(), "The monster should have the correct list of attacks.");
        }
    }
}
