package src;

import java.util.ArrayList;
import java.util.List;
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

        for (ModeleAttaque attaque: attaquesDisponibles) {
            if (attaque.getType().equals(this.type) || attaque.getType().equals("Normal")) {
                this.attaques.add(attaque);
                if(this.attaques.size() == 4) break;
            }
        }

    }

    private int getRandomValue(int[] values){
        Random random = new Random();
        return random.nextInt(values[1] - values[0] + 1) + values[0];
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
    public int getDefense(){return defense;}

    public List<ModeleAttaque> getAttaques(){return attaques;}

    public void subirDegats(int degats) {
       if(estBrulee){
           degats += getAttaque()/10;
       }
        this.pv -= degats;
        if (this.pv < 0) {
            this.pv = 0;
        }
        System.out.println(nom + " a subit " + degats + " degats, il lui reste " + pv + "PV");

    }

    public void attaquer(Monstre cible, ModeleAttaque attaque, Terrain terrain) {
       if (estParalyse && !estSortiParalysie()){
            System.out.println(nom + " est paralyse et ne peut attaquer");
            return;
       }
      if(hasFail(attaque.getFail())){
           System.out.println(nom + " a rate son attaque : "+attaque.getName());
           return;
        }
        System.out.println(nom + " attaque avec : " + attaque.getName());
        int degats = calculerDegats(cible, attaque, terrain);
        cible.subirDegats(degats);
        appliquerEffetAttaque(cible, terrain, attaque);
    }

   private boolean hasFail(double fail){
       Random random = new Random();
       return random.nextDouble() < fail;
   }
    private int calculerDegats(Monstre cible, ModeleAttaque attaque, Terrain terrain) {
        double coef = 0.85 + (1.0 - 0.85) * new Random().nextDouble();

        double avantage = calculerAvantage(cible, attaque);

        int degats = (int) (((11.0 * this.attaque * attaque.getPower()) / (25.0 * cible.getDefense()) + 2) * avantage * coef);

        return degats;
    }

    private double calculerAvantage(Monstre cible, ModeleAttaque attaque){
        if (attaque.getType().equals("Eau") && cible.getType().equals("Feu") ||
        attaque.getType().equals("Feu") && cible.getType().equals("Nature") ||
         attaque.getType().equals("Nature") && cible.getType().equals("Terre") ||
        attaque.getType().equals("Terre") && cible.getType().equals("Foudre") ||
        attaque.getType().equals("Foudre") && cible.getType().equals("Eau")){
            return 2.0;
        }
       else if (attaque.getType().equals("Feu") && cible.getType().equals("Eau") ||
           attaque.getType().equals("Nature") && cible.getType().equals("Feu") ||
            attaque.getType().equals("Terre") && cible.getType().equals("Nature") ||
           attaque.getType().equals("Foudre") && cible.getType().equals("Terre") ||
            attaque.getType().equals("Eau") && cible.getType().equals("Foudre")){
           return 0.5;
        }
        return 1.0;
    }

    private void appliquerEffetAttaque(Monstre cible, Terrain terrain, ModeleAttaque attaque) {
        switch (this.type) {
            case "Foudre":
                paralyser(cible);
                break;
            case "Eau":
                inonder(terrain, cible);
                break;
            case "Feu":
               bruler(cible);
                break;
            case "Nature":
               empoisonner(cible);
                break;
        }
    }
    public void soigner(int heal){
        this.pv += heal;
        if(this.pv > pvMax){
           this.pv = pvMax;
        }
       System.out.println(nom +" a recupere " + heal +"PV il a maintenant " + pv + "PV");
    }
    public void guerir(){
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
       if(Math.random() < 0.3){
           cible.subirDegats(cible.getAttaque()/4);
            System.out.println(cible.nom + " a chute");
       }

    }
    private void paralyser(Monstre cible){
        if(!cible.estParalyse){
           cible.estParalyse = true;
             System.out.println(cible.nom + " est paralyse");
        }
    }

    public boolean estSortiParalysie() {
        tourParalyse++;
        if(tourParalyse >= 6){
            estParalyse = false;
            tourParalyse = 0;
            return true;
        }
        double probabilite = tourParalyse / 6.0;
        Random random = new Random();
       if(random.nextDouble() < probabilite){
            estParalyse = false;
            tourParalyse = 0;
             System.out.println(nom + " n'est plus paralyse");
            return true;
       }
        return false;
    }
    public void debutTour(){
       if(estBrulee){
           subirDegats(getAttaque()/10);
       } else if(estEmpoisonne){
            subirDegats(getAttaque()/10);
       }
        if(type.equals("Nature") && !estBrulee && !estEmpoisonne && !estParalyse && pv < pvMax){
            soigner(pvMax / 20);
        }

    }
}