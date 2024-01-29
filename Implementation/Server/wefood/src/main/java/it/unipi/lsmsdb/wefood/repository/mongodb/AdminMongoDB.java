package it.unipi.lsmsdb.wefood.repository.mongodb;

import it.unipi.lsmsdb.wefood.dto.RegisteredUserDTO;
import it.unipi.lsmsdb.wefood.model.Admin;
import it.unipi.lsmsdb.wefood.repository.base.BaseMongoDB;
import it.unipi.lsmsdb.wefood.repository.interfaces.AdminMongoDBInterface;

import java.util.ArrayList;
import java.util.List;
import org.bson.Document;

import com.mongodb.MongoException;

import org.springframework.security.crypto.bcrypt.BCrypt;

public class AdminMongoDB implements AdminMongoDBInterface {

    
    public Admin loginAdmin(String username, String password) throws MongoException, IllegalArgumentException, IllegalStateException {
        String query = "db.User.find({username: \"" + username + "\"})";
        List<Document> result = BaseMongoDB.executeQuery(query);
        if(result.isEmpty()){
            // It does not exist a user with this username
            return null;
        }
        else{
            // It exists a user with this username
            Document admin = result.get(0);

            if (admin.containsKey("type") && admin.getString("type").equals("Admin")) {
                // The user is an Admin
                if(BCrypt.checkpw(password, admin.getString("password"))){
                    // The password is correct
                    return new Admin(admin.getString("username"), admin.getString("password"));
                } 
                else {
                    // The password is not correct
                    return null;
                }
            } else {
                // The user is not an Admin
                return null;
            }

        }
    }

    public List<RegisteredUserDTO> findBannedUsers() {
        String query = "db.User.find({" +
                "  deleted: true," +
                "  name: { $exists: true }" +
                "});";
        List<Document> results = BaseMongoDB.executeQuery(query);
        if(results.isEmpty()){
            // There are no banned users
            return null;
        }
        else{
            // It exists a banned user/users
            List<Document> result = BaseMongoDB.executeQuery(query);
            List<RegisteredUserDTO> registeredUsers = new ArrayList<RegisteredUserDTO>();

            for(Document document : result) {
                registeredUsers.add(new RegisteredUserDTO(
                        document.getObjectId("_id").toHexString(),
                        document.getString("username")
                ));
            }

            return registeredUsers;
        }
    }
    
}