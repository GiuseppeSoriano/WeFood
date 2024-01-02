package it.unipi.lsmsdb.wefood.repository.neo4j;

import java.util.LinkedList;
import java.util.List;
import org.neo4j.driver.Record;

import it.unipi.lsmsdb.wefood.model.RegisteredUser;
import it.unipi.lsmsdb.wefood.repository.base.BaseNeo4j;
import it.unipi.lsmsdb.wefood.repository.interfaces.RegisteredUserNeo4jInterface;

public class RegisteredUserNeo4j implements RegisteredUserNeo4jInterface {
    public boolean followUser(RegisteredUser user, RegisteredUser userToFollow) {
        String query = "MATCH (u1:User {username: "+user.getUsername()+"}), (u2:User {username: "+userToFollow.getUsername()+"})" +
                        "CREATE (u1)-[:FOLLOWS]->(u2)";
        //BaseNeo4j.getNeo4jDriver();
        List<Record> results = BaseNeo4j.executeQuery(query);
        if (results == null) {
            return false;
        } else {
            return true;
        }
    };
    public boolean unfollowUser(RegisteredUser user, RegisteredUser userToUnfollow) {
        String query = "MATCH (u1:User {username: "+user.getUsername()+"})-[r:FOLLOWS]->(u2:User {username: "+userToUnfollow.getUsername()+"})" +
                        "DELETE r";
        //BaseNeo4j.getNeo4jDriver();
        List<Record> results = BaseNeo4j.executeQuery(query);
        if (results == null) {
            return false;
        } else {
            return true;
        }
    };

    public List<RegisteredUser> findFriends(RegisteredUser user) {
        String query = "MATCH (u1:User {username: "+user.getUsername()+"})-[:FOLLOWS]->(u2:User)-[:FOLLOWS]->(u1)" +
                        "RETURN u2";
        //BaseNeo4j.getNeo4jDriver();
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
    public List<RegisteredUser> findFollowers(RegisteredUser user) {
        String query = "MATCH (u1:User)-[:FOLLOWS]->(u2:User {username: "+user.getUsername()+"})" +
                        "RETURN u1";
        //BaseNeo4j.getNeo4jDriver();
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
    public List<RegisteredUser> findFollowed(RegisteredUser user) {
        String query = "MATCH (u1:User {username: "+user.getUsername()+"})-[:FOLLOWS]->(u2:User)" +
                        "RERURN u2";
        //BaseNeo4j.getNeo4jDriver();
        List<Record> results = BaseNeo4j.executeQuery(query);
        LinkedList<Record> resultsList = new LinkedList<Record>(results);
        List<RegisteredUser> followed = new LinkedList<RegisteredUser>();
        while (resultsList.iterator().hasNext()) {
            Record result = resultsList.iterator().next();
            RegisteredUser followedUser = new RegisteredUser(result.get("username").asString());
            followed.add(followedUser);
        }
        return followed;
    };

    public boolean modifyPersonalProfile(RegisteredUser user) { //Tra l'altro hanno lo stesso nome
        //come garantire la consistenza?                 //Dovrebbero per forza stare in due classi diverse
        return false;                                    //A questo punto, che senso ha questa classe?
    };
}
