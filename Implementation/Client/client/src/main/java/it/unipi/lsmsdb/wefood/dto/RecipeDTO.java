package it.unipi.lsmsdb.wefood.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class RecipeDTO {

    private String _id;
    private String name;

    public RecipeDTO(@JsonProperty("id") String _id,
                     @JsonProperty("name") String name) {
        this._id = _id;
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    public String getID() {
        return this._id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setID(String _id) {
        this._id = _id;
    }

    public String editedGetID() {
        return "ObjectId('" + this._id + "')";
    }
}
