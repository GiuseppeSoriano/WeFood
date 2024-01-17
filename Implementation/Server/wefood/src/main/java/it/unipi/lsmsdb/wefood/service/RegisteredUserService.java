package it.unipi.lsmsdb.wefood.service;

import java.util.List;

import org.neo4j.driver.exceptions.Neo4jException;
import org.springframework.stereotype.Service;

import com.mongodb.MongoException;

import it.unipi.lsmsdb.wefood.dao.RegisteredUserDAO;
import it.unipi.lsmsdb.wefood.dto.RegisteredUserDTO;
import it.unipi.lsmsdb.wefood.dto.RegisteredUserPageDTO;
import it.unipi.lsmsdb.wefood.model.RegisteredUser;

@Service
public class RegisteredUserService {
    PostService postService = new PostService();

    public RegisteredUser login(String username, String password){
        try{
            return RegisteredUserDAO.login(username, password);
        }
        // Other types of exceptions can be handled if necessary: MongoException, IllegalArgumentException, IllegalStateException
        catch(Exception e) {
            System.err.println("Exception in RegisteredUserDAO.login: " + e.getMessage());
            return null;
        }
    }

    public RegisteredUserPageDTO findRegisteredUserPageByUsername(String username) {
        try{
            return RegisteredUserDAO.findRegisteredUserPageByUsername(username);
        }
        // Other types of exceptions can be handled if necessary: MongoException, IllegalArgumentException, IllegalStateException
        catch(Exception e) {
            System.err.println("Exception in RegisteredUserDAO.findRegisteredUserPageByUsername: " + e.getMessage());
            return null;
        }
    }

    public boolean modifyPersonalInformation(RegisteredUser user) {
        try{
            return RegisteredUserDAO.modifyPersonalInformation(user);
        }
        // Other types of exceptions can be handled if necessary: MongoException, IllegalArgumentException, IllegalStateException
        catch(Exception e) {
            System.err.println("Exception in RegisteredUserDAO.modifyPersonalInformation: " + e.getMessage());
            return false;
        }
    }

    public boolean deleteUser(RegisteredUserPageDTO user) {
        try{
            // We need to delete all the posts of the user before deleting him/her
            boolean result = postService.deleteAllUserPosts(user.getPosts());
            if(!result)
                // Posts not deleted from MongoDB (Post Collection)
                return false;

            // Now we can delete the user (MongoDB)
            RegisteredUserDAO.deleteUserMongoDB(user);

            // And finally we can delete the user from Neo4j (by launching a Thread)
            Thread neo4jThread = new Thread(() -> {
                try {
                    RegisteredUserDAO.deleteUserNeo4j(user);
                } catch (Exception e) {
                    System.err.println("Exception in RegisteredUserDAO.deleteUserNeo4j: " + e.getMessage());
                    System.err.println("Databases are not synchronized, see User:username: " + user.getUsername());
                }
            });
            neo4jThread.start();

            return true;
        }
        // Other types of exceptions can be handled if necessary: MongoException, IllegalArgumentException, IllegalStateException
        catch(Exception e){
            System.err.println("Exception in RegisteredUserDAO.deleteUser: " + e.getMessage());
            System.err.println("MongoDB is not consistent anymore, see User:username: " + user.getUsername());
            return false;
        }
    }

    public boolean banUser(String username) {
        try{
            return RegisteredUserDAO.banUser(username);
        }
        // Other types of exceptions can be handled if necessary: MongoException, IllegalArgumentException, IllegalStateException
        catch(Exception e) {
            System.err.println("Exception in RegisteredUserDAO.banUser: " + e.getMessage());
            return false;
        }
    }

    public boolean unbanUser(String username) {
        try{
            return RegisteredUserDAO.unbanUser(username);
        }
        // Other types of exceptions can be handled if necessary: MongoException, IllegalArgumentException, IllegalStateException
        catch(Exception e) {
            System.err.println("Exception in RegisteredUserDAO.unbanUser: " + e.getMessage());
            return false;
        }
    }

    // Neo4j
    public boolean followUser(RegisteredUserDTO user, String usernameToFollow) {
        try{
            return RegisteredUserDAO.followUser(user, usernameToFollow);
        }
        // Other types of exceptions can be handled if necessary: Neo4jException, IllegalStateException
        catch(Exception e) {
            System.err.println("Exception in RegisteredUserDAO.followUser: " + e.getMessage());
            return false;
        }
    }

    public boolean unfollowUser(RegisteredUserDTO user, String usernameToUnfollow) {
        try{
            return RegisteredUserDAO.unfollowUser(user, usernameToUnfollow);
        }
        // Other types of exceptions can be handled if necessary: Neo4jException, IllegalStateException
        catch(Exception e) {
            System.err.println("Exception in RegisteredUserDAO.unfollowUser: " + e.getMessage());
            return false;
        }
    }

    public List<RegisteredUserDTO> findFriends(RegisteredUserDTO user) {
        try{
            return RegisteredUserDAO.findFriends(user);
        }
        // Other types of exceptions can be handled if necessary: Neo4jException, IllegalStateException
        catch(Exception e) {
            System.err.println("Exception in RegisteredUserDAO.findFriends: " + e.getMessage());
            return null;
        }
    }

    public List<RegisteredUserDTO> findFollowers(RegisteredUserDTO user) {
        try{
            return RegisteredUserDAO.findFollowers(user);
        }
        // Other types of exceptions can be handled if necessary: Neo4jException, IllegalStateException
        catch(Exception e) {
            System.err.println("Exception in RegisteredUserDAO.findFollowers: " + e.getMessage());
            return null;
        }
    }

    public List<RegisteredUserDTO> findFollowed(RegisteredUserDTO user) {
        try{
            return RegisteredUserDAO.findFollowed(user);
        }
        // Other types of exceptions can be handled if necessary: Neo4jException, IllegalStateException
        catch(Exception e) {
            System.err.println("Exception in RegisteredUserDAO.findFollowed: " + e.getMessage());
            return null;
        }
    }

    public List<RegisteredUserDTO> findUsersToFollowBasedOnUserFriends(RegisteredUserDTO user) {
        try{
            return RegisteredUserDAO.findUsersToFollowBasedOnUserFriends(user);
        }
        // Other types of exceptions can be handled if necessary: Neo4jException, IllegalStateException
        catch(Exception e) {
            System.err.println("Exception in RegisteredUserDAO.findUsersToFollowBasedOnUserFriends: " + e.getMessage());
            return null;
        }
    }

    public List<RegisteredUserDTO> findMostFollowedUsers() {
        try{
            return RegisteredUserDAO.findMostFollowedUsers();
        }
        // Other types of exceptions can be handled if necessary: Neo4jException, IllegalStateException
        catch(Exception e) {
            System.err.println("Exception in RegisteredUserDAO.findMostFollowedUsers: " + e.getMessage());
            return null;
        }
    }

    public List<RegisteredUserDTO> findUsersByIngredientUsage(String ingredientName) {
        try{
            return RegisteredUserDAO.findUsersByIngredientUsage(ingredientName);
        }
        // Other types of exceptions can be handled if necessary: Neo4jException, IllegalStateException
        catch(Exception e) {
            System.err.println("Exception in RegisteredUserDAO.findUsersByIngredientUsage: " + e.getMessage());
            return null;
        }
    }

}
