package src.models.bots.interfaces;

import src.models.MonsterModel;
import src.models.PlayerModel;
import src.models.bots.models.GameReportModel;
import src.models.Action;
import src.models.GameModel;

import java.util.List;

public interface BotInterface {
    public List<MonsterModel> chooseMonsters(GameModel gameModel, PlayerModel player);
    public Action getActionDecided(PlayerModel player, GameReportModel gameReport);
}