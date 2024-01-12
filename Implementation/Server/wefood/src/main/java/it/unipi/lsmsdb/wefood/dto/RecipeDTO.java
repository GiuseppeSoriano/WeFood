package it.unipi.lsmsdb.wefood.dto;

public class RecipeDTO {

    private String _id;
    private String name;

    public RecipeDTO(String _id, String name) {
        this._id = _id;
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    public String editedGetId() {
        return this._id;
    }

    public String getId() {
        return this._id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setID(String _id) {
        this._id = _id;
    }
}
