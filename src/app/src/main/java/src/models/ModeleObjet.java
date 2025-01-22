package src.models;

public class ModeleObjet {
    private String nom;
    private TypeObjet type;

    public ModeleObjet(String nom, TypeObjet type) {
        this.nom = nom;
        this.type = type;
    }

    public String getNom() {
        return nom;
    }

    public TypeObjet getType() {
        return type;
    }

    public void utiliser(Monstre monstre){
        if (this.type == TypeObjet.POTION){
            monstre.soigner(50);
        } else if(this.type == TypeObjet.MEDICAMENT){
            monstre.guerir();
        }
    }
}