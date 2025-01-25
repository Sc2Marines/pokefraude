package src;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import src.models.AttackModel;
import src.models.MonsterModel;
import src.utils.LecteurFichier;

class FileParserTest {
    private LecteurFichier fileParser = new LecteurFichier();

    private static final String MONSTERS = "Monster\n" + //
                "Name Pikachu\n" + //
                "Type Electric\n" + //
                "HP 110 141\n" + //
                "Speed 110 141\n" + //
                "Attack 75 106\n" + //
                "Defense 50 82\n" + //
                "Paralysis 0.2\n" + //
                "EndMonster\n" + //
                "Monster\n" + //
                "Name Cerapuca\n" + //
                "Type Water\n" + //
                "HP 119 150\n" + //
                "Attack 68 99\n" + //
                "Speed 63 94\n" + //
                "Defense 85 116\n" + //
                "Flood 0.25\n" + //
                "Fall 0.35\n" + //
                "EndMonster";

    private static final String ATTACKS = "Attack\n" + //
                "Name Eclair\n" + //
                "Type Electric\n" + //
                "Power 40\n" + //
                "NbUse 10\n" + //
                "Fail 0.07\n" + //
                "EndAttack\n" + //
                "Attack\n" + //
                "Name Charge\n" + //
                "Type Normal\n" + //
                "Power 35\n" + //
                "NbUse 15\n" + //
                "Fail 0.03\n" + //
                "EndAttack";

    @Test
    void MonsterParseSuccessfully() throws IOException {
        // Create a temporary file and put the text into it
        File tempFile = File.createTempFile("monsters", ".txt");
        tempFile.deleteOnExit();
        try (FileWriter writer = new FileWriter(tempFile)) {
            writer.write(MONSTERS);
        }

        List<MonsterModel> monsters = fileParser.lireMonstres(tempFile.getAbsolutePath());

        assertNotNull(monsters, "The list of mosnters must not be zero.");
        assertEquals(2, monsters.size(), "The list must contain exactly 2 monsters.");

        MonsterModel tmpMonster1 = new MonsterModel("Pikachu", "Electric");
        tmpMonster1.populateStats(new int[]{110, 141}, new int[]{110, 141}, new int[]{75, 106}, new int[]{50, 82}, 0.2, 0.0, 0.0);

        MonsterModel tmpMonstre2 = new MonsterModel("Cerapuca", "Water");
        tmpMonstre2.populateStats(new int[]{119, 150}, new int[]{63, 94}, new int[]{68, 99}, new int[]{85, 116}, 0.0, 0.25, 0.35);

        assertEquals(tmpMonster1, monsters.get(0), "The first monster must be equal to the artificially created monster.");
        assertEquals(tmpMonstre2, monsters.get(1), "The second monster must be equal to the artificially created monster.");

        System.out.println("All assertions are passed, the test is successful.");
    }

    @Test 
    void AttaquesParsedSUccessfully() throws IOException {
        File tempFile = File.createTempFile("attacks", ".txt");
        try (FileWriter writer = new FileWriter(tempFile)) {
            writer.write(ATTACKS);
        }


        
        List<AttackModel> attacks = fileParser.lireAttaques(tempFile.getAbsolutePath());
        assertNotNull(attacks, "The list of attacks does not have to be zero.");
        assertEquals(2, attacks.size(), "The list must contain exactly 2 attacks.");

        AttackModel tmpAttack1 = new AttackModel("Eclair", "Electric", 40, 10, 0.07);
        AttackModel tmpAttack2 = new AttackModel("Charge", "Normal", 35, 15, 0.03);
        assertEquals(tmpAttack1, attacks.get(0), "The first attack must be equal to the artificially created attack.");
        assertEquals(tmpAttack2, attacks.get(1), "The second attack must be equal to the artificially created attack.");
        
        System.out.println("All assertions are passed, the test is successful.");
    }
}