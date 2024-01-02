package it.unipi.lsmsdb.wefood.dao;

import java.util.List;

import it.unipi.lsmsdb.wefood.model.RegisteredUser;
import it.unipi.lsmsdb.wefood.repository.mongodb.RegisteredUserMongoDB;
import it.unipi.lsmsdb.wefood.repository.neo4j.RegisteredUserNeo4j;

public class RegisteredUserDAO {
    RegisteredUserNeo4j registeredUserNeo4j;
    RegisteredUserMongoDB registeredUserMongoDB;

    public RegisteredUserDAO() {
        registeredUserNeo4j = new RegisteredUserNeo4j();
        registeredUserMongoDB = new RegisteredUserMongoDB();
    }

    // MongoDB
    public RegisteredUser login(String username, String password) {
        return registeredUserMongoDB.login(username, password);
    };

    public RegisteredUser findRegisteredUserByUsername(String username) {
        return registeredUserMongoDB.findRegisteredUserByUsername(username);
    };

    public boolean deleteUser(RegisteredUser user) {
        return registeredUserMongoDB.deleteUser(user);
    };

    // Neo4j
    public boolean followUser(RegisteredUser user, RegisteredUser userToFollow) {
        return registeredUserNeo4j.followUser(user, userToFollow);
    };

    public boolean unfollowUser(RegisteredUser user, RegisteredUser userToUnfollow) {
        return registeredUserNeo4j.unfollowUser(user, userToUnfollow);
    };

    public List<RegisteredUser> findFriends(RegisteredUser user) {
        return registeredUserNeo4j.findFriends(user);
    };

    public List<RegisteredUser> findFollowers(RegisteredUser user) {
        return registeredUserNeo4j.findFollowers(user);
    };

    // MongoDB and Neo4j
    public boolean modifyPersonalProfile(RegisteredUser user) {
        boolean result = registeredUserMongoDB.modifyPersonalProfile(user) && registeredUserNeo4j.modifyPersonalProfile(user);
        return result;
    };
}
