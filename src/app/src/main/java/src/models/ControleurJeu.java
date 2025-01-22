package src.models;
import src.utils.LecteurFichier;
import java.util.List;
import src.view.InterfaceConsole;
public class ControleurJeu {
    private ModeleJeu modeleJeu;
    private InterfaceConsole interfaceConsole;
    private LecteurFichier lecteurFichier;

    public ControleurJeu() {
        lecteurFichier = new LecteurFichier();
        // Load monsters and attacks
        List<ModeleMonstre> monstres = lecteurFichier.lireMonstres("src/app/src/main/java/src/monsters.txt");
        List<ModeleAttaque> attaques = lecteurFichier.lireAttaques("src/app/src/main/java/src/attacks.txt");

        if (monstres == null || attaques == null) {
           System.err.println("Error loading monsters and attacks");
           return;
        }

        // Create the game model and user interface
        modeleJeu = new ModeleJeu(monstres, attaques);
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

        ModeleJoueur vainqueur = modeleJeu.getVainqueur();
        if (vainqueur != null) {
           System.out.println("Le vainqueur est le joueur : " + vainqueur.getNom());
        } else {
            System.out.println("Match nul");
        }

    }

    public static void main(String[] args) {
        ControleurJeu controleurJeu = new ControleurJeu();
        controleurJeu.demarrer();
    }
}