package it.unipi.lsmsdb.wefood.repository.neo4j;

import java.util.ArrayList;
import java.util.List;
import org.neo4j.driver.Record;
import org.neo4j.driver.exceptions.Neo4jException;

import it.unipi.lsmsdb.wefood.dto.RegisteredUserDTO;
import it.unipi.lsmsdb.wefood.repository.base.BaseNeo4j;
import it.unipi.lsmsdb.wefood.repository.interfaces.RegisteredUserNeo4jInterface;

    //correggere le etichette con le nuove info
public class RegisteredUserNeo4j implements RegisteredUserNeo4jInterface {

    public boolean deleteRegisteredUser(RegisteredUserDTO registeredUserDTO) throws IllegalStateException, Neo4jException {
        String query = "MATCH (u:User {username: '" + registeredUserDTO.getUsername() + "'})\r\n" + //
                       "DETACH DELETE u";
        List<Record> results = BaseNeo4j.executeQuery(query);
        // If it does not throw an exception, it means that the query has been executed successfully
        return true;
    }

    public boolean createUserUsedIngredient(RegisteredUserDTO registeredUserDTO, List<String> ingredientNames) throws IllegalStateException, Neo4jException {
        for(String ingredientName: ingredientNames){
            String query = "MATCH (u:User {username: '" + registeredUserDTO.getUsername() + "'}), (i:Ingredient {name: '" + ingredientName + "'})\r\n" + //
                           "MERGE (u)-[r:USED]->(i) ON CREATE SET r.times = 1 ON MATCH SET r.times = r.times + 1";
            List<Record> results = BaseNeo4j.executeQuery(query);
        }
        return true;
    }

    public boolean deleteUserUsedIngredient(String username, List<String> ingredientNames) throws IllegalStateException, Neo4jException {
        for(String ingredientName: ingredientNames){
            String query = "MATCH (u:User {username: \"" + username + "\"})-[r:USED]->(i2:Ingredient {name: \"" + ingredientName + "\"})\n" +
                            "SET r.times = r.times - 1\n" +
                            "WITH r\n" +
                            "WHERE r.times = 0\n" +
                            "DELETE r\n";
            List<Record> results = BaseNeo4j.executeQuery(query);
        }
        return true;
    }

    public boolean followUser(RegisteredUserDTO user, String usernameToFollow) throws IllegalStateException, Neo4jException {
        String query = "MATCH (u1:User {username: '" + user.getUsername() + "'}), (u2:User {username: '" + usernameToFollow + "'})" +
                        "MERGE (u1)-[:FOLLOWS]->(u2)";
        List<Record> results = BaseNeo4j.executeQuery(query);
        // If it does not throw an exception, it means that the query has been executed successfully
        return true;       
    }

    public boolean unfollowUser(RegisteredUserDTO user, String usernameToUnfollow) throws IllegalStateException, Neo4jException {
        String query = "MATCH (u1:User {username: '" + user.getUsername() + "'})-[r:FOLLOWS]->(u2:User {username: '" + usernameToUnfollow + "'})" +
                        "DELETE r";
        List<Record> results = BaseNeo4j.executeQuery(query);
        // If it does not throw an exception, it means that the query has been executed successfully
        return true; 
    }

    public List<RegisteredUserDTO> findFriends(RegisteredUserDTO user) throws IllegalStateException, Neo4jException {
        String query = "MATCH (u1:User {username: '" + user.getUsername() + "'})-[:FOLLOWS]->(u2:User)-[:FOLLOWS]->(u1)\r\n" + //
                       "RETURN u2";
        List<Record> results = BaseNeo4j.executeQuery(query);

        List<RegisteredUserDTO> friends = new ArrayList<RegisteredUserDTO>();
        for(Record result: results){
            RegisteredUserDTO friend = new RegisteredUserDTO(result.get("u2").get("_id").asString(),
                                                             result.get("u2").get("username").asString());
            friends.add(friend);
        }
        return friends;
    }
    
