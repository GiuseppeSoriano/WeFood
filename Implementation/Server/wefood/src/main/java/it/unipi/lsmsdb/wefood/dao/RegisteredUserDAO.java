package it.unipi.lsmsdb.wefood.dao;

import java.util.List;

import com.mongodb.MongoException;
import it.unipi.lsmsdb.wefood.dto.IngredientDTO;
import it.unipi.lsmsdb.wefood.dto.PostDTO;
import it.unipi.lsmsdb.wefood.dto.RegisteredUserDTO;
import it.unipi.lsmsdb.wefood.dto.RegisteredUserPageDTO;
import it.unipi.lsmsdb.wefood.model.RegisteredUser;
import it.unipi.lsmsdb.wefood.repository.interfaces.RegisteredUserMongoDBInterface;
import it.unipi.lsmsdb.wefood.repository.interfaces.RegisteredUserNeo4jInterface;
import it.unipi.lsmsdb.wefood.repository.mongodb.RegisteredUserMongoDB;
import it.unipi.lsmsdb.wefood.repository.neo4j.RegisteredUserNeo4j;
import org.neo4j.driver.exceptions.Neo4jException;

public class RegisteredUserDAO {
    private final static RegisteredUserNeo4jInterface registeredUserNeo4j = new RegisteredUserNeo4j();
    private final static RegisteredUserMongoDBInterface registeredUserMongoDB = new RegisteredUserMongoDB();

    // MongoDB
    public static RegisteredUser login(String username, String password) throws MongoException, IllegalArgumentException, IllegalStateException {
        return registeredUserMongoDB.login(username, password);
    };

    public static RegisteredUserPageDTO findRegisteredUserPageByUsername(String username) throws MongoException, IllegalArgumentException, IllegalStateException {
        return registeredUserMongoDB.findRegisteredUserPageByUsername(username);
    }

    public static boolean modifyPersonalInformation(RegisteredUser user) throws MongoException, IllegalArgumentException, IllegalStateException {
        return registeredUserMongoDB.modifyPersonalInformation(user);
    };

    public static boolean deleteUser(RegisteredUser user) throws MongoException, IllegalArgumentException, IllegalStateException {
        return registeredUserMongoDB.deleteUser(user);
    };

    public static boolean banUser(String username) throws MongoException, IllegalArgumentException, IllegalStateException {
        return registeredUserMongoDB.banUser(username);
    }
    public static boolean unbanUser(String username) throws MongoException, IllegalArgumentException, IllegalStateException {
        return registeredUserMongoDB.unbanUser(username);
    }
    public static boolean cancelUser(String username) throws MongoException, IllegalArgumentException, IllegalStateException {
        return registeredUserMongoDB.cancelUser(username);
    }

    public static boolean addPost(RegisteredUser user, PostDTO postDTO) throws MongoException, IllegalArgumentException, IllegalStateException {
        return registeredUserMongoDB.addPost(user, postDTO);
    }

    public static boolean removePost(RegisteredUser user, PostDTO postDTO) throws MongoException, IllegalArgumentException, IllegalStateException {
        return registeredUserMongoDB.removePost(user, postDTO);
    }

    // Neo4j
    public static boolean createUserUsedIngredient(RegisteredUserDTO registeredUserDTO, List<String> ingredientNames) throws IllegalStateException, Neo4jException {
        return registeredUserNeo4j.createUserUsedIngredient(registeredUserDTO, ingredientNames);
    }

    public static boolean deleteUserUsedIngredient(RegisteredUserDTO registeredUserDTO, List<String> ingredientNames) throws IllegalStateException, Neo4jException{
        return registeredUserNeo4j.deleteUserUsedIngredient(registeredUserDTO, ingredientNames);        
    }

    public static boolean followUser(RegisteredUserDTO user, String usernameToFollow) throws IllegalStateException, Neo4jException{
        return registeredUserNeo4j.followUser(user, usernameToFollow);
    } 

    public static boolean unfollowUser(RegisteredUserDTO user, String usernameToUnfollow) throws IllegalStateException, Neo4jException{
        return registeredUserNeo4j.unfollowUser(user, usernameToUnfollow);
    }

    public static List<RegisteredUserDTO> findFriends(RegisteredUserDTO user) throws IllegalStateException, Neo4jException{
        return registeredUserNeo4j.findFriends(user);
    }

    public static List<RegisteredUserDTO> findFollowers(RegisteredUserDTO user) throws IllegalStateException, Neo4jException{
        return registeredUserNeo4j.findFollowers(user);
    }

    public static List<RegisteredUserDTO> findFollowed(RegisteredUserDTO user) throws IllegalStateException, Neo4jException{
        return registeredUserNeo4j.findFollowed(user);
    }

    public static List<RegisteredUserDTO> findUsersToFollowBasedOnUserFriends(RegisteredUserDTO user) throws IllegalStateException, Neo4jException{
        return registeredUserNeo4j.findUsersToFollowBasedOnUserFriends(user);
    }

    public static List<RegisteredUserDTO> findMostFollowedUsers() throws IllegalStateException, Neo4jException{
        return registeredUserNeo4j.findMostFollowedUsers();
    } //valid also for showing them

    public static List<RegisteredUserDTO> findUsersByIngredientUsage(IngredientDTO ingredientDTO) throws IllegalStateException, Neo4jException{
        return registeredUserNeo4j.findUsersByIngredientUsage(ingredientDTO);
    }

    public static void cancelUser(RegisteredUser registeredUser) {
    }


}
