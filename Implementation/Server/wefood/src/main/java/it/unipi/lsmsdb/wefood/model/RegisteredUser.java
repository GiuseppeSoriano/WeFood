package it.unipi.lsmsdb.wefood.model;

import java.util.ArrayList;
import java.util.List;

public class RegisteredUser extends User {
    private String username;
    private String password;
    private String name;
    private String surname;
    private List<Post> posts;

    public RegisteredUser(String username, String password, String name, String surname) {
        this.username = username;
        this.password = password;
        this.name = name;
        this.surname = surname;
        this.posts = new ArrayList<Post>();
    }
    public RegisteredUser(String username) {
        this.username = username;
        this.posts = new ArrayList<Post>();
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public List<Post> getPosts() {
        return posts;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }
    
    public void addPost(Post post) {
        this.posts.add(post);
    }
    
    public void removePost(Post post) {
        this.posts.remove(post);
    }

}
