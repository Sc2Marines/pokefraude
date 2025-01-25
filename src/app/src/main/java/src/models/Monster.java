package src.models;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import src.utils.Tuple;

public class Monster {
    private String nom;
    private String type;
    private int pv;
    private int pvMax;
    private int attaque;
    private int defense;
    private int vitesse;
    private List<AttackModel> attaques;

    private boolean estParalyse;
    private boolean estEmpoisonne;
    private boolean estBrulee;

    private int tourParalyse = 0;

    private static final String FOUDRE = "Foudre";
    private static final String FEU = "Feu";
    private static final String EAU = "Eau";
    private static final String NATURE = "Nature";
    private static final String TERRE = "Terre";
    //store advantages in a map (way more cleaner way to do it -> can be reused) (Need to be automated in a txt file)
    private static final Map<String, Map<String, Double>> TYPE_ADVANTAGES = new HashMap<>();
    static {
        Map<String, Double> eauMap = new HashMap<>();
        eauMap.put(FEU, 2.0);
        eauMap.put(FOUDRE, 0.5);
        TYPE_ADVANTAGES.put(EAU, eauMap);

        Map<String, Double> feuMap = new HashMap<>();
        feuMap.put(NATURE, 2.0);
        feuMap.put(EAU, 0.5);
        TYPE_ADVANTAGES.put(FEU, feuMap);

        Map<String, Double> natureMap = new HashMap<>();
        natureMap.put(TERRE, 2.0);
        natureMap.put(FEU, 0.5);
        TYPE_ADVANTAGES.put(NATURE, natureMap);

        Map<String, Double> terreMap = new HashMap<>();
        terreMap.put(FOUDRE, 2.0);
        terreMap.put(NATURE, 0.5);
        TYPE_ADVANTAGES.put(TERRE, terreMap);

        Map<String, Double> foudreMap = new HashMap<>();
        foudreMap.put(EAU, 2.0);
        foudreMap.put(TERRE, 0.5);
        TYPE_ADVANTAGES.put(FOUDRE, foudreMap);
    }

    // make the random truely random and compliant
    private Random random = new Random();

    public Monster(MonsterModel modeleMonstre, List<AttackModel> attaquesDisponibles) {
        this.nom = modeleMonstre.getName();
        this.type = modeleMonstre.getType();
        this.pv = getRandomValue(modeleMonstre.getHp());
        this.pvMax = pv;
        this.attaque = getRandomValue(modeleMonstre.getAttack());
        this.defense = getRandomValue(modeleMonstre.getDefense());
        this.vitesse = getRandomValue(modeleMonstre.getSpeed());
        this.attaques = new ArrayList<>();
        this.estParalyse = false;
        this.estBrulee = false;
        this.estEmpoisonne = false;

        for (AttackModel modelAttaque : attaquesDisponibles) {
            if (modelAttaque.getType().equals(this.type) || modelAttaque.getType().equals("Normal")) {
                this.attaques.add(modelAttaque);
                if (this.attaques.size() == 4)
                    break;
            }
        }

    }

    private int getRandomValue(int[] values) {
        return this.random.nextInt(values[1] - values[0] + 1) + values[0];
    }

    public String getNom() {
        return nom;
    }

    public String getType() {
        return type;
    }

    public int getPV() {
        return pv;
    }

    public int getVitesse() {
        return vitesse;
    }

    public int getAttaque() {
        return attaque;
    }

    public int getDefense() {
        return defense;
    }

    public List<AttackModel> getAttaques() {
        return attaques;
    }

    public String subirDegats(int degats) {
        if (estBrulee) {
            degats += getAttaque() / 10;
        }
        this.pv -= degats;
        if (this.pv < 0) {
            this.pv = 0;
        }
        return nom + " a subit " + degats + " degats, il lui reste " + pv + "PV" +"\n";
    }

    public String attaquer(Monster cible, AttackModel attaque, Terrain terrain) {
        StringBuilder result = new StringBuilder();
        Tuple<Boolean, String> paralyse = estSortiParalysie();
        result.append(paralyse.getSecond());
        if (estParalyse && !(boolean)paralyse.getFirst()) {
            result.append(nom + " est paralyse et ne peut attaquer" +"\n"); 
        }
        if (hasFail(attaque.getFail())) {
            result.append(nom + " a rate son attaque : " + attaque.getName() +"\n"); 
        }
        int degats = calculerDegats(cible, attaque);
        result.append(cible.subirDegats(degats));
        result.append(appliquerEffetAttaque(cible, terrain));
        result.append(nom + " attaque avec : " + attaque.getName() +"\n");
        return result.toString();
    }

