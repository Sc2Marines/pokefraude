package src.models.bots.instances;

import java.util.ArrayList;
import java.util.List;

import src.models.ActionType;
import src.models.AttackModel;
import src.models.Action;
import src.models.GameModel;
import src.models.Monster;
import src.models.MonsterModel;
import src.models.ObjectModel;
import src.models.PlayerModel;
import src.models.TypeObject;
import src.models.Types;
import src.models.bots.interfaces.BotInterface;
import src.models.bots.models.GameReportModel;
import java.util.Random;

public class DefaultBot implements  BotInterface{
    
    private Random random;
    private GameReportModel gameReport;
    private static final int CRITICALHP = 45;

    public DefaultBot() {
        this.random = new Random();
    }

    public List<MonsterModel> chooseMonsters(GameModel gameModel, PlayerModel player) {
        List<MonsterModel> listMonsters = gameModel.getAvailableMonsters();
        List<MonsterModel> myChoices = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            int choose = random.nextInt(0, listMonsters.size() -i -1);
            myChoices.add(listMonsters.get(choose));
            listMonsters.remove(listMonsters.get(choose));
        }
        return myChoices;
    }

    public Action getActionDecided(PlayerModel player, GameReportModel gameReport) {
        this.gameReport = gameReport;
        Action myAction;
        if (gameReport.getActualMonster().getPV() < CRITICALHP && havePotion()) {
            myAction = new Action(ActionType.OBJET, this.getPotion());
        } else if (gameReport.getActualMonster().getPV() < CRITICALHP && !this.allMonsterLowHP()) {
            myAction = new Action(ActionType.CHANGER_MONSTRE, this.pickMonster());
        }
        else {
            myAction = new Action(ActionType.ATTAQUE, this.pickAttack());
        }
        return myAction;
    }

    private AttackModel pickAttack() {
        List<AttackModel> listAttack = gameReport.getActualMonster().getAttacks();
        for (AttackModel attack : listAttack) {
            if (attack.getType() != Types.NORMAL){
                return attack;
            }
        }
        return listAttack.get(0);
    }

    private Monster pickMonster() {
        List<Monster> myMonsters = gameReport.getDeckMonsters();
        for (Monster monster : myMonsters) {
            if (monster.getPV() > CRITICALHP) {
                return monster;
            }
        }
        for (Monster monster: myMonsters) {
            if (monster.getPV() > 0)
            {
                return monster;
            }
        }
        return myMonsters.get(0);
    }

    private boolean havePotion() {
        List<ObjectModel> myObjectModels = gameReport.getMyself().getObjets();
        for (ObjectModel objectModel : myObjectModels) {
            if (objectModel.getType() == TypeObject.POTION) {
                return true;
            }
        }
        return false;
    }

    private boolean allMonsterLowHP() {
        List<Monster> myMonsters = gameReport.getDeckMonsters();
        for (Monster monster : myMonsters) {
            if (monster.getPV() > CRITICALHP) {
                return false;
            }
        }
        return true;
    }

    private ObjectModel getPotion() {
        List<ObjectModel> myObjectModels = gameReport.getMyself().getObjets();
        for (ObjectModel objectModel : myObjectModels) {
            if (objectModel.getType() == TypeObject.POTION) {
                return objectModel;
            }
        }
        return null;
    }
}
