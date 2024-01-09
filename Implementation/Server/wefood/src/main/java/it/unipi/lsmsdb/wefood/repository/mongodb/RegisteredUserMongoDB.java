package it.unipi.lsmsdb.wefood.repository.mongodb;

import java.util.ArrayList;
import java.util.List;

import org.bson.Document;
import org.springframework.security.crypto.bcrypt.BCrypt;

import com.mongodb.MongoException;

import it.unipi.lsmsdb.wefood.dto.PostDTO;
import it.unipi.lsmsdb.wefood.dto.RegisteredUserPageDTO;
import it.unipi.lsmsdb.wefood.model.RegisteredUser;
import it.unipi.lsmsdb.wefood.repository.base.BaseMongoDB;
import it.unipi.lsmsdb.wefood.repository.interfaces.RegisteredUserMongoDBInterface;

public class RegisteredUserMongoDB implements RegisteredUserMongoDBInterface {
    
    public RegisteredUser login(String username, String password) throws MongoException, IllegalArgumentException, IllegalStateException {

        String query = "db.User.find({username: \"" + username + "\"})";
        List<Document> result = BaseMongoDB.executeQuery(query);
        if(result.isEmpty()){
            // It does not exist a user with this username
            return null;
        }
        else{
            // It exists a user with this username
            Document user = result.get(0);

            if ((user.containsKey("type") && user.getString("type").equals("Admin")) || (user.containsKey("deleted") && user.getBoolean("deleted"))) {
                // The user is an Admin
                return null;
            } else {
                // The user is not an Admin
                if(BCrypt.checkpw(password, user.getString("password"))){
                    // The password is correct
                    return new RegisteredUser(user.getString("_id"), user.getString("username"), user.getString("password"), user.getString("name"), user.getString("surname"));
                } 
                else {
                    // The password is not correct
                    return null;
                }
            }

        }

    };

    public RegisteredUserPageDTO findRegisteredUserPageByUsername(String username) throws MongoException, IllegalArgumentException, IllegalStateException {
        String query = "db.User.find({username: \"" + username + "\"})";

        List<Document> result = BaseMongoDB.executeQuery(query);

        if(result.isEmpty())
            // It does not exist a user with this username
            return null; 
        
        Document user_doc = result.get(0);

        if(user_doc.containsKey("deleted") && user_doc.getBoolean("deleted"))
            // The user has been deleted
            return null;

        List<PostDTO> postDTOs = new ArrayList<PostDTO>();

        List<Document> posts = user_doc.getList(user_doc, Document.class);
        for(Document post : posts) { 
            String image = (post.get("image") == null) ? "DEFAULT" : post.get("image").toString();
            PostDTO postDTO = new PostDTO(post.get("idPost").toString(), image, post.getString("name"));  
            postDTOs.add(postDTO);
        }
        
        RegisteredUserPageDTO user = new RegisteredUserPageDTO(user_doc.get("_id").toString(), user_doc.getString("username"), postDTOs);
        
        return user;
    }

    public boolean modifyPersonalInformation(RegisteredUser user) throws MongoException, IllegalArgumentException, IllegalStateException {
        String  query = "db.User.updateOne({\r\n" + //
                        "    _id: " + user.getId() + ",\r\n" + //
                        "}, {\r\n" + //
                        "    $set: {\r\n" + //
                        "        password: \"" + user.getPassword() + "\",\r\n" + //
                        "        name: \"" + user.getName() + "\",\r\n" + //
                        "        surname: \"" + user.getSurname() + "\"\r\n" + //
                        "    }\r\n" + //
                        "})";

        List<Document> result = BaseMongoDB.executeQuery(query);
        System.out.println(result.get(0).toJson());
        // If it does not throw an exception, it means that the query has been executed successfully
        return true;
    };