    private boolean hasFail(double fail) {
        return this.random.nextDouble() < fail;
    }

    private int calculerDegats(Monster cible, AttackModel attaque) {
        double coef = 0.85 + (1.0 - 0.85) * this.random.nextDouble();

        double avantage = calculerAvantage(cible, attaque);

        return (int) (((11.0 * this.attaque * attaque.getPower()) / (25.0 * cible.getDefense()) + 2) * avantage
                * coef);
    }

    private double calculerAvantage(Monster cible, AttackModel attaque) {
        String attaqueType = attaque.getType();
        String cibleType = cible.getType();

        if (TYPE_ADVANTAGES.containsKey(attaqueType) && TYPE_ADVANTAGES.get(attaqueType).containsKey(cibleType)) {
            return TYPE_ADVANTAGES.get(attaqueType).get(cibleType);
        }
        return 1.0;
    }


    private String appliquerEffetAttaque(Monster cible, Terrain terrain) {
        String result = "";
        switch (this.type) {
            case FOUDRE:
                result = paralyser(cible);
                break;
            case EAU:
                result = inonder(terrain, cible);
                break;
            case FEU:
                result = bruler(cible);
                break;
            case NATURE:
                result = empoisonner(cible);
                break;
            default:
                break;
        }
        return result;
    }

    public String soigner(int heal) {
        this.pv += heal;
        if (this.pv > pvMax) {
            this.pv = pvMax;
        }
        return nom + " a recupere " + heal + "PV il a maintenant " + pv + "PV" +"\n";
    }

    public String guerir() {
        this.estBrulee = false;
        this.estEmpoisonne = false;
        this.estParalyse = false;
        return nom + " est guerri de ses effets de status" +"\n";
    }

    private String empoisonner(Monster cible) {
        String result = "";
        if (!cible.estEmpoisonne) {
            cible.estEmpoisonne = true;
            result = cible.nom + " est empoisonne";
        }
        return result;
    }

    private String bruler(Monster cible) {
        String result = "";
        if (!cible.estBrulee) {
            cible.estBrulee = true;
            result = cible.nom + " est brule";
        }
        return result;
    }

    private String inonder(Terrain terrain, Monster cible) {
        StringBuilder result = new StringBuilder();
        if (terrain.getEtat() != Terrain.TypeTerrain.INONDE) {
            terrain.modifier(Terrain.TypeTerrain.INONDE);
            
            result.append("Le terrain est inonde" +"\n");
            result.append(chute(cible));
        }
        return result.toString();
    }

    private String chute(Monster cible) {
        StringBuilder result = new StringBuilder();
        if (Math.random() < 0.3) {
            result.append(cible.subirDegats(cible.getAttaque() / 4));
            result.append(cible.nom + " a chute" +"\n");
        }
        return result.toString();

    }

    private String paralyser(Monster cible) {
        String result = "";
        if (!cible.estParalyse) {
            cible.estParalyse = true;
            result = cible.nom + " est paralyse";
        }
        return result;
    }

    public Tuple<Boolean, String> estSortiParalysie() {
        Tuple<Boolean, String> result = new Tuple<>(false, "");
        tourParalyse++;
        if (tourParalyse >= 6) {
            estParalyse = false;
            tourParalyse = 0;
            result.setFirst(true);
            return result;
        }
        double probabilite = tourParalyse / 6.0;
        if (this.random.nextDouble() < probabilite) {
            estParalyse = false;
            tourParalyse = 0;
            result.setSecond(nom + " n'est plus paralyse" +"\n");
            result.setFirst(true);
            return result;
        }
        return result;
    }

    public String debutTour() {
        StringBuilder result = new StringBuilder();
        if (estBrulee || estEmpoisonne) {
            result.append(subirDegats(getAttaque() / 10));
            
        }
        if (type.equals(NATURE) && !estBrulee && !estEmpoisonne && !estParalyse && pv < pvMax) {
            result.append(soigner(pvMax / 20));
        }
        return result.toString();
    }

    /**
     * Utilisé pour les tests unitaires junit
     */
    public void appliquerEtat(){
        this.estBrulee = true;
        this.estEmpoisonne = true;
        this.estParalyse = true;
    }

    /**
     * Utilisé pour les tests unitaires junit
     */
    public boolean aAucunEtat(){
        return (!this.estBrulee && !this.estEmpoisonne && !this.estParalyse);
    }
}