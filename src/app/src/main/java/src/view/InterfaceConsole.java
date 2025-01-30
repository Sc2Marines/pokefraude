package src.view;

import src.models.*;
import java.util.*;

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
                if (monstre != monstreActif && monstre.getPV() > 0) {
                    System.out.println(" - " + j + ": " + monstre.getNom() + " - PV: " + monstre.getPV());
                }

            }
            System.out.println("Objets : ");
            for (int j = 0; j < joueur.getObjectCount().size(); j++) {
                System.out.println(" - " + j + ": " + joueur.getObjectCount().get(j).getFirst().getNom() + " x "
                        + joueur.getObjectCount().get(j).getSecond());
            }
        }
        System.out.println("Terrain : " + modeleJeu.getTerrain().getEtat());
        System.out.println("============================================================\n");

    }

    public void displayText(String text) {
        if (!text.isEmpty()) {
            System.out.println(text);
        }
    }

    public Action obtenirActionJoueur(PlayerModel joueur) {
        System.out.println("Tour du joueur : " + joueur.getNom() + " Choississez une action :");
        System.out.println("1 : Attaquer");
        System.out.println("2 : Utiliser un objet");
        System.out.println("3 : Changer de monstre");
        System.out.print("Votre action : ");

        int choix = -1;
        try {
             choix = scanner.nextInt();
        } catch (InputMismatchException e) {
            System.out.println("Erreur: Veuillez entrer un nombre valide.");
            scanner.next();
            return obtenirActionJoueur(joueur);
        }


        switch (choix) {
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
         List<MonsterModel> monsterListCopy = new ArrayList<>();
          for (MonsterModel monster : game.getAvailableMonsters()) {
            MonsterModel copy = new MonsterModel(monster.getName(), monster.getType());
            copy.populateStats(monster.getHp(), monster.getSpeed(), monster.getAttack(), monster.getDefense(), monster.getParalysis(), monster.getFlood(), monster.getFall());
            monsterListCopy.add(copy);
        }
        do {
            for (int j = 0; j < monsterListCopy.size(); j++) {
                System.out.println(j + " " + monsterListCopy.get(j).getName());
            }
            int choice = -1;
             try {
                  choice = scanner.nextInt();
             } catch (InputMismatchException e) {
                System.out.println("Erreur: Veuillez entrer un nombre valide.");
                scanner.next();
                continue;
            }
            while (choice >= monsterListCopy.size() || choice < 0) {
                System.out.println("Choisissez un monstre dans la liste");
                try {
                      choice = scanner.nextInt();
                } catch (InputMismatchException e) {
                    System.out.println("Erreur: Veuillez entrer un nombre valide.");
                    scanner.next();
                    continue;
                }
            }
            MonsterModel tmpMonster = monsterListCopy.get(choice);
            monsterListCopy.remove(tmpMonster);
            monsterSelection.add(tmpMonster);
            cnt++;
        } while (cnt < 3);
        return monsterSelection;
    }

    /**
     * Get the gamemode.
     * @return The player choice.
     */
     public int getGameMode() {
        System.out.println("Which gamemode do you want ? \n");
        System.out.println("0: human vs human");
        System.out.println("1: human vs bot");
        int choice = -1;
        do {
            System.out.println("make sure to select a valid option.");
             try {
                  choice = scanner.nextInt();
             } catch (InputMismatchException e) {
                System.out.println("Erreur: Veuillez entrer un nombre valide.");
                 scanner.next();
                continue;
            }
        } while (choice < 0 || choice > 1);
        return choice;
    }

    private Action obtenirActionChangerMonstre(PlayerModel joueur) {
         System.out.println("Choisisez le monstre a utiliser :");
        List<Monster> monstres = joueur.getMonstres();
        for (int i = 0; i < monstres.size(); i++) {
            if (monstres.get(i).getPV() > 0) {
                System.out.println(" " + i + ": " + monstres.get(i).getNom() + " - PV: " + monstres.get(i).getPV());
            }
        }
        System.out.print("Votre monstre : ");
        int indexMonstre = -1;
        try {
             indexMonstre = scanner.nextInt();
        } catch (InputMismatchException e) {
            System.out.println("Erreur: Veuillez entrer un nombre valide.");
            scanner.next();
            return obtenirActionChangerMonstre(joueur);
        }
        while (indexMonstre < 0 || indexMonstre >= monstres.size() || monstres.get(indexMonstre).getPV() <= 0) {
            System.out.println("Choisissez un monstre avec des PV !");
            try {
                indexMonstre = scanner.nextInt();
            } catch (InputMismatchException e) {
                System.out.println("Erreur: Veuillez entrer un nombre valide.");
                scanner.next();
                continue;
            }
        }
        return new Action(ActionType.CHANGER_MONSTRE, monstres.get(indexMonstre));
    }

    private Action obtenirActionObjet(PlayerModel joueur) {
        System.out.println("Choisisez un objet :");
        for (int i = 0; i < joueur.getObjets().size(); i++) {
            System.out.println(" " + i + ": " + joueur.getObjets().get(i).getNom());
        }
        System.out.print("Votre objet : ");
        int indexObjet = -1;
        try {
             indexObjet = scanner.nextInt();
        } catch (InputMismatchException e) {
            System.out.println("Erreur: Veuillez entrer un nombre valide.");
            scanner.next();
            return obtenirActionObjet(joueur);
        }
        return new Action(ActionType.OBJET, joueur.getObjets().get(indexObjet));
    }

    private Action obtenirActionAttaquer(PlayerModel joueur) {
        Monster monstreActif = joueur.getMonstreActif();
        System.out.println("Choisisez une attack :");
        for (int i = 0; i < monstreActif.getAttacks().size(); i++) {
            System.out.println(" " + i + ": " + monstreActif.getAttacks().get(i).getName() + " x "
                    + monstreActif.getAttacks().get(i).getNbUse());
        }
        System.out.print("Votre attack : ");
        int indexAttaque = -1;
         try {
              indexAttaque = scanner.nextInt();
         } catch (InputMismatchException e) {
            System.out.println("Erreur: Veuillez entrer un nombre valide.");
             scanner.next();
            return obtenirActionAttaquer(joueur);
        }
        while (indexAttaque < 0 || indexAttaque >= monstreActif.getAttacks().size() || !monstreActif.getAttacks().get(indexAttaque).isAttackAvailable()) {
            System.out.println("Choisissez une attack disponible !");
             try {
                  indexAttaque = scanner.nextInt();
             } catch (InputMismatchException e) {
                System.out.println("Erreur: Veuillez entrer un nombre valide.");
                scanner.next();
                 continue;
            }
        }
        return new Action(ActionType.ATTAQUE, monstreActif.getAttacks().get(indexAttaque));
    }
}