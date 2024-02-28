package it.unipi.lsmsdb.wefood.dto;


import com.fasterxml.jackson.annotation.JsonProperty;

public class PostDTO {
    
    private String _id;
    private String image;
    private String recipeName;

    
    public PostDTO(@JsonProperty("id") String _id,
                   @JsonProperty("image") String image,
                   @JsonProperty("recipeName") String recipeName) {
        this._id = _id;
        this.image = image;
        this.recipeName = recipeName;
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