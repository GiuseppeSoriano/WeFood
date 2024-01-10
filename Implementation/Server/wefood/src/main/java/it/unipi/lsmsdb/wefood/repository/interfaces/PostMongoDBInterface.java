package it.unipi.lsmsdb.wefood.repository.interfaces;

import java.util.List;
import java.util.Map;

import com.mongodb.MongoException;
import it.unipi.lsmsdb.wefood.dto.PostDTO;
import it.unipi.lsmsdb.wefood.model.Post;
import it.unipi.lsmsdb.wefood.model.RegisteredUser;

public interface PostMongoDBInterface {

    String uploadPost(Post post, RegisteredUser user) throws MongoException, IllegalArgumentException, IllegalStateException;
    boolean modifyPost(Post post, PostDTO postDTO) throws MongoException, IllegalArgumentException, IllegalStateException;
    boolean deletePost(PostDTO postDTO) throws MongoException, IllegalArgumentException, IllegalStateException;

    List<PostDTO> browseMostRecentTopRatedPosts(long hours, int limit) throws MongoException, IllegalArgumentException, IllegalStateException;
    List<PostDTO> browseMostRecentTopRatedPostsByIngredients(List<String> ingredientNames, long hours, int limit) throws MongoException, IllegalArgumentException, IllegalStateException;
    List<PostDTO> browseMostRecentPostsByCalories(Double minCalories, Double maxCalories, long hours, int limit) throws MongoException, IllegalArgumentException, IllegalStateException;
    
    Post findPostByPostDTO(PostDTO postDTO) throws MongoException, IllegalArgumentException, IllegalStateException;

    // After having found the recipe on Neo4j, we need to find the post on MongoDB by _id
    Post findPostById(String _id) throws MongoException, IllegalArgumentException, IllegalStateException;
    List<PostDTO> findPostsByRecipeName(String recipeName) throws MongoException, IllegalArgumentException, IllegalStateException;

    // #1 Aggregation
    // Show the ratio of interactions (number of comments / number of Posts
    // and  number of star rankings / number of Posts) and the average of 
    // avgStarRanking distinguishing among posts with and without images 
    // (i.e. no field image inside recipe) (Admin)
    Map<String, Double> interactionsAnalysis() throws MongoException, IllegalArgumentException, IllegalStateException;
    
    // #2 Aggregation
    // Given a User, show the number of comments he/she has done, 
    // the number of star rankings he/she has done and the average 
    // of this star rankings (Admin and RegisteredUser)
    Map<String, Double> userInteractionsAnalysis(String username) throws MongoException, IllegalArgumentException, IllegalStateException;

    // #3 Aggregation
    // After filtering recipes by name, retrieve the average 
    // amount of calories of first 10 recipes ordered by 
    // descending avgStarRanking (Admin and RegisteredUser)
    Double caloriesAnalysis(String recipeName) throws MongoException, IllegalArgumentException, IllegalStateException;

    // Given a user, show the average totalCalories of recipes published by him/her.
    Double averageTotalCaloriesByUser(String username) throws MongoException, IllegalArgumentException, IllegalStateException;
    
}
