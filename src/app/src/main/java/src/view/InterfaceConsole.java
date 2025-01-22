package src.view;

import java.util.Scanner;
import src.models.*;

public class InterfaceConsole {
    private Scanner scanner;


    public InterfaceConsole() {
        this.scanner = new Scanner(System.in);
    }

    public void afficherEtatJeu(GameModel modeleJeu) {
        System.out.println("\n======================== Etat du Jeu =========================");
        for (int i = 0; i < modeleJeu.getJoueurs().size(); i++) {
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
            for(int j = 0; j< joueur.getObjets().size(); j++){
               System.out.println(" - "+j +": "+ joueur.getObjets().get(j).getNom());
            }
        }
        System.out.println("Terrain : " + modeleJeu.getTerrain().getEtat());
        System.out.println("============================================================\n");

    }

    public src.models.Action obtenirActionJoueur(PlayerModel joueur) {
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

    private src.models.Action obtenirActionChangerMonstre(PlayerModel joueur) {
        System.out.println("Choisisez le monstre a utiliser :");
        for(int i = 0; i < joueur.getMonstres().size(); i++){
            System.out.println(" "+i +": "+ joueur.getMonstres().get(i).getNom());
        }
        System.out.print("Votre monstre : ");
        int indexMonstre = scanner.nextInt();
        return new Action(ActionType.CHANGER_MONSTRE, indexMonstre);
    }

    private src.models.Action obtenirActionObjet(PlayerModel joueur) {
        System.out.println("Choisisez un objet :");
        for (int i = 0; i< joueur.getObjets().size(); i++){
            System.out.println(" "+i + ": " + joueur.getObjets().get(i).getNom());
        }
        System.out.print("Votre objet : ");
        int indexObjet = scanner.nextInt();
        return new Action(ActionType.OBJET, indexObjet, joueur.getObjets().get(indexObjet));
    }

    private src.models.Action obtenirActionAttaquer(PlayerModel joueur){
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