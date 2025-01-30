package src.view;

import java.util.List;

import src.models.Action;
import src.models.GameModel;
import src.models.MonsterModel;
import src.models.PlayerModel;

public interface InterfaceGenerale {
    public void afficherEtatJeu(GameModel modeleJeu);
    public Action obtenirActionJoueur(PlayerModel player);
    public void displayText(String text);
    public List<MonsterModel> chooseMonsters(GameModel game, PlayerModel player);
    public int getGameMode();
}
