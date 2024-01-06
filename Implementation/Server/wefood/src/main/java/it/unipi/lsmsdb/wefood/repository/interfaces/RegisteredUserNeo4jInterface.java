package it.unipi.lsmsdb.wefood.repository.interfaces;

import java.util.List;

import it.unipi.lsmsdb.wefood.dto.IngredientDTO;
import it.unipi.lsmsdb.wefood.dto.RegisteredUserDTO;

public interface RegisteredUserNeo4jInterface {

    boolean createRegisteredUser(RegisteredUserDTO registeredUserDTO);

    boolean createUserUsedIngredient(RegisteredUserDTO registeredUserDTO, List<IngredientDTO> ingredientDTOs);
    boolean deleteUserUsedIngredient(RegisteredUserDTO registeredUserDTO, List<IngredientDTO> ingredientDTOs);

    boolean followUser(RegisteredUserDTO user, String usernameToFollow); 
    boolean unfollowUser(RegisteredUserDTO user, String usernameToUnfollow);

    List<RegisteredUserDTO> findFriends(RegisteredUserDTO user);
    List<RegisteredUserDTO> findFollowers(RegisteredUserDTO user);
    List<RegisteredUserDTO> findFollowed(RegisteredUserDTO user);

    List<RegisteredUserDTO> findUsersToFollowBasedOnUserFriends(RegisteredUserDTO user);
    List<RegisteredUserDTO> findMostFollowedUsers(); //valid also for showing them

    public List<RegisteredUserDTO> findUsersByIngredientUsage(IngredientDTO ingredientDTO);
}
