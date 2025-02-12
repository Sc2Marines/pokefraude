package src.models;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import src.utils.Tuple;

public class Monster {
    private String name;
    private Types type;
    private Types subType;
    private int pv;
    private int pvMax;
    private int attack;
    private int defense;
    private int vitesse;
    private double flood;
    private double fall;
    private List<AttackModel> attacks;

    private boolean isParalyzed;
    private boolean isPoisoned;
    private boolean isBurned;
    private boolean isHide;

    private boolean haveTriggerFlood;

    private boolean attackCancelled;

    private int turnParalyse = 0;
    private int turnHide = 0;

    private static final Types ELECTRIC = Types.ELECTRIC;
    private static final Types FIRE = Types.FIRE;
    private static final Types WATER = Types.WATER;
    private static final Types NATURE = Types.NATURE;
    private static final Types DIRT = Types.DIRT;
    private static final Types PLANT = Types.PLANT;
    @SuppressWarnings("unused")
    private static final Types INSECT = Types.INSECT;
    // store advantages in a map (way more cleaner way to do it -> can be reused)
    // (Need to be automated in a txt file)
    public static final Map<Types, Map<Types, Double>> TYPE_ADVANTAGES = new HashMap<>();
    static {
        Map<Types, Double> eauMap = new HashMap<>();
        eauMap.put(FIRE, 2.0);
        eauMap.put(ELECTRIC, 0.5);
        TYPE_ADVANTAGES.put(WATER, eauMap);

        Map<Types, Double> feuMap = new HashMap<>();
        feuMap.put(NATURE, 2.0);
        feuMap.put(WATER, 0.5);
        TYPE_ADVANTAGES.put(FIRE, feuMap);

        Map<Types, Double> natureMap = new HashMap<>();
        natureMap.put(DIRT, 2.0);
        natureMap.put(FIRE, 0.5);
        TYPE_ADVANTAGES.put(NATURE, natureMap);

        Map<Types, Double> terreMap = new HashMap<>();
        terreMap.put(ELECTRIC, 2.0);
        terreMap.put(NATURE, 0.5);
        TYPE_ADVANTAGES.put(DIRT, terreMap);

        Map<Types, Double> foudreMap = new HashMap<>();
        foudreMap.put(WATER, 2.0);
        foudreMap.put(DIRT, 0.5);
        TYPE_ADVANTAGES.put(ELECTRIC, foudreMap);
    }

    // make the random truely random and compliant
    private Random random = new Random();

    public Monster(MonsterModel modeleMonstre, List<AttackModel> availableAttacks) {
        this.name = modeleMonstre.getName();
        this.type = modeleMonstre.getType();
        this.subType = modeleMonstre.getSubType();
        this.pv = getRandomValue(modeleMonstre.getHp());
        this.pvMax = pv;
        this.attack = getRandomValue(modeleMonstre.getAttack());
        this.defense = getRandomValue(modeleMonstre.getDefense());
        this.vitesse = getRandomValue(modeleMonstre.getSpeed());
        this.flood = modeleMonstre.getFlood();
        this.fall = modeleMonstre.getFall();
        this.attacks = new ArrayList<>();
        this.isParalyzed = false;
        this.isBurned = false;
        this.isPoisoned = false;
        this.attackCancelled = false;
        this.isHide = false;
        this.haveTriggerFlood = false;
        // deep copy of list + randomisation of elements.
        List<AttackModel> randomisedAttackList = new ArrayList<>();
        for (AttackModel atks : availableAttacks) {
            randomisedAttackList.add(atks);
        }
        Collections.shuffle(randomisedAttackList);

        for (AttackModel modelAttaque : randomisedAttackList) {
             if (modelAttaque.getType() == this.type || modelAttaque.getType() == Types.NORMAL) {
                // Create a deep copy of the AttackModel
                AttackModel copiedAttack = new AttackModel(modelAttaque);
                 this.attacks.add(copiedAttack);
                if (this.attacks.size() == 4)
                     break;
            }
        }
    }

    private int getRandomValue(int[] values) {
        return this.random.nextInt(values[1] - values[0] + 1) + values[0];
    }

    public String getNom() {
        return name;
    }

    public Types getType() {
        return type;
    }

    public int getPV() {
        return pv;
    }

    public int getVitesse() {
        return vitesse;
    }

    public int getAttaque() {
        return attack;
    }

    public int getDefense() {
        return defense;
    }

    public boolean haveTriggerFlood()
    {
        return this.haveTriggerFlood;
    }

    public void setTriggerFlood(boolean triggerState) {
        this.haveTriggerFlood = triggerState;
    }

