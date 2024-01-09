package it.unipi.lsmsdb.wefood.service;

import java.util.List;

import org.neo4j.driver.exceptions.Neo4jException;
import org.springframework.stereotype.Service;

import com.mongodb.MongoException;

import it.unipi.lsmsdb.wefood.dao.RegisteredUserDAO;
import it.unipi.lsmsdb.wefood.dto.IngredientDTO;
import it.unipi.lsmsdb.wefood.dto.RegisteredUserDTO;
import it.unipi.lsmsdb.wefood.dto.RegisteredUserPageDTO;
import it.unipi.lsmsdb.wefood.model.RegisteredUser;

@Service
public class RegisteredUserService {

    public RegisteredUser login(String username, String password){
        try{
            return RegisteredUserDAO.login(username, password);
        }
        catch(MongoException e){
            System.out.println("MongoException in RegisteredUserDAO.login: " + e.getMessage());
            return null;
        }
        catch(IllegalArgumentException e){
            System.out.println("IllegalArgumentException in RegisteredUserDAO.login: " + e.getMessage());
            return null;
        }
        catch(IllegalStateException e){
            System.out.println("IllegalStateException in RegisteredUserDAO.login: " + e.getMessage());
            return null;
        }
        catch(Exception e){
            System.out.println("Exception in RegisteredUserDAO.login: " + e.getMessage());
            return null;
        }
    }

    public RegisteredUserPageDTO findRegisteredUserPageByUsername(String username) throws MongoException, IllegalArgumentException, IllegalStateException {
        try{
            return RegisteredUserDAO.findRegisteredUserPageByUsername(username);
        }
        catch(MongoException e){
            System.out.println("MongoException in RegisteredUserDAO.findRegisteredUserPageByUsername: " + e.getMessage());
            return null;
        }
        catch(IllegalArgumentException e){
            System.out.println("IllegalArgumentException in RegisteredUserDAO.findRegisteredUserPageByUsername: " + e.getMessage());
            return null;
        }
        catch(IllegalStateException e){
            System.out.println("IllegalStateException in RegisteredUserDAO.findRegisteredUserPageByUsername: " + e.getMessage());
            return null;
        }
        catch(Exception e){
            System.out.println("Exception in RegisteredUserDAO.findRegisteredUserPageByUsername: " + e.getMessage());
            return null;
        }
    }


    public boolean modifyPersonalInformation(RegisteredUser user) throws MongoException, IllegalArgumentException, IllegalStateException {
        try{
            return RegisteredUserDAO.modifyPersonalInformation(user);
        }
        catch(MongoException e){
            System.out.println("MongoException in RegisteredUserDAO.modifyPersonalInformation: " + e.getMessage());
            return false;
        }
        catch(IllegalArgumentException e){
            System.out.println("IllegalArgumentException in RegisteredUserDAO.modifyPersonalInformation: " + e.getMessage());
            return false;
        }
        catch(IllegalStateException e){
            System.out.println("IllegalStateException in RegisteredUserDAO.modifyPersonalInformation: " + e.getMessage());
            return false;
        }
        catch(Exception e){
            System.out.println("Exception in RegisteredUserDAO.modifyPersonalInformation: " + e.getMessage());
            return false;
        }
    }


    public boolean deleteUser(RegisteredUser user) throws MongoException, IllegalArgumentException, IllegalStateException {
        try{
            return RegisteredUserDAO.deleteUser(user);
        }
        catch(MongoException e){
            System.out.println("MongoException in RegisteredUserDAO.deleteUser: " + e.getMessage());
            return false;
        }
        catch(IllegalArgumentException e){
            System.out.println("IllegalArgumentException in RegisteredUserDAO.deleteUser: " + e.getMessage());
            return false;
        }
        catch(IllegalStateException e){
            System.out.println("IllegalStateException in RegisteredUserDAO.deleteUser: " + e.getMessage());
            return false;
        }
        catch(Exception e){
            System.out.println("Exception in RegisteredUserDAO.deleteUser: " + e.getMessage());
            return false;
        }
    }

    public boolean banUser(String username) throws MongoException, IllegalArgumentException, IllegalStateException {
        try{
            return RegisteredUserDAO.banUser(username);
        }
        catch(MongoException e){
            System.out.println("MongoException in RegisteredUserDAO.banUser: " + e.getMessage());
            return false;
        }
        catch(IllegalArgumentException e){
            System.out.println("IllegalArgumentException in RegisteredUserDAO.banUser: " + e.getMessage());
            return false;
        }
        catch(IllegalStateException e){
            System.out.println("IllegalStateException in RegisteredUserDAO.banUser: " + e.getMessage());
            return false;
        }
        catch(Exception e){
            System.out.println("Exception in RegisteredUserDAO.banUser: " + e.getMessage());
            return false;
        }
    }

    public boolean unbanUser(String username) throws MongoException, IllegalArgumentException, IllegalStateException {
        try{
            return RegisteredUserDAO.unbanUser(username);
        }
        catch(MongoException e){
            System.out.println("MongoException in RegisteredUserDAO.unbanUser: " + e.getMessage());
            return false;
        }
        catch(IllegalArgumentException e){
            System.out.println("IllegalArgumentException in RegisteredUserDAO.unbanUser: " + e.getMessage());
            return false;
        }
        catch(IllegalStateException e){
            System.out.println("IllegalStateException in RegisteredUserDAO.unbanUser: " + e.getMessage());
            return false;
        }
        catch(Exception e){
            System.out.println("Exception in RegisteredUserDAO.unbanUser: " + e.getMessage());
            return false;
        }
    }

