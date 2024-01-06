package it.unipi.lsmsdb.wefood.dao;

import it.unipi.lsmsdb.wefood.model.Post;
import it.unipi.lsmsdb.wefood.dto.PostDTO;
import it.unipi.lsmsdb.wefood.dto.IngredientDTO;
import it.unipi.lsmsdb.wefood.model.RegisteredUser;
import it.unipi.lsmsdb.wefood.repository.mongodb.PostMongoDB;

import java.util.List;
import java.util.Map;

public class PostDAO {
    PostMongoDB postMongoDB;

    public PostDAO() {
        this.postMongoDB = new PostMongoDB();
    }

    public boolean uploadPost(Post post, RegisteredUser user) {
        return this.postMongoDB.uploadPost(post, user);
    }
    
    public boolean modifyPost(Post post, PostDTO postDTO) {
        return this.postMongoDB.modifyPost(post, postDTO);
    }

    public boolean deletePost(PostDTO postDTO) {
        return this.postMongoDB.deletePost(postDTO);
    }

    public List<PostDTO> browseMostRecentTopRatedPosts(long hours, int limit) {
        return this.postMongoDB.browseMostRecentTopRatedPosts(hours, limit);
    }

    public List<PostDTO> browseMostRecentTopRatedPostsByIngredients(List<IngredientDTO> ingredientDTOs, long hours, int limit) {
        return this.postMongoDB.browseMostRecentTopRatedPostsByIngredients(ingredientDTOs, hours, limit);
    }

    public List<PostDTO> browseMostRecentPostsByCalories(Double minCalories, Double maxCalories, long hours, int limit) {
        return this.postMongoDB.browseMostRecentPostsByCalories(minCalories, maxCalories, hours, limit);
    }

    public Post findPostByPostDTO(PostDTO postDTO) {
        return this.postMongoDB.findPostByPostDTO(postDTO);
    }

    Post findPostById(String _id);
    List<PostDTO> findPostsByRecipeName(String recipeName);

    // #1 Aggregation
    // Show the ratio of interactions (number of comments / number of Posts
    // and  number of star rankings / number of Posts) and the average of 
    // avgStarRanking distinguishing among posts with and without images 
    // (i.e. no field image inside recipe) (Admin)
    Map<String, Double> interactionsAnalysis();
    
    // #2 Aggregation
    // Given a User, show the number of comments he/she has done, 
    // the number of star rankings he/she has done and the average 
    // of this star rankings (Admin and RegisteredUser)
    Map<String, Double> userInteractionsAnalysis(String username);

    // #3 Aggregation
    // After filtering recipes by name, retrieve the average 
    // amount of calories of first 10 recipes ordered by 
    // descending avgStarRanking (Admin and RegisteredUser)
    Double caloriesAnalysis(String recipeName);

    // Given a user, show the average totalCalories of recipes publishished by him/her.
    Double averageTotalCaloriesByUser(String username);



}
