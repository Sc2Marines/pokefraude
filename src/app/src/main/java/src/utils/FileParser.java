package src.utils;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import src.models.AttackModel;
import src.models.MonsterModel;

public class FileParser {

    // Regex to extract data from the file
    private static final Pattern MONSTER_PATTERN = Pattern.compile("Monster.*?EndMonster", Pattern.DOTALL);
    private static final Pattern ATTACK_PATTERN = Pattern.compile("Attack.*?EndAttack", Pattern.DOTALL);

    private static final Pattern NAME_PATTERN = Pattern.compile("Name\\s+(\\w+)");
    private static final Pattern TYPE_PATTERN = Pattern.compile("Type\\s+(\\w+)");
    private static final Pattern HP_PATTERN = Pattern.compile("HP\\s+(\\d+)\\s+(\\d+)");
    private static final Pattern SPEED_PATTERN = Pattern.compile("Speed\\s+(\\d+)\\s+(\\d+)");
    private static final Pattern ATTACK_PATTERN_PARAMS = Pattern.compile("Attack\\s+(\\d+)\\s+(\\d+)");
    private static final Pattern DEFENSE_PATTERN = Pattern.compile("Defense\\s+(\\d+)\\s+(\\d+)");
    private static final Pattern PARALYSIS_PATTERN = Pattern.compile("Paralysis\\s+(\\d+\\.\\d+)");
    private static final Pattern FLOOD_PATTERN = Pattern.compile("Flood\\s+(\\d+\\.\\d+)");
    private static final Pattern FALL_PATTERN = Pattern.compile("Fall\\s+(\\d+\\.\\d+)");

    private static final Pattern POWER_PATTERN = Pattern.compile("Power\\s+(\\d+)");
    private static final Pattern NBUSE_PATTERN = Pattern.compile("NbUse\\s+(\\d+)");
    private static final Pattern FAIL_PATTERN_PARAMS = Pattern.compile("Fail\\s+(\\d+\\.\\d+)");

    public List<MonsterModel> lireMonstres(String filePath) {
        List<MonsterModel> monstres = new ArrayList<>();
        String fileContent = readFile(filePath);
        if (fileContent == null)
            return Collections.emptyList();

        Matcher matcher = MONSTER_PATTERN.matcher(fileContent);
        while (matcher.find()) {
            String monsterData = matcher.group();

            String name = extractValue(monsterData, NAME_PATTERN);
            String type = extractValue(monsterData, TYPE_PATTERN);
            int[] hp = extractValueIntArray(monsterData, HP_PATTERN);
            int[] speed = extractValueIntArray(monsterData, SPEED_PATTERN);
            int[] attack = extractValueIntArray(monsterData, ATTACK_PATTERN_PARAMS);
            int[] defense = extractValueIntArray(monsterData, DEFENSE_PATTERN);
            double paralysis = extractValueDouble(monsterData, PARALYSIS_PATTERN);
            double flood = extractValueDouble(monsterData, FLOOD_PATTERN);
            double fall = extractValueDouble(monsterData, FALL_PATTERN);

            MonsterModel tmpMonstre = new MonsterModel(name, type);
            tmpMonstre.populateStats(hp, speed, attack, defense, paralysis, flood, fall);
            monstres.add(tmpMonstre);
        }
        return monstres;
    }

    public List<AttackModel> lireAttaques(String filePath) {
        List<AttackModel> attacks = new ArrayList<>();
        String fileContent = readFile(filePath);
        if (fileContent == null)
            return Collections.emptyList();

        Matcher matcher = ATTACK_PATTERN.matcher(fileContent);
        while (matcher.find()) {
            String attackData = matcher.group();

            String name = extractValue(attackData, NAME_PATTERN);
            String type = extractValue(attackData, TYPE_PATTERN);
            int power = extractValueInt(attackData, POWER_PATTERN);
            int nbUse = extractValueInt(attackData, NBUSE_PATTERN);
            double fail = extractValueDouble(attackData, FAIL_PATTERN_PARAMS);

            attacks.add(new AttackModel(name, type, power, nbUse, fail));
        }
        return attacks;
    }

    private String readFile(String filePath) {
        StringBuilder content = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                content.append(line).append("\n");
            }
            return content.toString();
        } catch (IOException e) {
            System.err.println("Error while reading file :" + filePath);
            return null;
        }
    }

    private String extractValue(String data, Pattern pattern) {
        Matcher matcher = pattern.matcher(data);
        if (matcher.find()) {
            return matcher.group(1);
        }
        return null;
    }

    private int extractValueInt(String data, Pattern pattern) {
        Matcher matcher = pattern.matcher(data);
        if (matcher.find()) {
            return Integer.parseInt(matcher.group(1));
        }
        return 0;
    }

    private double extractValueDouble(String data, Pattern pattern) {
        Matcher matcher = pattern.matcher(data);
        if (matcher.find()) {
            return Double.parseDouble(matcher.group(1));
        }
        return 0.0;
    }

    private int[] extractValueIntArray(String data, Pattern pattern) {
        Matcher matcher = pattern.matcher(data);
        if (matcher.find()) {
            return new int[] { Integer.parseInt(matcher.group(1)), Integer.parseInt(matcher.group(2)) };
        }
        return new int[] { 0, 0 };
    }

    public static void main(String[] args) {
        FileParser lecteur = new FileParser();
        List<MonsterModel> monstres = lecteur.lireMonstres("src/app/src/main/java/src/monsters.txt");
        List<AttackModel> attacks = lecteur.lireAttaques("src/app/src/main/java/src/attacks.txt");
        if (monstres == null || attacks == null)
            return;
        for (MonsterModel monstre : monstres) {
            System.out.println(monstre);
        }
        for (AttackModel attack : attacks) {
            System.out.println(attack);
        }
    }

}