package src.models;

public class ObjectModel {
    private String nom;
    private TypeObject type;

    public ObjectModel(String nom, TypeObject type) {
        this.nom = nom;
        this.type = type;
    }

    public String getNom() {
        return nom;
    }

    public TypeObject getType() {
        return type;
    }

    public void utiliser(Monster monstre){
        if (this.type == TypeObject.POTION){
            monstre.soigner(50);
        } else if(this.type == TypeObject.MEDICAMENT){
            monstre.guerir();
        }
    }
}