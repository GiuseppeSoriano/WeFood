package it.unipi.lsmsdb.wefood.repository.mongodb;

import it.unipi.lsmsdb.wefood.model.Admin;
import it.unipi.lsmsdb.wefood.repository.base.BaseMongoDB;
import it.unipi.lsmsdb.wefood.repository.interfaces.AdminMongoDBInterface;
import java.util.List;
import org.bson.Document;

import com.mongodb.MongoException;


public class AdminMongoDB implements AdminMongoDBInterface {

    
    public Admin loginAdmin(String username, String password) throws MongoException, IllegalArgumentException, IllegalStateException {
        String query = "db.User.find({username: \"" + username + "\"})";
        List<Document> result = BaseMongoDB.executeQuery(query);
        if(result.isEmpty()){
            // It does not exist a user with this username
        }
        else{
            // It exists a user with this username
            
            // check if user is an admin (TYPE)
            // check if the password is correct
            // https://docs.spring.io/spring-security/site/docs/current/api/org/springframework/security/crypto/bcrypt/BCrypt.html
            // ...

        }
        //ritornare l'admin
        return null;
    };
    
}