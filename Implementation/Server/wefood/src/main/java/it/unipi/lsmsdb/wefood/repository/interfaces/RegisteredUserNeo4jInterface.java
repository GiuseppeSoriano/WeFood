package it.unipi.lsmsdb.wefood.repository.interfaces;

import java.util.List;

import it.unipi.lsmsdb.wefood.dto.IngredientDTO;
import it.unipi.lsmsdb.wefood.dto.RegisteredUserDTO;
import it.unipi.lsmsdb.wefood.model.RegisteredUser;

public interface RegisteredUserNeo4jInterface {

    boolean followUser(RegisteredUser user, String usernameToFollow); 
    boolean unfollowUser(RegisteredUser user, String usernameToUnfollow);

    List<RegisteredUserDTO> findFriends(RegisteredUser user);
    List<RegisteredUserDTO> findFollowers(RegisteredUser user);
    List<RegisteredUserDTO> findFollowed(RegisteredUser user);

    List<RegisteredUserDTO> findUsersbyIngredientsUsed(List<IngredientDTO> ingredientDTOs)

}