    // Neo4j
    public boolean followUser(RegisteredUserDTO user, String usernameToFollow) throws IllegalStateException, Neo4jException{
        try{
            return RegisteredUserDAO.followUser(user, usernameToFollow);
        }
        catch(Neo4jException e){
            System.out.println("Neo4jException in RegisteredUserDAO.followUser: " + e.getMessage());
            return false;
        }
        catch(IllegalStateException e){
            System.out.println("IllegalStateException in RegisteredUserDAO.followUser: " + e.getMessage());
            return false;
        }
        catch(Exception e){
            System.out.println("Exception in RegisteredUserDAO.followUser: " + e.getMessage());
            return false;
        }
    }

    public boolean unfollowUser(RegisteredUserDTO user, String usernameToUnfollow) throws IllegalStateException, Neo4jException{
        try{
            return RegisteredUserDAO.unfollowUser(user, usernameToUnfollow);
        }
        catch(Neo4jException e){
            System.out.println("Neo4jException in RegisteredUserDAO.unfollowUser: " + e.getMessage());
            return false;
        }
        catch(IllegalStateException e){
            System.out.println("IllegalStateException in RegisteredUserDAO.unfollowUser: " + e.getMessage());
            return false;
        }
        catch(Exception e){
            System.out.println("Exception in RegisteredUserDAO.unfollowUser: " + e.getMessage());
            return false;
        }
    }

    public List<RegisteredUserDTO> findFriends(RegisteredUserDTO user) throws IllegalStateException, Neo4jException{
        try{
            return RegisteredUserDAO.findFriends(user);
        }
        catch(Neo4jException e){
            System.out.println("Neo4jException in RegisteredUserDAO.findFriends: " + e.getMessage());
            return null;
        }
        catch(IllegalStateException e){
            System.out.println("IllegalStateException in RegisteredUserDAO.findFriends: " + e.getMessage());
            return null;
        }
        catch(Exception e){
            System.out.println("Exception in RegisteredUserDAO.findFriends: " + e.getMessage());
            return null;
        }
    }

    public List<RegisteredUserDTO> findFollowers(RegisteredUserDTO user) throws IllegalStateException, Neo4jException{
        try{
            return RegisteredUserDAO.findFollowers(user);
        }
        catch(Neo4jException e){
            System.out.println("Neo4jException in RegisteredUserDAO.findFollowers: " + e.getMessage());
            return null;
        }
        catch(IllegalStateException e){
            System.out.println("IllegalStateException in RegisteredUserDAO.findFollowers: " + e.getMessage());
            return null;
        }
        catch(Exception e){
            System.out.println("Exception in RegisteredUserDAO.findFollowers: " + e.getMessage());
            return null;
        }
    }

    public List<RegisteredUserDTO> findFollowed(RegisteredUserDTO user) throws IllegalStateException, Neo4jException{
        try{
            return RegisteredUserDAO.findFollowed(user);
        }
        catch(Neo4jException e){
            System.out.println("Neo4jException in RegisteredUserDAO.findFollowed: " + e.getMessage());
            return null;
        }
        catch(IllegalStateException e){
            System.out.println("IllegalStateException in RegisteredUserDAO.findFollowed: " + e.getMessage());
            return null;
        }
        catch(Exception e){
            System.out.println("Exception in RegisteredUserDAO.findFollowed: " + e.getMessage());
            return null;
        }
    }

    public List<RegisteredUserDTO> findUsersToFollowBasedOnUserFriends(RegisteredUserDTO user) throws IllegalStateException, Neo4jException{
        try{
            return RegisteredUserDAO.findUsersToFollowBasedOnUserFriends(user);
        }
        catch(Neo4jException e){
            System.out.println("Neo4jException in RegisteredUserDAO.findUsersToFollowBasedOnUserFriends: " + e.getMessage());
            return null;
        }
        catch(IllegalStateException e){
            System.out.println("IllegalStateException in RegisteredUserDAO.findUsersToFollowBasedOnUserFriends: " + e.getMessage());
            return null;
        }
        catch(Exception e){
            System.out.println("Exception in RegisteredUserDAO.findUsersToFollowBasedOnUserFriends: " + e.getMessage());
            return null;
        }
    }

    public List<RegisteredUserDTO> findMostFollowedUsers() throws IllegalStateException, Neo4jException{
        try{
            return RegisteredUserDAO.findMostFollowedUsers();
        }
        catch(Neo4jException e){
            System.out.println("Neo4jException in RegisteredUserDAO.findMostFollowedUsers: " + e.getMessage());
            return null;
        }
        catch(IllegalStateException e){
            System.out.println("IllegalStateException in RegisteredUserDAO.findMostFollowedUsers: " + e.getMessage());
            return null;
        }
        catch(Exception e){
            System.out.println("Exception in RegisteredUserDAO.findMostFollowedUsers: " + e.getMessage());
            return null;
        }
    }

    public List<RegisteredUserDTO> findUsersByIngredientUsage(IngredientDTO ingredientDTO) throws IllegalStateException, Neo4jException{
        try{
            return RegisteredUserDAO.findUsersByIngredientUsage(ingredientDTO);
        }
        catch(Neo4jException e){
            System.out.println("Neo4jException in RegisteredUserDAO.findUsersByIngredientUsage: " + e.getMessage());
            return null;
        }
        catch(IllegalStateException e){
            System.out.println("IllegalStateException in RegisteredUserDAO.findUsersByIngredientUsage: " + e.getMessage());
            return null;
        }
        catch(Exception e){
            System.out.println("Exception in RegisteredUserDAO.findUsersByIngredientUsage: " + e.getMessage());
            return null;
        }
    }

}