    public boolean deleteUser(RegisteredUser user) throws MongoException, IllegalArgumentException, IllegalStateException {
        // We heve to unset the following fields: password, name, surname and posts
        // and then we have to set the field deleted to true

        String query = "db.User.updateOne({\r\n" + //
                       "    _id: " + user.getId() + ",\r\n" + //
                       "}, {\r\n" + //
                       "    $set: {\r\n" + //
                       "        deleted: true\r\n" + //
                       "    }\r\n" + //
                       "})";
        
        List<Document> result = BaseMongoDB.executeQuery(query);

        query = "db.User.updateOne({\r\n" + //
                       "    _id: " + user.getId() + ",\r\n" + //
                       "}, {\r\n" + //
                       "    $unset: {\r\n" + //
                       "        password: \"\",\r\n" + //
                       "        name: \"\",\r\n" + //
                       "        surname: \"\",\r\n" + //
                       "        posts: \"\"\r\n" + //
                       "    }\r\n" + //
                       "})";

        System.out.println(result.get(0).toJson());
        // If it does not throw an exception, it means that the query has been executed successfully
        return true;

    };

    public boolean banUser(String username) throws MongoException, IllegalArgumentException, IllegalStateException {
        String query = "db.User.updateOne({\r\n" + //
                       "    username: \"" + username + "\",\r\n" + //
                       "}, {\r\n" + //
                       "    $set: {\r\n" + //
                       "        deleted: true\r\n" + //
                       "    }\r\n" + //
                       "})";
        List<Document> result = BaseMongoDB.executeQuery(query);
        System.out.println(result.get(0).toJson());
        // If it does not throw an exception, it means that the query has been executed successfully
        return true;
    };

    public boolean unbanUser(String username) throws MongoException, IllegalArgumentException, IllegalStateException {
        String query = "db.User.updateOne({\r\n" + //
                       "    username: \"" + username + "\",\r\n" + //
                       "}, {\r\n" + //
                       "    $unset: {\r\n" + //
                       "        deleted: \"\"\r\n" + //
                       "    }\r\n" + //
                       "})";
        List<Document> result = BaseMongoDB.executeQuery(query);
        System.out.println(result.get(0).toJson());
        // If it does not throw an exception, it means that the query has been executed successfully
        return true;
    };

    public boolean cancelUserMongoDB(String registeredUsername) throws MongoException, IllegalArgumentException, IllegalStateException {
        String query = "db.User.deleteOne({\r\n" + //
                       "    username: \"" + registeredUsername + "\"\r\n" + //
                       "})";
        List<Document> result = BaseMongoDB.executeQuery(query);
        System.out.println(result.get(0).toJson());
        // If it does not throw an exception, it means that the query has been executed successfully
        return true;
    }

    public boolean addPost(RegisteredUser user, PostDTO postDTO) throws MongoException, IllegalArgumentException, IllegalStateException {
        String query = "db.User.updateOne({\r\n" + //
                       "    _id: " + user.getId() + ",\r\n" + //
                       "}, {\r\n" + //
                       "    $push: {\r\n" + //
                       "        posts: {\r\n" + //
                       "            idPost: " + postDTO.getId() + ",\r\n" + //
                       "            name: \"" + postDTO.getRecipeName() + "\",\r\n" + //
                       "            image: \"" + postDTO.getImage() + "\"\r\n" + //
                       "        }\r\n" + //
                       "    }\r\n" + //
                       "})"; //

        List<Document> result = BaseMongoDB.executeQuery(query);
        System.out.println(result.get(0).toJson());
        // If it does not throw an exception, it means that the query has been executed successfully
        return true;
    }

    public boolean removePost(RegisteredUser user, PostDTO postDTO) throws MongoException, IllegalArgumentException, IllegalStateException {
        String query = "db.User.updateOne({\r\n" + //
                       "    _id: " + user.getId() + ",\r\n" + //
                       "}, {\r\n" + //
                       "    $pull: {\r\n" + //
                       "        posts: {\r\n" + //
                       "            idPost: " + postDTO.getId() + "\r\n" + //
                       "        }\r\n" + //
                       "    }\r\n" + //
                       "})"; //

        List<Document> result = BaseMongoDB.executeQuery(query);
        System.out.println(result.get(0).toJson());
        // If it does not throw an exception, it means that the query has been executed successfully
        return true;
    }
}