    public List<AttackModel> getAttacks() {
        return attacks;
    }

    private String damageStringGenerator(int damages) {
        return this.name + " a subit " + damages + " dégats, il lui reste " + this.pv + " PV" + "\n";
    }

    private String takeBurnDamage(Terrain terrain) {
        String result = "";
        if (terrain.getEtat().equals(Terrain.TypeTerrain.INONDE) && this.isBurned) {
            this.isBurned = false;
        } else {
            int damages = getAttaque() / 10;
            this.pv -= damages;
            result = this.damageStringGenerator(damages);
        }
        return result;
    }

    private String takePoisonDamage(Terrain terrain) {
        String result = "";
        if (terrain.getEtat().equals(Terrain.TypeTerrain.INONDE) && this.isPoisoned) {
            this.isPoisoned = false;
        } else {
            int damages = getAttaque() / 10;
            this.pv -= damages;
            result = this.damageStringGenerator(damages);
        }
        return result;
    }

    private String regainHealth(Terrain terrain) {
        String result = "";
        if (terrain.getEtat().equals(Terrain.TypeTerrain.INONDE) && this.type.equals(NATURE)) {
            double gain = this.pv * 0.1;
            this.pv += gain;
            result = this.name + " a regagné" + gain + " pv, il lui reste " + this.pv + " PV" + "\n";
        }
        return result;
    }

     public String healing() {
        String result = "";
         if (this.type.equals(NATURE) && this.subType.equals(PLANT) && Math.random() < 0.3) {
            this.isBurned = false;
            this.isParalyzed = false;
             this.isPoisoned = false;
            result = this.name + " s'est débarrassé de ses altérations d'état" + "\n";
        }
        return result;
    }

    public String takeDamages(int damages) {
        this.pv -= damages;
        if (this.pv < 0) {
            this.pv = 0;
        }
        return this.name + " a subit " + damages + " dégats, il lui reste " + this.pv + "PV" + "\n";
    }

    public String beforeTurnEffects(Terrain terrain){
        StringBuilder result = new StringBuilder();
        result.append(this.decrementHide());
        result.append(this.takeBurnDamage(terrain));
        result.append(this.takePoisonDamage(terrain));
        return result.toString();
    }

    public String afterTurnEffects(Terrain terrain){
        StringBuilder result = new StringBuilder();
        result.append(this.regainHealth(terrain));

        return result.toString();

    }



    public String attack(Monster target, AttackModel attack, Terrain terrain) {
        StringBuilder result = new StringBuilder();
         if (this.pv <= 0) {
            return result.toString();
        }
        Tuple<Boolean, String> paralyze = estSortiParalysie();
        result.append(paralyze.getSecond());
        result.append(this.fall(terrain, target, attack));
        if (!this.attackCancelled) {
            boolean canAttack = true;
            if (isParalyzed && !(boolean) paralyze.getFirst()) {
                if (Math.random() > 0.25) {
                    canAttack = false;
                } else {
                    result.append(name + " est paralysé" + "\n");
                }
            }
            if (hasFail(attack.getFail()) && !canAttack) {
                result.append(name + " a raté son attaque : " + attack.getName() + "\n");
            }
            if (canAttack) {
                int damages = calculerDegats(target, attack);
                result.append(name + " attaque avec : " + attack.getName() + "\n");
                result.append(target.takeDamages(damages));
                result.append(appliquerEffetAttaque(target, terrain, attack));

            }
        }
        this.attackCancelled = false;

        attack.use(1);
        result.append(this.healing());
        return result.toString();
    }

    private boolean hasFail(double fail) {
        return this.random.nextDouble() < fail;
    }

    private int calculerDegats(Monster target, AttackModel attack) {
        double coef = 0.85 + (1.0 - 0.85) * this.random.nextDouble();

        double avantage = calculerAvantage(target, attack);

        return (int) (((11.0 * this.attack * attack.getPower()) / (25.0 * target.getDefense()) + 2) * avantage
                * coef);
    }

    private double calculerAvantage(Monster target, AttackModel attack) {
        Types attackType = attack.getType();
        Types targetType = target.getType();

        if (TYPE_ADVANTAGES.containsKey(attackType) && TYPE_ADVANTAGES.get(attackType).containsKey(targetType)) {
            return TYPE_ADVANTAGES.get(attackType).get(targetType);
        }
        return 1.0;
    }

