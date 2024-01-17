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

        String query = "db.User.find({username: \"" + username + "\"}, {posts:0})";
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
                    System.out.println(user.getObjectId("_id").toHexString());
                    return new RegisteredUser(user.getObjectId("_id").toHexString(), user.getString("username"), user.getString("password"), user.getString("name"), user.getString("surname"));
                } 
                else {
                    // The password is not correct
                    return null;
                }
            }

        }

    };

    public RegisteredUserPageDTO findRegisteredUserPageByUsername(String username) throws MongoException, IllegalArgumentException, IllegalStateException {
        String query = "db.User.find({username: \"" + username + "\"}, {username:1, posts:1})";

        List<Document> result = BaseMongoDB.executeQuery(query);

        if(result.isEmpty())
            // It does not exist a user with this username
            return null; 
        
        Document user_doc = result.get(0);

        if(user_doc.containsKey("deleted") && user_doc.getBoolean("deleted"))
            // The user has been deleted
            return null;

        List<PostDTO> postDTOs = new ArrayList<PostDTO>();

        List<Document> posts = user_doc.getList("posts", Document.class);
        for(Document post : posts) { 
            String image = (post.get("image") == null) ? "DEFAULT" : post.get("image").toString();
            PostDTO postDTO = new PostDTO(post.getObjectId("idPost").toHexString(), image, post.getString("name"));  
            postDTOs.add(postDTO);
        }
        
        RegisteredUserPageDTO user = new RegisteredUserPageDTO(user_doc.getObjectId("_id").toHexString(), user_doc.getString("username"), postDTOs);
        
        return user;
    }

    public boolean modifyPersonalInformation(RegisteredUser user) throws MongoException, IllegalArgumentException, IllegalStateException {
        String hashedPassword = BCrypt.hashpw(user.getPassword(), BCrypt.gensalt());
        String  query = "db.User.updateOne({" + //
                        "    _id: " + user.editedGetId() +  //
                        "}, {" + //
                        "    $set: {" + //
                        "        password: \"" + hashedPassword + "\"," + //
                        "        name: \"" + user.getName() + "\"," + //
                        "        surname: \"" + user.getSurname() + "\"" + //
                        "    }" + //
                        "})";

        List<Document> result = BaseMongoDB.executeQuery(query);
        System.out.println(result.get(0).toJson());
        // If it does not throw an exception, it means that the query has been executed successfully
        return true;
    };

    public boolean deleteUser(String _id) throws MongoException, IllegalArgumentException, IllegalStateException {
        // We have to unset the following fields: password, name, surname and posts,
        // and then we have to set the field deleted to true

        String query = "db.User.updateOne({" + //
                       "    _id: " + _id + //
                       "}, {" + //
                       "    $unset: {" + //
                       "        password: \"\"," + //
                       "        name: \"\"," + //
                       "        surname: \"\"," + //
                       "        posts: \"\"" + //
                       "    }," + //
                       "    $set: {" + //
                       "        deleted: true" + //
                       "    }" + //
                       "})";

        List<Document> result = BaseMongoDB.executeQuery(query);

        System.out.println(result.get(0).toJson());
        // If it does not throw an exception, it means that the query has been executed successfully
        return true;
    };

    public boolean banUser(String username) throws MongoException, IllegalArgumentException, IllegalStateException {
        String query = "db.User.updateOne({" + //
                       "    username: \"" + username + "\"" + //
                       "}, {" + //
                       "    $set: {" + //
                       "        deleted: true" + //
                       "    }" + //
                       "})";
        List<Document> result = BaseMongoDB.executeQuery(query);
        System.out.println(result.get(0).getLong("result"));
        if(result.get(0).getLong("result") == 0)
            // It does not exist a user with this username
            return false;
        else
            // The user has been banned
            // If it does not throw an exception, it means that the query has been executed successfully
            return true;
    };

    public boolean unbanUser(String username) throws MongoException, IllegalArgumentException, IllegalStateException {
        String query = "db.User.updateOne({" + //
                       "    username: \"" + username + "\"" + //
                       "}, {" + //
                       "    $unset: {" + //
                       "        deleted: \"\"" + //
                       "    }" + //
                       "})";
        List<Document> result = BaseMongoDB.executeQuery(query);
        System.out.println(result.get(0).getLong("result"));
        // If it does not throw an exception, it means that the query has been executed successfully
        if(result.get(0).getLong("result") == 0)
            // It does not exist a user with this username
            return false;
        else
            // The user has been unbanned
            // If it does not throw an exception, it means that the query has been executed successfully
            return true;
    };

    public boolean cancelUserMongoDB(String registeredUsername) throws MongoException, IllegalArgumentException, IllegalStateException {
        String query = "db.User.deleteOne({" + //
                       "    username: \"" + registeredUsername + "\"" + //
                       "})";
        List<Document> result = BaseMongoDB.executeQuery(query);
        System.out.println(result.get(0).toJson());
        // If it does not throw an exception, it means that the query has been executed successfully
        return true;
    }

    public boolean addPost(RegisteredUser user, PostDTO postDTO) throws MongoException, IllegalArgumentException, IllegalStateException {
        String image_string = (postDTO.getImage() == null) ? "" : ", image: \"" + postDTO.getImage() + "\"";
        String query = "db.User.updateOne({" + //
                       "    _id: " + user.editedGetId() + //
                       "}, {" + //
                       "    $push: {" + //
                       "        posts: {" + //
                       "            idPost: " + postDTO.editedGetId() + "," + //
                       "            name: \"" + postDTO.getRecipeName() + "\"" + //
                                   image_string + //
                       "        }" + //
                       "    }" + //
                       "})"; //

        List<Document> result = BaseMongoDB.executeQuery(query);
        System.out.println(result.get(0).toJson());
        // If it does not throw an exception, it means that the query has been executed successfully
        return true;
    }

    public boolean removePost(RegisteredUser user, PostDTO postDTO) throws MongoException, IllegalArgumentException, IllegalStateException {
        String query = "db.User.updateOne({" + //
                       "    _id: " + user.editedGetId() + //
                       "}, {" + //
                       "    $pull: {" + //
                       "        posts: {" + //
                       "            idPost: " + postDTO.editedGetId() +  //
                       "        }" + //
                       "    }" + //
                       "})"; //

        List<Document> result = BaseMongoDB.executeQuery(query);
        System.out.println(result.get(0).toJson());
        // If it does not throw an exception, it means that the query has been executed successfully
        return true;
    }
}
