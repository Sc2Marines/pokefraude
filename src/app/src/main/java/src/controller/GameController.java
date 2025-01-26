package src.controller;
import src.models.Action;
import src.models.AttackModel;
import src.models.GameModel;
import src.models.MonsterModel;
import src.models.PlayerModel;
import src.utils.FileParser;
import java.util.List;
import src.view.*;
public class GameController {
    private GameModel gameModel;
    private InterfaceGenerale interfaceConsole;
    private FileParser fileParser;

    /**
     * The constructor for the game controller.
     */
    public GameController() {
        fileParser = new FileParser();
        // Load monsters and attacks
        List<MonsterModel> monstres = fileParser.lireMonstres("./src/app/src/main/java/src/config/monsters.txt");
        List<AttackModel> attacks = fileParser.lireAttaques("./src/app/src/main/java/src/config/attacks.txt");

        if (monstres == null || attacks == null) {
           System.err.println("Error loading monsters and attacks");
           return;
        }

        // Create the game model and user interface
        gameModel = new GameModel(monstres, attacks);
        interfaceConsole = new InterfaceConsole();
        //create 2 players 
        PlayerModel p1 = new PlayerModel("joueur 1");
        PlayerModel p2 = new PlayerModel("joueur 2");
        gameModel.addJoueur(p1);
        gameModel.addJoueur(p2);
    }

    /**
     * The method to start the game.
     */
    public void start(){
        List<PlayerModel> players = gameModel.getPlayers();
        for (PlayerModel playerModel : players) {
            List<MonsterModel> playerMonsters= interfaceConsole.chooseMonsters(gameModel, playerModel);
            playerModel.setMonsters(playerMonsters, gameModel.getAvailableAttacks());
        }

        interfaceConsole.afficherEtatJeu(gameModel);
        
        while(!gameModel.estTermine()){
            // Get the action for each player
            Action actionJoueur1 = interfaceConsole.obtenirActionJoueur(gameModel.getJoueur(0));
            Action actionJoueur2 = interfaceConsole.obtenirActionJoueur(gameModel.getJoueur(1));

            // Execute the actions
            interfaceConsole.displayText(gameModel.executerActions(actionJoueur1, actionJoueur2));

            // Display the game status
            interfaceConsole.afficherEtatJeu(gameModel);
        }
        interfaceConsole.displayText("Game over");

        PlayerModel vainqueur = gameModel.getVainqueur();
        if (vainqueur != null) {
            interfaceConsole.displayText("Le vainqueur est le joueur : " + vainqueur.getNom());
        } else {
            interfaceConsole.displayText("Match nul");
        }

    }
}