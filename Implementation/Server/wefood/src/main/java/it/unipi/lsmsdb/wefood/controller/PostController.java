package it.unipi.lsmsdb.wefood.controller;

import com.mongodb.MongoException;
import it.unipi.lsmsdb.wefood.apidto.PostByCaloriesRequestDTO;
import it.unipi.lsmsdb.wefood.apidto.PostByIngredientsRequestDTO;
import it.unipi.lsmsdb.wefood.apidto.PostRequestDTO;
import it.unipi.lsmsdb.wefood.apidto.PostTopRatedRequestDTO;
import it.unipi.lsmsdb.wefood.dao.PostDAO;
import it.unipi.lsmsdb.wefood.dao.RecipeDAO;
import it.unipi.lsmsdb.wefood.dao.RegisteredUserDAO;
import it.unipi.lsmsdb.wefood.dto.IngredientDTO;
import it.unipi.lsmsdb.wefood.dto.PostDTO;
import it.unipi.lsmsdb.wefood.dto.RecipeDTO;
import it.unipi.lsmsdb.wefood.dto.RegisteredUserDTO;
import it.unipi.lsmsdb.wefood.model.Post;
import it.unipi.lsmsdb.wefood.model.RegisteredUser;
import it.unipi.lsmsdb.wefood.service.PostService;
import org.neo4j.driver.exceptions.Neo4jException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/post")
public class PostController {
    private final PostService postService;

    public PostController(){
        postService = new PostService();
        recipeImageService = new RecipeImageService();
    }


    @PostMapping("/uploadPost")
    public ResponseEntity<Boolean> uploadPost(@RequestBody PostRequestDTO request){
        // IMAGE
        // Post post, RegisteredUser user
        return ResponseEntity.ok(postService.uploadPost(request.getPost(), request.getUser()));
    }

    @PostMapping("/modifyPost")
    public ResponseEntity<Boolean> modifyPost(@RequestBody PostRequestDTO request){
        // Post post, PostDTO postDTO
        return ResponseEntity.ok(postService.modifyPost(request.getPost(), request.getPostDTO()));
    }

    @PostMapping("/deletePost")
    public ResponseEntity<Boolean> deletePost(@RequestBody PostRequestDTO request){
        return ResponseEntity.ok(postService.deletePost(request.getPost(), request.getPostDTO(), request.getUser()));
    }

    @PostMapping("/browseMostRecentTopRatedPosts")
    public ResponseEntity<List<PostDTO>> browseMostRecentTopRatedPosts(@RequestBody PostTopRatedRequestDTO request){
        // IMAGE
        return ResponseEntity.ok(postService.browseMostRecentTopRatedPosts(request.getHours(), request.getLimit()));
    }

    @PostMapping("/browseMostRecentTopRatedPostsByIngredients")
    public ResponseEntity<List<PostDTO>> browseMostRecentTopRatedPostsByIngredients(@RequestBody PostByIngredientsRequestDTO request){
        // IMAGE
        return ResponseEntity.ok(postService.browseMostRecentTopRatedPostsByIngredients(request.getIngredientNames(), request.getHours(), request.getLimit()));
    }

    @PostMapping("/browseMostRecentPostsByCalories")
    public ResponseEntity<List<PostDTO>> browseMostRecentPostsByCalories(@RequestBody PostByCaloriesRequestDTO request){
        // IMAGE
        return ResponseEntity.ok(postService.browseMostRecentPostsByCalories(request.getMinCalories(), request.getMaxCalories(), request.getHours(), request.getLimit()));
    }

    @PostMapping("/findPostByPostDTO")
    public ResponseEntity<Post> findPostByPostDTO(@RequestBody PostDTO request){
        // IMAGE
        Post post = recipeImageService.postDTOconverter(postService.findPostByPostDTO(request));
        return ResponseEntity.ok(postService.findPostByPostDTO(request));
    }

    @PostMapping("/findPostById")
    public ResponseEntity<Post> findPostById(@RequestBody String request){
        // IMAGE
        return ResponseEntity.ok(postService.findPostById(request));
    }

    @PostMapping("/findPostsByRecipeName")
    public ResponseEntity<List<PostDTO>> findPostsByRecipeName(@RequestBody String request){
        // IMAGE
        return ResponseEntity.ok(postService.findPostsByRecipeName(request));
    }

    @PostMapping("/interactionsAnalysis")
    public ResponseEntity<Map<String, Double>> interactionsAnalysis(){
        return ResponseEntity.ok(postService.interactionsAnalysis());
    }

    @PostMapping("/userInteractionsAnalysis")
    public ResponseEntity<Map<String, Double>> userInteractionsAnalysis(@RequestBody String request){
        return ResponseEntity.ok(postService.userInteractionsAnalysis(request));
    }

    @PostMapping("/caloriesAnalysis")
    public ResponseEntity<Double> caloriesAnalysis(@RequestBody String request){
        return ResponseEntity.ok(postService.caloriesAnalysis(request));
    }

    @PostMapping("/averageTotalCaloriesByUser")
    public ResponseEntity<Double> averageTotalCaloriesByUser(@RequestBody String request){
        return ResponseEntity.ok(postService.caloriesAnalysis(request));
    }

    @PostMapping("/findRecipeByIngredients")
    public ResponseEntity<List<RecipeDTO>> findRecipeByIngredients(@RequestBody List<String> request){
        return ResponseEntity.ok(postService.findRecipeByIngredients(request));
    }
}
