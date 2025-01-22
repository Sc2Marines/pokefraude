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

class LecteurFichierTest {
    private LecteurFichier lecteurFichier = new LecteurFichier();

    private static final String MONSTRES = "Monster\n" + //
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

    private static final String ATTAQUES = "Attack\n" + //
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
    void MonstresParseSuccessfully() throws IOException {
        // Create a temporary file and put the text into it
        File tempFile = File.createTempFile("monsters", ".txt");
        tempFile.deleteOnExit();
        try (FileWriter writer = new FileWriter(tempFile)) {
            writer.write(MONSTRES);
        }

        List<MonsterModel> monstres = lecteurFichier.lireMonstres(tempFile.getAbsolutePath());

        assertNotNull(monstres, "La liste des mosntres ne doit pas être nulle.");
        assertEquals(2, monstres.size(), "La liste doit contenir exactement 2 monstres.");

        MonsterModel tmpMonstre1 = new MonsterModel("Pikachu", "Electric");
        tmpMonstre1.populateStats(new int[]{110, 141}, new int[]{110, 141}, new int[]{75, 106}, new int[]{50, 82}, 0.2, 0.0, 0.0);

        MonsterModel tmpMonstre2 = new MonsterModel("Cerapuca", "Water");
        tmpMonstre2.populateStats(new int[]{119, 150}, new int[]{63, 94}, new int[]{68, 99}, new int[]{85, 116}, 0.0, 0.25, 0.35);

        assertEquals(tmpMonstre1, monstres.get(0), "Le premier monstre doit être égal au monstre crée artificiellement.");
        assertEquals(tmpMonstre2, monstres.get(1), "Le deuxième monstre doit être égal au monstre crée artificiellement.");

        System.out.println("Toutes les assertions sont passées, le test est réussi.");
    }

    @Test 
    void AttaquesParsedSUccessfully() throws IOException {
        File tempFile = File.createTempFile("attaques", ".txt");
        try (FileWriter writer = new FileWriter(tempFile)) {
            writer.write(ATTAQUES);
        }


        
        List<AttackModel> attaques = lecteurFichier.lireAttaques(tempFile.getAbsolutePath());
        assertNotNull(attaques, "La liste des attaques ne doit pas être nulle.");
        assertEquals(2, attaques.size(), "La liste doit contenir exactement 2 attaques.");

        AttackModel tmpAttaque1 = new AttackModel("Eclair", "Electric", 40, 10, 0.07);
        AttackModel tmpAttaque2 = new AttackModel("Charge", "Normal", 35, 15, 0.03);
        assertEquals(tmpAttaque1, attaques.get(0), "La première attaque doit être égale à l'attaque crée artificellement.");
        assertEquals(tmpAttaque2, attaques.get(1), "La deuxième attaque doit être égale à l'attaque crée artificellement.");
        
        System.out.println("Toutes les assertions sont passées, le test est réussi.");
    }
}