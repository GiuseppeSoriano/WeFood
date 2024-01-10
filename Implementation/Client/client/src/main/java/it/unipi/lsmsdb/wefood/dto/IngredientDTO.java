package it.unipi.lsmsdb.wefood.dto;

public class IngredientDTO {

    private String _id;
    private String name;

    public IngredientDTO(String _id, String name) {
        this._id = _id;
        this.name = name;
    }

    public String mongoDBgetId() {
        return "ObjectId('" + this._id + "')";
    }

    public String neo4JgetId(){
        return _id;
    }

    public String getName() {
        return this.name;
    }

    public void setId(String _id) {
        this._id = _id;
    }

    public void setName(String name) {
        this.name = name;
    }
    
}
