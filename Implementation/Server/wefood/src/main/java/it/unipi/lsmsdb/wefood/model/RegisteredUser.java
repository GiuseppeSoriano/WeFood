package it.unipi.lsmsdb.wefood.model;

public class RegisteredUser extends User {

    private String _id;
    private String username;
    private String password;
    private String name;
    private String surname;


    public RegisteredUser(String _id, String username, String password, String name, String surname) {
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
        return _id;
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
