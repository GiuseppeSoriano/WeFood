package it.unipi.lsmsdb.wefood.repository.mongodb;

import java.util.List;
import org.bson.Document;
import com.mongodb.MongoException;
import org.springframework.security.crypto.bcrypt.BCrypt;

import it.unipi.lsmsdb.wefood.model.RegisteredUser;
import it.unipi.lsmsdb.wefood.repository.base.BaseMongoDB;
import it.unipi.lsmsdb.wefood.repository.interfaces.UnregisteredUserMongoDBInterface;

public class UnregisteredUserMongoDB implements UnregisteredUserMongoDBInterface {
    
    public RegisteredUser register(String username, String password, String name, String surname) throws MongoException, IllegalArgumentException, IllegalStateException {
        
        String hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt());
        
        String query = "db.User.insertOne({\r\n" + //
                       "    username: \"" + username+ "\",\r\n" + //
                       "    password: \"" + hashedPassword + "\",\r\n" + //
                       "    name: \"" + name + "\",\r\n" + //
                       "    surname: \"" + surname + "\"\r\n" + //
                       "})";


        List<Document> result = BaseMongoDB.executeQuery(query);
        System.out.println(result.get(0).toJson());
        // If it does not throw an exception, it means that the query has been executed successfully
        
        String _id = result.get(0).get("_id").toString();
        
        RegisteredUser user = new RegisteredUser(_id, username, hashedPassword, name, surname);
        return user;
    }
}
