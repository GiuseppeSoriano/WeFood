package it.unipi.lsmsdb.wefood.repository.neo4j;

import java.util.LinkedList;
import java.util.List;
import org.neo4j.driver.Record;

import it.unipi.lsmsdb.wefood.model.RegisteredUser;
import it.unipi.lsmsdb.wefood.dto.RegisteredUserDTO;
import it.unipi.lsmsdb.wefood.repository.base.BaseNeo4j;
import it.unipi.lsmsdb.wefood.repository.interfaces.RegisteredUserNeo4jInterface;

    //correggere le etichette con le nuove info
public class RegisteredUserNeo4j implements RegisteredUserNeo4jInterface {

    //aggiungere un metodo create user all'atto di login per la consistenza

    public boolean followUser(RegisteredUser user, RegisteredUser userToFollow) {
        String query = "MATCH (u1:User {username: "+user.getUsername()+"}), (u2:User {username: "+userToFollow.getUsername()+"})" +
                        "CREATE (u1)-[:FOLLOWS]->(u2)";
        List<Record> results = BaseNeo4j.executeQuery(query);
        if (results.isEmpty()) {
            return false;
        } else {
            return true;
        }
    };
    public boolean unfollowUser(RegisteredUser user, RegisteredUser userToUnfollow) {
        String query = "MATCH (u1:User {username: "+user.getUsername()+"})-[r:FOLLOWS]->(u2:User {username: "+userToUnfollow.getUsername()+"})" +
                        "DELETE r";
        List<Record> results = BaseNeo4j.executeQuery(query);
        if (results.isEmpty()) {
            return false;
        } else {
            return true;
        }
    };

    public List<RegisteredUserDTO> findFriends(RegisteredUser user) {
        String query = "MATCH (u1:User {username: "+user.getUsername()+"})-[:FOLLOWS]->(u2:User)-[:FOLLOWS]->(u1)" +
                        "RETURN u2";
        List<Record> results = BaseNeo4j.executeQuery(query);
        LinkedList<Record> resultsList = new LinkedList<Record>(results);
        List<RegisteredUser> friends = new LinkedList<RegisteredUser>();
        while (resultsList.iterator().hasNext()) {
            Record result = resultsList.iterator().next();
            RegisteredUser friend = new RegisteredUser(result.get("username").asString());
            friends.add(friend);
        }
        return friends;
    };
    public List<RegisteredUserDTO> findFollowers(RegisteredUser user) {
        String query = "MATCH (u1:User)-[:FOLLOWS]->(u2:User {username: "+user.getUsername()+"})" +
                        "RETURN u1";
        List<Record> results = BaseNeo4j.executeQuery(query);
        LinkedList<Record> resultsList = new LinkedList<Record>(results);
        List<RegisteredUser> followers = new LinkedList<RegisteredUser>();
        while (resultsList.iterator().hasNext()) {
            Record result = resultsList.iterator().next();
            RegisteredUser follower = new RegisteredUser(result.get("username").asString());
            followers.add(follower);
        }
        return followers;
    };
    public List<RegisteredUserDTO> findFollowed(RegisteredUser user) {
        String query = "MATCH (u1:User {username: "+user.getUsername()+"})-[:FOLLOWS]->(u2:User)" +
                        "RERURN u2";
        List<Record> results = BaseNeo4j.executeQuery(query);
        LinkedList<Record> resultsList = new LinkedList<Record>(results);
        /*
            Quello che viene dopo va corretto in tutti
            rendendo coerente il tipo della lista e inserendo
            nel costruttore le info corrette 
        */
        List<RegisteredUserDTO> followed = new LinkedList<RegisteredUserDTO>();
        while (resultsList.iterator().hasNext()) {
            Record result = resultsList.iterator().next();
            /*
                Qua bisogna mettere nel costruttore del DTO le info corrette
                RegisteredUserDTO followedUser = new RegisteredUserDTO(result.get("username").asString()); 
            */
            followed.add(followedUser);
        }
        return followed;
    };

    public boolean modifyPersonalProfile(RegisteredUser user) { 
        //come garantire la consistenza?                 
        return false;                                   
    };
}
