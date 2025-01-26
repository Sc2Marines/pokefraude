package src.models;

public class Action {
    private ActionType type;
    private int index;
    private AttackModel attack;
    private ObjectModel objet;
    private Monster nouveauMonstre;

    public Action(ActionType type, int index) {
        this.type = type;
        this.index = index;
    }

    public Action(ActionType type, AttackModel attack) {
        this.type = type;
        this.attack = attack;
    }

    public Action(ActionType type, ObjectModel objet) {
        this.type = type;
        this.objet = objet;
    }

    public Action(ActionType type, Monster nouveauMonstre) {
        this.type = type;
        this.nouveauMonstre = nouveauMonstre;
    }

    public ActionType getType() {
        return type;
    }

    public int getIndex() {
        return index;
    }

    public AttackModel getAttaque() {
        return attack;
    }

    public ObjectModel getObjet() {
        return objet;
    }

    public Monster getNouveauMonstre() {
        return nouveauMonstre;
    }
}