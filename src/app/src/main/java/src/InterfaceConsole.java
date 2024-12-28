package src;

import java.util.List;
import java.util.Scanner;

public class InterfaceConsole {
    private ControleurJeu controleurJeu;
    private ModeleJeu modeleJeu;
    private Scanner scanner;


    public InterfaceConsole(ControleurJeu controleurJeu, ModeleJeu modeleJeu) {
        this.controleurJeu = controleurJeu;
        this.modeleJeu = modeleJeu;
        this.scanner = new Scanner(System.in);
    }

    public void afficherEtatJeu(ModeleJeu modeleJeu) {
        System.out.println("\n======================== Etat du Jeu =========================");
        for (int i = 0; i < modeleJeu.getJoueurs().size(); i++) {
            ModeleJoueur joueur = modeleJeu.getJoueur(i);
            Monstre monstreActif = joueur.getMonstreActif();
            System.out.println("Joueur " + (i + 1) + " (" + joueur.getNom() + "): ");
            System.out.println(" Monstre Actif : " + monstreActif.getNom() + " - PV: " + monstreActif.getPV());
            System.out.println(" Autres monstres:");
            for (int j = 0; j < joueur.getMonstres().size(); j++) {
                Monstre monstre = joueur.getMonstres().get(j);
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

    public ModeleJeu.Action obtenirActionJoueur(ModeleJoueur joueur) {
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

    private ModeleJeu.Action obtenirActionChangerMonstre(ModeleJoueur joueur) {
        System.out.println("Choisisez le monstre a utiliser :");
        for(int i = 0; i < joueur.getMonstres().size(); i++){
            System.out.println(" "+i +": "+ joueur.getMonstres().get(i).getNom());
        }
        System.out.print("Votre monstre : ");
        int indexMonstre = scanner.nextInt();
        return new ModeleJeu.Action(ModeleJeu.ActionType.CHANGER_MONSTRE, indexMonstre);
    }

    private ModeleJeu.Action obtenirActionObjet(ModeleJoueur joueur) {
        System.out.println("Choisisez un objet :");
        for (int i = 0; i< joueur.getObjets().size(); i++){
            System.out.println(" "+i + ": " + joueur.getObjets().get(i).getNom());
        }
        System.out.print("Votre objet : ");
        int indexObjet = scanner.nextInt();
        return new ModeleJeu.Action(ModeleJeu.ActionType.OBJET, indexObjet, joueur.getObjets().get(indexObjet));
    }

    private ModeleJeu.Action obtenirActionAttaquer(ModeleJoueur joueur){
        Monstre monstreActif = joueur.getMonstreActif();
        System.out.println("Choisisez une attaque :");
        for (int i = 0; i < monstreActif.getAttaques().size(); i++){
           System.out.println(" "+ i+ ": " + monstreActif.getAttaques().get(i).getName());
        }
        System.out.print("Votre attaque : ");
       int indexAttaque = scanner.nextInt();
       return new ModeleJeu.Action(ModeleJeu.ActionType.ATTAQUE, indexAttaque, monstreActif.getAttaques().get(indexAttaque));
    }
}