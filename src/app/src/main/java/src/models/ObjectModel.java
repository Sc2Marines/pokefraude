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

    public String utiliser(Monster monstre){
        StringBuilder result = new StringBuilder();
        if (this.type == TypeObject.POTION){
            result.append(monstre.soigner(50)); 
        } else if(this.type == TypeObject.MEDICAMENT){
            result.append(monstre.guerir());
        }
        return result.toString();
    }
}