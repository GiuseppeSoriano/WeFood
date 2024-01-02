package it.unipi.lsmsdb.wefood.dto;

import it.unipi.lsmsdb.wefood.model.Recipe;

public class PostDTO {
    
    private String _id;
    private String image;
    private String recipeName;

    
    public PostDTO(String _id, String image, Recipe recipe) {
        this._id = _id;
        this.image = image;
        this.recipeName = recipe.getName();
    }

    public void setId(String _id) {
        this._id = _id;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public void setRecipeName(String recipeName) {
        this.recipeName = recipeName;
    }

    public String getId() {
        return this._id;
    }
    
    public String getImage() {
        return this.image;
    }
    
    public String getRecipeName() {
        return this.recipeName;
    }
}