package it.unipi.lsmsdb.wefood.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Admin extends User {

    private String username;
    private String password;

    public Admin(@JsonProperty("username") String username,
                 @JsonProperty("password") String password){
        this.username = username;
        this.password = password;
    }

    public String getUsername(){
        return username;
    }

    public String getPassword(){
        return password;
    }

    public void setUsername(String username){
        this.username = username;
    }

    public void setPassword(String password){
        this.password = password;
    }

}
