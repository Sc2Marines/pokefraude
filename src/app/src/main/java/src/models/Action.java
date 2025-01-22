package src.models;

public class Action {
    private ActionType type;
    private int index;
    private ModeleAttaque attaque;
    private ModeleObjet objet;
    private ModeleMonstre nouveauMonstre;

    public Action(ActionType type, int index) {
        this.type = type;
        this.index = index;
    }

    public Action(ActionType type, int index, ModeleAttaque attaque) {
        this.type = type;
        this.index = index;
        this.attaque = attaque;
    }

    public Action(ActionType type, int index, ModeleObjet objet) {
        this.type = type;
        this.index = index;
        this.objet = objet;
    }

    public Action(ActionType type, int index, ModeleMonstre nouveauMonstre) {
        this.type = type;
        this.index = index;
        this.nouveauMonstre = nouveauMonstre;
    }

    public ActionType getType() {
        return type;
    }

    public int getIndex() {
        return index;
    }

    public ModeleAttaque getAttaque() {
        return attaque;
    }

    public ModeleObjet getObjet() {
        return objet;
    }

    public ModeleMonstre getNouveauMonstre() {
        return nouveauMonstre;
    }
}