package src.models;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class Monstre {
    private String nom;
    private String type;
    private int pv;
    private int pvMax;
    private int attaque;
    private int defense;
    private int vitesse;
    private List<ModeleAttaque> attaques;

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

    public Monstre(ModeleMonstre modeleMonstre, List<ModeleAttaque> attaquesDisponibles) {
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

        for (ModeleAttaque modelAttaque : attaquesDisponibles) {
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

    public List<ModeleAttaque> getAttaques() {
        return attaques;
    }

    public void subirDegats(int degats) {
        if (estBrulee) {
            degats += getAttaque() / 10;
        }
        this.pv -= degats;
        if (this.pv < 0) {
            this.pv = 0;
        }
        System.out.println(nom + " a subit " + degats + " degats, il lui reste " + pv + "PV");

    }

    public void attaquer(Monstre cible, ModeleAttaque attaque, Terrain terrain) {
        if (estParalyse && !estSortiParalysie()) {
            System.out.println(nom + " est paralyse et ne peut attaquer");
            return;
        }
        if (hasFail(attaque.getFail())) {
            System.out.println(nom + " a rate son attaque : " + attaque.getName());
            return;
        }
        System.out.println(nom + " attaque avec : " + attaque.getName());
        int degats = calculerDegats(cible, attaque);
        cible.subirDegats(degats);
        appliquerEffetAttaque(cible, terrain);
    }

    private boolean hasFail(double fail) {
        return this.random.nextDouble() < fail;
    }

    private int calculerDegats(Monstre cible, ModeleAttaque attaque) {
        double coef = 0.85 + (1.0 - 0.85) * this.random.nextDouble();

        double avantage = calculerAvantage(cible, attaque);

        return (int) (((11.0 * this.attaque * attaque.getPower()) / (25.0 * cible.getDefense()) + 2) * avantage
                * coef);
    }

    private double calculerAvantage(Monstre cible, ModeleAttaque attaque) {
        String attaqueType = attaque.getType();
        String cibleType = cible.getType();

        if (TYPE_ADVANTAGES.containsKey(attaqueType) && TYPE_ADVANTAGES.get(attaqueType).containsKey(cibleType)) {
            return TYPE_ADVANTAGES.get(attaqueType).get(cibleType);
        }
        return 1.0;
    }


    private void appliquerEffetAttaque(Monstre cible, Terrain terrain) {
        switch (this.type) {
            case FOUDRE:
                paralyser(cible);
                break;
            case EAU:
                inonder(terrain, cible);
                break;
            case FEU:
                bruler(cible);
                break;
            case NATURE:
                empoisonner(cible);
                break;
            default:
                break;
        }
    }

    public void soigner(int heal) {
        this.pv += heal;
        if (this.pv > pvMax) {
            this.pv = pvMax;
        }
        System.out.println(nom + " a recupere " + heal + "PV il a maintenant " + pv + "PV");
    }

    public void guerir() {
        this.estBrulee = false;
        this.estEmpoisonne = false;
        this.estParalyse = false;
        System.out.println(nom + " est guerri de ses effets de status");
    }

    private void empoisonner(Monstre cible) {
        if (!cible.estEmpoisonne) {
            cible.estEmpoisonne = true;
            System.out.println(cible.nom + " est empoisonne");
        }
    }

    private void bruler(Monstre cible) {
        if (!cible.estBrulee) {
            cible.estBrulee = true;
            System.out.println(cible.nom + " est brule");
        }
    }

    private void inonder(Terrain terrain, Monstre cible) {
        if (terrain.getEtat() != Terrain.TypeTerrain.INONDE) {
            terrain.modifier(Terrain.TypeTerrain.INONDE);
            System.out.println("Le terrain est inonde");
            chute(cible);
        }
    }

    private void chute(Monstre cible) {
        if (Math.random() < 0.3) {
            cible.subirDegats(cible.getAttaque() / 4);
            System.out.println(cible.nom + " a chute");
        }

    }

    private void paralyser(Monstre cible) {
        if (!cible.estParalyse) {
            cible.estParalyse = true;
            System.out.println(cible.nom + " est paralyse");
        }
    }

    public boolean estSortiParalysie() {
        tourParalyse++;
        if (tourParalyse >= 6) {
            estParalyse = false;
            tourParalyse = 0;
            return true;
        }
        double probabilite = tourParalyse / 6.0;
        if (this.random.nextDouble() < probabilite) {
            estParalyse = false;
            tourParalyse = 0;
            System.out.println(nom + " n'est plus paralyse");
            return true;
        }
        return false;
    }

    public void debutTour() {
        if (estBrulee || estEmpoisonne) {
            subirDegats(getAttaque() / 10);
        }
        if (type.equals(NATURE) && !estBrulee && !estEmpoisonne && !estParalyse && pv < pvMax) {
            soigner(pvMax / 20);
        }

    }
}