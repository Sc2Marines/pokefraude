package src.controller;
import src.models.Action;
import src.models.AttackModel;
import src.models.GameModel;
import src.models.MonsterModel;
import src.models.PlayerModel;
import src.utils.LecteurFichier;
import java.util.List;
import src.view.*;
public class GameController {
    private GameModel modeleJeu;
    private InterfaceGenerale interfaceConsole;
    private LecteurFichier lecteurFichier;

    public GameController() {
        lecteurFichier = new LecteurFichier();
        // Load monsters and attacks
        List<MonsterModel> monstres = lecteurFichier.lireMonstres("./src/app/src/main/java/src/config/monsters.txt");
        List<AttackModel> attaques = lecteurFichier.lireAttaques("./src/app/src/main/java/src/config/attacks.txt");

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
            interfaceConsole.displayText(modeleJeu.executerActions(actionJoueur1, actionJoueur2));

            // Display the game status
            interfaceConsole.afficherEtatJeu(modeleJeu);
        }
        interfaceConsole.displayText("Game over");

        PlayerModel vainqueur = modeleJeu.getVainqueur();
        if (vainqueur != null) {
            interfaceConsole.displayText("Le vainqueur est le joueur : " + vainqueur.getNom());
        } else {
            interfaceConsole.displayText("Match nul");
        }

    }

    public static void main(String[] args) {
        GameController controleurJeu = new GameController();
        controleurJeu.demarrer();
    }
}