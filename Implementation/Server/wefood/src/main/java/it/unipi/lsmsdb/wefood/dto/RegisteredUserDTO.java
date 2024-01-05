package it.unipi.lsmsdb.wefood.dto;


public class RegisteredUserDTO { 

    private String _id;
    private String username;

    public RegisteredUserDTO(String _id, String username) {
        this._id = "ObjectId('" + _id + "')";
        this.username = username;
    }

    public String getId() {
        return this._id;
    }

    public String getUsername() {
        return this.username;
    }

    public void setId(String _id) {
        this._id = _id;
    }

    public void setUsername(String username) {
        this.username = username;
    }
    
}