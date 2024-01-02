package it.unipi.lsmsdb.wefood.repository.mongodb;

import java.util.List;

import org.bson.Document;

import it.unipi.lsmsdb.wefood.model.RegisteredUser;
import it.unipi.lsmsdb.wefood.repository.base.BaseMongoDB;
import it.unipi.lsmsdb.wefood.repository.interfaces.RegisteredUserMongoDBInterface;

public class RegisteredUserMongoDB implements RegisteredUserMongoDBInterface {
    private final String collectionName = "User";


    public RegisteredUser login(String username, String password) {
        String query = "db."+collectionName+".find({username: \"" + username + "\", password: \"" + password + "\"})";
        //BaseMongoDB.getMongoClient();
        List<Document> registeredUserDocument = BaseMongoDB.executeQuery(query);
        if(registeredUserDocument.size() == 0) {
            return null;
        } else {
            RegisteredUser registeredUser = new RegisteredUser(
                registeredUserDocument.get(0).getString("username"),
                registeredUserDocument.get(0).getString("password"),
                registeredUserDocument.get(0).getString("name"),
                registeredUserDocument.get(0).getString("surname")
            );

            return registeredUser;
        }
    };

    public RegisteredUser findRegisteredUserByUsername(String username) {
        String query = "db."+collectionName+".find({username: \"" + username + "\"})";
        //BaseMongoDB.getMongoClient();
        List<Document> registeredUserDocument = BaseMongoDB.executeQuery(query);
        if(registeredUserDocument.size() == 0) {
            return null;
        } else {
            RegisteredUser registeredUser = new RegisteredUser(
                registeredUserDocument.get(0).getString("username"),
                registeredUserDocument.get(0).getString("password"),
                registeredUserDocument.get(0).getString("name"),
                registeredUserDocument.get(0).getString("surname")
            );

            return registeredUser;
        }
    };

    public boolean modifyPersonalProfile(RegisteredUser user) {
        //come garantire la consistenza?
        return true;
    };

    public boolean deleteUser(RegisteredUser user) {
        // String query = "db."+collectionName+".deleteOne({username: \"" + user.getUsername() + "\"})";
        //BaseMongoDB.getMongoClient();
        List<Document> result = BaseMongoDB.executeQuery(query);
        if (result == null) {
            return false;
        } else {
            return true;
        }
    };
}
