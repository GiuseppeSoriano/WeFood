package it.unipi.lsmsdb.wefood.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class RegisteredUser extends User {

    private String _id;
    private String username;
    private String password;
    private String name;
    private String surname;


    public RegisteredUser(@JsonProperty("id") String _id,
                          @JsonProperty("username") String username,
                          @JsonProperty("password") String password,
                          @JsonProperty("name") String name,
                          @JsonProperty("surname") String surname) {
        this._id = _id;
        this.username = username;
        this.password = password;
        this.name = name;
        this.surname = surname;
    }
    public RegisteredUser(String username) {
        this.username = username;
    }

    public String getId() {
        return this._id;
    }

    public void setId(String id) {
        this._id = id;
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
}
