package it.unipi.lsmsdb.wefood.repository.interfaces;

import java.util.List;

import it.unipi.lsmsdb.wefood.model.RegisteredUser;

public interface RegisteredUserNeo4jInterface {

    boolean followUser(RegisteredUser user, RegisteredUser userToFollow); //aggiunto RegisteredUser user come base
    boolean unfollowUser(RegisteredUser user, RegisteredUser userToUnfollow);

    List<RegisteredUser> findFriends(RegisteredUser user);
    List<RegisteredUser> findFollowers(RegisteredUser user);
    List<RegisteredUser> findFollowed(RegisteredUser user);

    boolean modifyPersonalProfile(RegisteredUser user);

}
