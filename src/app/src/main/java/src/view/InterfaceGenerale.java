package src.view;

import src.models.Action;
import src.models.GameModel;
import src.models.PlayerModel;

public interface InterfaceGenerale {
    public void afficherEtatJeu(GameModel modeleJeu);
    public Action obtenirActionJoueur(PlayerModel player);
    public void displayText(String text);
}
