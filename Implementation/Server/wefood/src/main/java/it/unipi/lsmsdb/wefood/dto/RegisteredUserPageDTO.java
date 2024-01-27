package it.unipi.lsmsdb.wefood.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class RegisteredUserPageDTO {

    private String _id;
    private String username;
    private List<PostDTO> posts;

    public RegisteredUserPageDTO(@JsonProperty("id") String _id, String username, List<PostDTO> posts) {
        this._id = _id;
        this.username = username;
        this.posts = posts;
    }

    public String editedGetId() {
        return "ObjectId('" + this._id + "')";
    }

    public String getId(){
        return _id;
    }

    public String getUsername() {
        return username;
    }

    public List<PostDTO> getPosts() {
        return posts;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPosts(List<PostDTO> posts) {
        this.posts = posts;
    }
}
