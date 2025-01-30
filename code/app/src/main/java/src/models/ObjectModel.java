package src.models;

public class ObjectModel {
    private String name;
    private TypeObject type;

    /**
     * The constructor the object.
     * @param name The object's name.
     * @param type Teh object's type.
     */
    public ObjectModel(String name, TypeObject type) {
        this.name = name;
        this.type = type;
    }

    /**
     * Get the object's name.
     * @return The name as a string.
     */
    public String getNom() {
        return name;
    }

    /**
     * Get the object's type.
     * @return The object's type.
     */
    public TypeObject getType() {
        return type;
    }

    /**
     * Use method to consume an object.
     * @param monstre The effect receiver (a monster);
     * @return The result string.
     */
    public String use(Monster monstre){
        StringBuilder result = new StringBuilder();
        if (this.type == TypeObject.POTION){
            result.append(monstre.soigner(50)); 
        } else if(this.type == TypeObject.MEDICAMENT){
            result.append(monstre.guerir());
        }
        return result.toString();
    }
}