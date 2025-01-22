package src.models;
import src.utils.LecteurFichier;
import java.util.List;
import src.view.InterfaceConsole;
public class GameController {
    private GameModel modeleJeu;
    private InterfaceConsole interfaceConsole;
    private LecteurFichier lecteurFichier;

    public GameController() {
        lecteurFichier = new LecteurFichier();
        // Load monsters and attacks
        List<MonsterModel> monstres = lecteurFichier.lireMonstres("src/app/src/main/java/src/monsters.txt");
        List<AttackModel> attaques = lecteurFichier.lireAttaques("src/app/src/main/java/src/attacks.txt");

        if (monstres == null || attaques == null) {
           System.err.println("Error loading monsters and attacks");
           return;
        }

        // Create the game model and user interface
        modeleJeu = new GameModel(monstres, attaques);
        interfaceConsole = new InterfaceConsole();

    }
    public void demarrer(){
        interfaceConsole.afficherEtatJeu(modeleJeu);

        while(!modeleJeu.estTermine()){
            // Get the action for each player
            Action actionJoueur1 = interfaceConsole.obtenirActionJoueur(modeleJeu.getJoueur(0));
            Action actionJoueur2 = interfaceConsole.obtenirActionJoueur(modeleJeu.getJoueur(1));

            // Execute the actions
            modeleJeu.executerActions(actionJoueur1, actionJoueur2);

            // Display the game status
            interfaceConsole.afficherEtatJeu(modeleJeu);
        }
        System.out.println("Game over");

        PlayerModel vainqueur = modeleJeu.getVainqueur();
        if (vainqueur != null) {
           System.out.println("Le vainqueur est le joueur : " + vainqueur.getNom());
        } else {
            System.out.println("Match nul");
        }

    }

    public static void main(String[] args) {
        GameController controleurJeu = new GameController();
        controleurJeu.demarrer();
    }
}