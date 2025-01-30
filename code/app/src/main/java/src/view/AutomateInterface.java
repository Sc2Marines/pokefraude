package src.view;

import src.models.Action;
import src.models.GameModel;
import src.models.MonsterModel;
import src.models.PlayerModel;

import src.models.bots.interfaces.BotInterface;
import src.models.bots.models.GameReportModel;

import java.util.List;

public class AutomateInterface implements InterfaceGenerale {
    private GameModel gameModel;
    private InterfaceGenerale playersInterface;
    private BotInterface bot;
    private GameReportModel gameReport;
    /**
     * Constructor for the computer interface.
     * @param gameModel the game model.
     * @param playerInterface the players interface.
     */
    public AutomateInterface(GameModel gameModel, InterfaceGenerale playersInterface, BotInterface bot) {
        this.gameModel = gameModel;
        this.playersInterface = playersInterface;
        this.bot = bot;
    }

    /**
     * Send the text to the players interface.
     * @param text The text to send.
     */
    public void displayText(String text){
        this.playersInterface.displayText(text);
    }

    /**
     * Display the state of the game (not used for the bot)
     * @param gameModel The game model.
     */
    public void afficherEtatJeu(GameModel gameModel) {
      // Not used for the bot.
    }

    /**
     * Return the list of monsters choosen by the computer.
     * @param game The game model.
     * @param player The player which is asked to choose monsters.
     * @return The list of chosen mosnters.
     */
    public List<MonsterModel> chooseMonsters(GameModel game, PlayerModel player) {
        return this.bot.chooseMonsters(game, player);
    }


    /**
     * Return the action choosen by the computer.
     * @param player the player wich is asked to do an action.
     * @return the action choosen.
     */
    public Action obtenirActionJoueur(PlayerModel player){
        if (this.gameReport == null) {
            gameReport = new GameReportModel(player, this.gameModel);
        }
        this.gameReport.generateReport();
        return this.bot.getActionDecided(player, gameReport);
    }

    /**
     * Return the gamemode choosen by the computer
     */
    public int getGameMode() {
        return 0;
    }
}
