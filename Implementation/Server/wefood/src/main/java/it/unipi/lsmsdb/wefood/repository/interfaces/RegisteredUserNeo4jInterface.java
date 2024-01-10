package it.unipi.lsmsdb.wefood.repository.interfaces;

import java.util.List;

import it.unipi.lsmsdb.wefood.dto.RegisteredUserDTO;
import org.neo4j.driver.exceptions.Neo4jException;

public interface RegisteredUserNeo4jInterface {

    boolean createUserUsedIngredient(RegisteredUserDTO registeredUserDTO, List<String> ingredientNames) throws IllegalStateException, Neo4jException;
    boolean deleteUserUsedIngredient(RegisteredUserDTO registeredUserDTO, List<String> ingredientNames) throws IllegalStateException, Neo4jException;

    boolean followUser(RegisteredUserDTO user, String usernameToFollow) throws IllegalStateException, Neo4jException;
    boolean unfollowUser(RegisteredUserDTO user, String usernameToUnfollow) throws IllegalStateException, Neo4jException;

    List<RegisteredUserDTO> findFriends(RegisteredUserDTO user) throws IllegalStateException, Neo4jException;
    List<RegisteredUserDTO> findFollowers(RegisteredUserDTO user) throws IllegalStateException, Neo4jException;
    List<RegisteredUserDTO> findFollowed(RegisteredUserDTO user) throws IllegalStateException, Neo4jException;

    List<RegisteredUserDTO> findUsersToFollowBasedOnUserFriends(RegisteredUserDTO user) throws IllegalStateException, Neo4jException;
    List<RegisteredUserDTO> findMostFollowedUsers() throws IllegalStateException, Neo4jException; //valid also for showing them

    List<RegisteredUserDTO> findUsersByIngredientUsage(String ingredientName) throws IllegalStateException, Neo4jException;
}
