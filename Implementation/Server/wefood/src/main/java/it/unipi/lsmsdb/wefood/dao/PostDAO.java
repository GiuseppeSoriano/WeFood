package it.unipi.lsmsdb.wefood.dao;

import com.mongodb.MongoException;
import it.unipi.lsmsdb.wefood.model.Post;
import it.unipi.lsmsdb.wefood.dto.PostDTO;
import it.unipi.lsmsdb.wefood.model.RegisteredUser;
import it.unipi.lsmsdb.wefood.repository.interfaces.PostMongoDBInterface;
import it.unipi.lsmsdb.wefood.repository.mongodb.PostMongoDB;

import java.util.List;
import java.util.Map;

public class PostDAO {
    private final static PostMongoDBInterface postMongoDB = new PostMongoDB();

    public static String uploadPost(Post post, RegisteredUser user) throws MongoException, IllegalArgumentException, IllegalStateException {
        return postMongoDB.uploadPost(post, user);
    }
    
    public static boolean modifyPost(Post post, PostDTO postDTO) throws MongoException, IllegalArgumentException, IllegalStateException {
        return postMongoDB.modifyPost(post, postDTO);
    }

    public static boolean deletePost(PostDTO postDTO) throws MongoException, IllegalArgumentException, IllegalStateException {
        return postMongoDB.deletePost(postDTO);
    }

    public static List<PostDTO> browseMostRecentTopRatedPosts(long hours, int limit) throws MongoException, IllegalArgumentException, IllegalStateException {
        return postMongoDB.browseMostRecentTopRatedPosts(hours, limit);
    }

    public static List<PostDTO> browseMostRecentTopRatedPostsByIngredients(List<String> ingredientNames, long hours, int limit) throws MongoException, IllegalArgumentException, IllegalStateException {
        return postMongoDB.browseMostRecentTopRatedPostsByIngredients(ingredientNames, hours, limit);
    }

    public static List<PostDTO> browseMostRecentPostsByCalories(Double minCalories, Double maxCalories, long hours, int limit) throws MongoException, IllegalArgumentException, IllegalStateException {
        return postMongoDB.browseMostRecentPostsByCalories(minCalories, maxCalories, hours, limit);
    }

    public static Post findPostByPostDTO(PostDTO postDTO) throws MongoException, IllegalArgumentException, IllegalStateException {
        return postMongoDB.findPostByPostDTO(postDTO);
    }

    public static Post findPostById(String _id) throws MongoException, IllegalArgumentException, IllegalStateException {
        return postMongoDB.findPostById(_id);
    }

    public static List<PostDTO> findPostsByRecipeName(String recipeName) throws MongoException, IllegalArgumentException, IllegalStateException {
        return postMongoDB.findPostsByRecipeName(recipeName);
    }

    // #1 Aggregation
    // Show the ratio of interactions (number of comments / number of Posts
    // and  number of star rankings / number of Posts) and the average of 
    // avgStarRanking distinguishing among posts with and without images 
    // (i.e. no field image inside recipe) (Admin)
    public static Map<String, Double> interactionsAnalysis() throws MongoException, IllegalArgumentException, IllegalStateException {
        return postMongoDB.interactionsAnalysis();
    }
    
    // #2 Aggregation
    // Given a User, show the number of comments he/she has done, 
    // the number of star rankings he/she has done and the average 
    // of this star rankings (Admin and RegisteredUser)
    public static Map<String, Double> userInteractionsAnalysis(String username) throws MongoException, IllegalArgumentException, IllegalStateException {
        return postMongoDB.userInteractionsAnalysis(username);
    }

    // #3 Aggregation
    // After filtering recipes by name, retrieve the average 
    // amount of calories of first 10 recipes ordered by 
    // descending avgStarRanking (Admin and RegisteredUser)
    public static Double caloriesAnalysis(String recipeName) throws MongoException, IllegalArgumentException, IllegalStateException {
        return postMongoDB.caloriesAnalysis(recipeName);
    }

    // Given a user, show the average totalCalories of recipes published by him/her.
    public static Double averageTotalCaloriesByUser(String username) throws MongoException, IllegalArgumentException, IllegalStateException {
        return postMongoDB.averageTotalCaloriesByUser(username);
    }
}
