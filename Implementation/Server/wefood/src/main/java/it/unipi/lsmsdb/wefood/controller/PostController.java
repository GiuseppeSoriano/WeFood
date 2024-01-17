package it.unipi.lsmsdb.wefood.controller;

import it.unipi.lsmsdb.wefood.apidto.PostByCaloriesRequestDTO;
import it.unipi.lsmsdb.wefood.apidto.PostByIngredientsRequestDTO;
import it.unipi.lsmsdb.wefood.apidto.PostRequestDTO;
import it.unipi.lsmsdb.wefood.apidto.PostTopRatedRequestDTO;
import it.unipi.lsmsdb.wefood.dto.PostDTO;
import it.unipi.lsmsdb.wefood.model.Post;
import it.unipi.lsmsdb.wefood.service.PostService;
import it.unipi.lsmsdb.wefood.service.RecipeImageService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/post")
public class PostController {
    private final PostService postService;
    private final RecipeImageService recipeImageService;

    public PostController(){
        postService = new PostService();
        recipeImageService = new RecipeImageService();
    }


    @PostMapping("/uploadPost")
    public ResponseEntity<Boolean> uploadPost(@RequestBody PostRequestDTO request){
        // IMAGE
        Post post = request.getPost();
        post.getRecipe().setImage(recipeImageService.storePostImage(post));
        return ResponseEntity.ok(postService.uploadPost(post, request.getUser()));
    }

    @PostMapping("/modifyPost")
    public ResponseEntity<Boolean> modifyPost(@RequestBody PostRequestDTO request){
        // Post post, PostDTO postDTO
        Post post = recipeImageService.postConverter(request.getPost());
        PostDTO postDTO = request.getPostDTO();
        postDTO.setImage(post.getRecipe().getImage());
        return ResponseEntity.ok(postService.modifyPost(post, postDTO));
    }

    @PostMapping("/deletePost")
    public ResponseEntity<Boolean> deletePost(@RequestBody PostRequestDTO request){
        Post post = recipeImageService.postConverter(request.getPost());
        PostDTO postDTO = request.getPostDTO();
        postDTO.setImage(post.getRecipe().getImage());
        return ResponseEntity.ok(postService.deletePost(post, postDTO, request.getUser()));
    }

    @PostMapping("/browseMostRecentTopRatedPosts")
    public ResponseEntity<List<PostDTO>> browseMostRecentTopRatedPosts(@RequestBody PostTopRatedRequestDTO request){
        List<PostDTO> postDTOList = recipeImageService.postDTOconverter(postService.browseMostRecentTopRatedPosts(request.getHours(), request.getLimit()));
        return ResponseEntity.ok(postDTOList);
    }

    @PostMapping("/browseMostRecentTopRatedPostsByIngredients")
    public ResponseEntity<List<PostDTO>> browseMostRecentTopRatedPostsByIngredients(@RequestBody PostByIngredientsRequestDTO request){
        List<PostDTO> postDTOList = recipeImageService.postDTOconverter(postService.browseMostRecentTopRatedPostsByIngredients(request.getIngredientNames(), request.getHours(), request.getLimit()));
        return ResponseEntity.ok(postDTOList);
    }

    @PostMapping("/browseMostRecentPostsByCalories")
    public ResponseEntity<List<PostDTO>> browseMostRecentPostsByCalories(@RequestBody PostByCaloriesRequestDTO request){
        List<PostDTO> postDTOList = recipeImageService.postDTOconverter(postService.browseMostRecentPostsByCalories(request.getMinCalories(), request.getMaxCalories(), request.getHours(), request.getLimit()));
        return ResponseEntity.ok(postDTOList);
    }

    @PostMapping("/findPostByPostDTO")
    public ResponseEntity<Post> findPostByPostDTO(@RequestBody PostDTO request){
        Post post = recipeImageService.postConverter(postService.findPostByPostDTO(request));
        return ResponseEntity.ok(post);
    }

    @PostMapping("/findPostById")
    public ResponseEntity<Post> findPostById(@RequestBody String request){
        Post post = recipeImageService.postConverter(postService.findPostById(request));
        return ResponseEntity.ok(post);
    }

    @PostMapping("/findPostsByRecipeName")
    public ResponseEntity<List<PostDTO>> findPostsByRecipeName(@RequestBody String request){
        List<PostDTO> postDTOList = recipeImageService.postDTOconverter(postService.findPostsByRecipeName(request));
        return ResponseEntity.ok(postDTOList);
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
        return ResponseEntity.ok(postService.averageTotalCaloriesByUser(request));
    }

    @PostMapping("/findRecipeByIngredients")
    public ResponseEntity<List<PostDTO>> findRecipeByIngredients(@RequestBody List<String> request){
        List<PostDTO> postDTOList = recipeImageService.postDTOconverter(postService.findRecipeByIngredients(request));
        return ResponseEntity.ok(postDTOList);
    }

}