    private String appliquerEffetAttaque(Monster target, Terrain terrain, AttackModel attack) {
        String result = "";
        if (this.type == Types.ELECTRIC) {
            result = paralyze(target);
        } else if (this.type == Types.WATER) {
            result = flood(terrain);
        } else if (this.type == Types.FIRE) {
            result = burn(target);
        } else if (this.type == Types.DIRT) {
            result = hide(attack);
        } else if (this.type == Types.INSECT) {
            result = poison(target);
        }
        return result;
    }

    public String soigner(int heal) {
        this.pv += heal;
        if (this.pv > pvMax) {
            this.pv = pvMax;
        }
        return name + " a recuperé " + heal + "PV il a maintenant " + pv + "PV" + "\n";
    }

    public String guerir() {
         this.isPoisoned = false;
        this.isParalyzed = false;
        this.isBurned = false;
        return name + " est guerri de ses effets de status" + "\n";
    }

    private String poison(Monster target) {
        String result = "";
        if (!target.isPoisoned && !target.isBurned && !target.isParalyzed) {
            target.isPoisoned = true;
            result = target.name + " est empoisonné" + "\n";
        }
        return result;
    }

    private String burn(Monster target) {
        String result = "";
        if (!target.isPoisoned && !target.isBurned && !target.isParalyzed) {
            target.isBurned = true;

            result = target.name + " est brulé" + "\n";
        }
        return result;
    }

    private String flood(Terrain terrain) {
        StringBuilder result = new StringBuilder();
        if (Math.random() < this.flood && terrain.getEtat() != Terrain.TypeTerrain.INONDE) {
            // randome from 1 to 3
            terrain.modify(Terrain.TypeTerrain.INONDE, this.random.nextInt(1, 3));
            this.haveTriggerFlood = true;
            result.append("Le terrain est inonde" + "\n");
        }

        return result.toString();
    }

    private String fall(Terrain terrain, Monster target, AttackModel attack) {
        StringBuilder result = new StringBuilder();
        double randomNess = Math.random();
        if (!this.type.equals(WATER) && terrain.getEtat() == Terrain.TypeTerrain.INONDE && randomNess < target.fall) {
            result.append(this.takeDamages(this.calculerDegats(target, attack) / 4));
            result.append(this.name + " a chuté" + "\n");
            this.attackCancelled = true;
        }

        return result.toString();

    }

    private String paralyze(Monster target) {
        String result = "";
        if (!target.isPoisoned && !target.isBurned && !target.isParalyzed) {
            target.isParalyzed = true;
            result = target.name + " est paralysé" + "\n";
        }
        return result;
    }

    private String hide(AttackModel attack) {
        String result = "";
        if (attack.getType().equals(DIRT) && !this.isHide) {
            int randomNess = this.random.nextInt(1, 3);
            this.isHide = true;
            this.defense = this.defense * 2;
            this.turnHide = randomNess;
            result = this.name + " est caché sous terre" + "\n";
        }
        return result;
    }

    private String decrementHide() {
        String res = "";
        if (this.getType().equals(DIRT)) {
            if (this.turnHide > 0) {
                this.turnHide--;
            } else {
                if (this.isHide) {
                    this.isHide = false;
                    this.defense = this.defense / 2;
                    res = this.name + " n'est plus caché sous terre" + "\n";
                }
            }

        }

        return res;
    }

    private Tuple<Boolean, String> estSortiParalysie() {
        Tuple<Boolean, String> result = new Tuple<>(false, "");
        if (this.isParalyzed) {
            this.turnParalyse++;
            if (turnParalyse >= 6) {
                this.isParalyzed = false;
                this.turnParalyse = 0;
                result.setFirst(true);
                return result;
            }
            double probabilite = turnParalyse / 6.0;
            if (this.random.nextDouble() < probabilite) {
                this.isParalyzed = false;
                this.turnParalyse = 0;
                result.setSecond(name + " n'est plus paralysé" + "\n");
                result.setFirst(true);
                return result;
            }
        }

        return result;
    }

    public String debutTour() {
        StringBuilder result = new StringBuilder();
        if (isBurned || isPoisoned) {
            result.append(takeDamages(getAttaque() / 10));

        }
        if (type.equals(NATURE) && !isBurned && !isPoisoned && !isParalyzed && pv < pvMax) {
            result.append(soigner(pvMax / 20));
        }
        return result.toString();
    }

    /**
     * Utilisé pour les tests unitaires junit
     */
    public void appliquerEtat() {
        this.isBurned = true;
        this.isPoisoned = true;
        this.isParalyzed = true;
    }

    /**
     * Utilisé pour les tests unitaires junit
     */
    public boolean aAucunEtat() {
        return (!this.isBurned && !this.isPoisoned && !this.isParalyzed);
    }
}