    public List<RegisteredUserDTO> findFollowers(RegisteredUserDTO user) throws IllegalStateException, Neo4jException {
        String query = "MATCH (u1:User)-[:FOLLOWS]->(u2:User {username: '" + user.getUsername() + "'})\r\n" + //
                       "RETURN u1";
        List<Record> results = BaseNeo4j.executeQuery(query);
        List<RegisteredUserDTO> followers = new ArrayList<RegisteredUserDTO>();
        for(Record result: results){
            RegisteredUserDTO follower = new RegisteredUserDTO(result.get("u1").get("_id").asString(),
                                                               result.get("u1").get("username").asString());
            followers.add(follower);
        }
        return followers;
    }
    
    public List<RegisteredUserDTO> findFollowed(RegisteredUserDTO user) throws IllegalStateException, Neo4jException {
        String query = "MATCH (u1:User {username: '" + user.getUsername() + "'})-[:FOLLOWS]->(u2:User)\r\n" + //
                       "RETURN u2";
        List<Record> results = BaseNeo4j.executeQuery(query);
        List<RegisteredUserDTO> followedUsers = new ArrayList<RegisteredUserDTO>();
        for(Record result: results){
            RegisteredUserDTO followedUser = new RegisteredUserDTO(result.get("u2").get("_id").asString(),
                                                                   result.get("u2").get("username").asString());
            followedUsers.add(followedUser);
        }
        return followedUsers;
    }

    public List<RegisteredUserDTO> findUsersToFollowBasedOnUserFriends(RegisteredUserDTO user) throws IllegalStateException, Neo4jException {
        String query = "MATCH (u1:User {username: '" + user.getUsername() + "'})-[:FOLLOWS]->(u2:User)-[:FOLLOWS]->(u3:User)\r\n" + //
                       "WHERE (u2)-[:FOLLOWS]->(u1)\r\n" + //
                       "AND NOT (u1)-[:FOLLOWS]->(u3)\r\n" + //
                       "RETURN u3\r\n" +
                       "LIMIT 15";
        List<Record> results = BaseNeo4j.executeQuery(query);
        List<RegisteredUserDTO> suggestedUsers = new ArrayList<RegisteredUserDTO>();
        for(Record result: results){
            RegisteredUserDTO suggestedUser = new RegisteredUserDTO(result.get("u3").get("_id").asString(),
                                                                   result.get("u3").get("username").asString());
            suggestedUsers.add(suggestedUser);
        }
        return suggestedUsers;
    }

    public List<RegisteredUserDTO> findMostFollowedUsers() throws IllegalStateException, Neo4jException {
        String query = "MATCH (u1:User)-[:FOLLOWS]->(u2:User)\r\n" + //
                       "RETURN u2, COUNT(u1) AS followers\r\n" + //
                       "ORDER BY followers DESC\r\n" + //
                       "LIMIT 5";
        List<Record> results = BaseNeo4j.executeQuery(query);
        List<RegisteredUserDTO> suggestedUsers = new ArrayList<RegisteredUserDTO>();
        for(Record result: results){
            RegisteredUserDTO suggestedUser = new RegisteredUserDTO(result.get("u2").get("_id").asString(),
                                                                   result.get("u2").get("username").asString());
            suggestedUsers.add(suggestedUser);
        }
        return suggestedUsers;
    }

    public List<RegisteredUserDTO> findUsersByIngredientUsage(String ingredientName) throws IllegalStateException, Neo4jException {
        String query = "MATCH (u:User)-[r:USED]->(i:Ingredient {name: '" + ingredientName + "'})\r\n" + //
                       "RETURN u, i, r.times AS times\r\n" + //
                       "ORDER BY times DESC\r\n" + //
                       "LIMIT 15";
        List<Record> results = BaseNeo4j.executeQuery(query);
        List<RegisteredUserDTO> suggestedUsers = new ArrayList<RegisteredUserDTO>();
        for(Record result: results){
            RegisteredUserDTO suggestedUser = new RegisteredUserDTO(result.get("u").get("_id").asString(),
                                                                   result.get("u").get("username").asString());
            suggestedUsers.add(suggestedUser);
        }
        return suggestedUsers;
    }


}
