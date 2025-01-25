package src.view;

import src.models.*;
import java.util.*;
import src.utils.Tuple;

public class InterfaceConsole implements InterfaceGenerale {
    private Scanner scanner;


    public InterfaceConsole() {
        this.scanner = new Scanner(System.in);
    }

    // surchage method to allow Junit test to inject a scanner
    public InterfaceConsole(Scanner scanner) {
        this.scanner = scanner;
    }

    public void afficherEtatJeu(GameModel modeleJeu) {
        System.out.println("\n======================== Etat du Jeu =========================");
        for (int i = 0; i < modeleJeu.getPlayers().size(); i++) {
            PlayerModel joueur = modeleJeu.getJoueur(i);
            Monster monstreActif = joueur.getMonstreActif();
            System.out.println("Joueur " + (i + 1) + " (" + joueur.getNom() + "): ");
            System.out.println(" Monstre Actif : " + monstreActif.getNom() + " - PV: " + monstreActif.getPV());
            System.out.println(" Autres monstres:");
            for (int j = 0; j < joueur.getMonstres().size(); j++) {
                Monster monstre = joueur.getMonstres().get(j);
               if(monstre != monstreActif){
                    System.out.println(" - "+j +": "+ monstre.getNom() + " - PV: "+monstre.getPV());
                }

            }
            System.out.println("Objets : ");
            for(int j = 0; j< joueur.getObjectCount().size(); j++){
               System.out.println(" - "+j +": "+ joueur.getObjectCount().get(j).getFirst().getNom() + " x " + joueur.getObjectCount().get(j).getSecond());
            }
        }
        System.out.println("Terrain : " + modeleJeu.getTerrain().getEtat());
        System.out.println("============================================================\n");

    }

    public void displayText(String text) {
        if (!text.isEmpty())
        {
            System.out.println(text);
        }
    }

    public Action obtenirActionJoueur(PlayerModel joueur) {
        System.out.println("Tour du joueur : "+ joueur.getNom() + " Choississez une action :");
        System.out.println("1 : Attaquer");
        System.out.println("2 : Utiliser un objet");
        System.out.println("3 : Changer de monstre");
        System.out.print("Votre action : ");

        int choix = scanner.nextInt();

        switch(choix){
            case 1:
               return obtenirActionAttaquer(joueur);
            case 2:
                return obtenirActionObjet(joueur);
            case 3:
               return obtenirActionChangerMonstre(joueur);
            default:
               System.out.println("Action invalide, choisiez une autre action");
               return obtenirActionJoueur(joueur);
        }
    }

    public List<MonsterModel> chooseMonsters(GameModel game, PlayerModel player) {
        System.out.println(player.getNom() + " Choisissez 3 monstres parmis les monstres disponibles");
        int cnt = 0;
        List<MonsterModel> monsterSelection = new ArrayList<>();
        do {
            List<MonsterModel> monsterList = game.getMonstresDisponibles();
            for (int j = 0; j < monsterList.size(); j++) {
                System.out.println(j + " " + monsterList.get(j).getName());
            }
            int choice = scanner.nextInt();
            while (choice >= monsterList.size() || choice < 0) {
                System.out.println("Choisissez un monstre dans la liste");
                choice = scanner.nextInt();
            }
            MonsterModel tmpMonster = monsterList.get(choice);
            monsterList.remove(tmpMonster);
            monsterSelection.add(tmpMonster); 
            cnt ++;
        } while (cnt < 3);
        return monsterSelection;
    }

    private Action obtenirActionChangerMonstre(PlayerModel joueur) {
        System.out.println("Choisisez le monstre a utiliser :");
        for(int i = 0; i < joueur.getMonstres().size(); i++){
            System.out.println(" "+i +": "+ joueur.getMonstres().get(i).getNom());
        }
        System.out.print("Votre monstre : ");
        int indexMonstre = scanner.nextInt();
        return new Action(ActionType.CHANGER_MONSTRE, indexMonstre);
    }

    private Action obtenirActionObjet(PlayerModel joueur) {
        System.out.println("Choisisez un objet :");
        for (int i = 0; i< joueur.getObjets().size(); i++){
            System.out.println(" "+i + ": " + joueur.getObjets().get(i).getNom());
        }
        System.out.print("Votre objet : ");
        int indexObjet = scanner.nextInt();
        return new Action(ActionType.OBJET, indexObjet, joueur.getObjets().get(indexObjet));
    }

    private Action obtenirActionAttaquer(PlayerModel joueur){
        Monster monstreActif = joueur.getMonstreActif();
        System.out.println("Choisisez une attaque :");
        for (int i = 0; i < monstreActif.getAttaques().size(); i++){
           System.out.println(" "+ i+ ": " + monstreActif.getAttaques().get(i).getName());
        }
        System.out.print("Votre attaque : ");
       int indexAttaque = scanner.nextInt();
       return new Action(ActionType.ATTAQUE, indexAttaque, monstreActif.getAttaques().get(indexAttaque));
    }
}