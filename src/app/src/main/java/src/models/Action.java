package src.models;

public class Action {
    private ActionType type;
    private int index;
    private AttackModel attaque;
    private ObjectModel objet;
    private MonsterModel nouveauMonstre;

    public Action(ActionType type, int index) {
        this.type = type;
        this.index = index;
    }

    public Action(ActionType type, int index, AttackModel attaque) {
        this.type = type;
        this.index = index;
        this.attaque = attaque;
    }

    public Action(ActionType type, int index, ObjectModel objet) {
        this.type = type;
        this.index = index;
        this.objet = objet;
    }

    public Action(ActionType type, int index, MonsterModel nouveauMonstre) {
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

    public AttackModel getAttaque() {
        return attaque;
    }

    public ObjectModel getObjet() {
        return objet;
    }

    public MonsterModel getNouveauMonstre() {
        return nouveauMonstre;
    }
}