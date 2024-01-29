package it.unipi.lsmsdb.wefood.controller;

import it.unipi.lsmsdb.wefood.apidto.LoginRequestDTO;
import it.unipi.lsmsdb.wefood.apidto.RegisteredUserRequestDTO;
import it.unipi.lsmsdb.wefood.dto.RegisteredUserDTO;
import it.unipi.lsmsdb.wefood.dto.RegisteredUserPageDTO;
import it.unipi.lsmsdb.wefood.model.RegisteredUser;
import it.unipi.lsmsdb.wefood.service.RecipeImageService;
import it.unipi.lsmsdb.wefood.service.RegisteredUserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/registereduser")
public class RegisteredUserController {
    private final RegisteredUserService registeredUserService;
    private final RecipeImageService recipeImageService;
    public RegisteredUserController(){
        this.registeredUserService = new RegisteredUserService();
        this.recipeImageService = new RecipeImageService();
    }

    @PostMapping("/login")
    public ResponseEntity<RegisteredUser> login(@RequestBody LoginRequestDTO request){
        RegisteredUser user = registeredUserService.login(request.getUsername(), request.getPassword());
        return user != null ? ResponseEntity.ok(user) : ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

    @PostMapping("/findRegisteredUserPageByUsername")
    public ResponseEntity<RegisteredUserPageDTO> findRegisteredUserPageByUsername(@RequestBody String request){
        RegisteredUserPageDTO result = registeredUserService.findRegisteredUserPageByUsername(request);
        if (result == null) {
            // Return a 404 error if no user is found
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        if (result.getPosts().size() >= 60)
            result.setPosts(result.getPosts().subList(0, 60));
        result.setPosts(recipeImageService.postDTOconverter(result.getPosts()));
        return ResponseEntity.ok(result);
    }

    @PostMapping("/modifyPersonalInformation")
    public ResponseEntity<Boolean> modifyPersonalInformation(@RequestBody RegisteredUser request){
        return ResponseEntity.ok(registeredUserService.modifyPersonalInformation(request));
    }

    @PostMapping("/deleteUser")
    public ResponseEntity<Boolean> deleteUser(@RequestBody RegisteredUserPageDTO request){
        return ResponseEntity.ok(registeredUserService.deleteUser(request));
    }

    @PostMapping("/followUser")
    public ResponseEntity<Boolean> followUser(@RequestBody RegisteredUserRequestDTO request){
        return ResponseEntity.ok(registeredUserService.followUser(request.getRegisteredUserDTO(), request.getUsernameToFollow()));
    }

    @PostMapping("/unfollowUser")
    public ResponseEntity<Boolean> unfollowUser(@RequestBody RegisteredUserRequestDTO request){
        return ResponseEntity.ok(registeredUserService.unfollowUser(request.getRegisteredUserDTO(), request.getUsernameToFollow()));
    }

    @PostMapping("/findFriends")
    public ResponseEntity<List<RegisteredUserDTO>> findFriends(@RequestBody RegisteredUserDTO request){
        return ResponseEntity.ok(registeredUserService.findFriends(request));
    }

    @PostMapping("/findFollowers")
    public ResponseEntity<List<RegisteredUserDTO>> findFollowers(@RequestBody RegisteredUserDTO request){
        return ResponseEntity.ok(registeredUserService.findFollowers(request));
    }

    @PostMapping("/findFollowed")
    public ResponseEntity<List<RegisteredUserDTO>> findFollowed(@RequestBody RegisteredUserDTO request){
        return ResponseEntity.ok(registeredUserService.findFollowed(request));
    }

    @PostMapping("/findUsersToFollowBasedOnUserFriends")
    public ResponseEntity<List<RegisteredUserDTO>> findUsersToFollowBasedOnUserFriends(@RequestBody RegisteredUserDTO request){
        return ResponseEntity.ok(registeredUserService.findUsersToFollowBasedOnUserFriends(request));
    }

    @PostMapping("/findMostFollowedUsers")
    public ResponseEntity<List<RegisteredUserDTO>> findMostFollowedUsers(){
        return ResponseEntity.ok(registeredUserService.findMostFollowedUsers());
    }

    @PostMapping("/findUsersByIngredientUsage")
    public ResponseEntity<List<RegisteredUserDTO>> findUsersByIngredientUsage(@RequestBody String request){
        return ResponseEntity.ok(registeredUserService.findUsersByIngredientUsage(request));
    }
}
