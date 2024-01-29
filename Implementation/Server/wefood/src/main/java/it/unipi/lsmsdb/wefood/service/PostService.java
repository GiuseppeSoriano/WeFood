package it.unipi.lsmsdb.wefood.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.neo4j.driver.exceptions.Neo4jException;

import com.mongodb.MongoException;

import it.unipi.lsmsdb.wefood.dao.IngredientDAO;
import it.unipi.lsmsdb.wefood.dao.PostDAO;
import it.unipi.lsmsdb.wefood.dao.RecipeDAO;
import it.unipi.lsmsdb.wefood.dao.RegisteredUserDAO;
import it.unipi.lsmsdb.wefood.dto.PostDTO;
import it.unipi.lsmsdb.wefood.dto.RegisteredUserDTO;
import it.unipi.lsmsdb.wefood.model.Post;
import it.unipi.lsmsdb.wefood.model.RegisteredUser;

public class PostService {
    private String uploadPostMongoDB(Post post, RegisteredUser user){
        String id = "";
        try{
            id = PostDAO.uploadPost(post, user);
            PostDTO postDTO = new PostDTO(id, post.getRecipe().getImage(), post.getRecipe().getName());
            try{
                RegisteredUserDAO.addPost(user, postDTO);
                return id;
            }
            // Other types of exceptions can be handled if necessary: MongoException, IllegalArgumentException, IllegalStateException
            catch(Exception e){
                System.err.println("Exception in RegisteredUserDAO.addPost: " + e.getMessage());
                System.err.println("MongoDB is inconsistent. Redundancy in User collection has to be updated. Post:_id: " + id);
                return null;
            }
        }
        // Other types of exceptions can be handled if necessary: MongoException, IllegalArgumentException, IllegalStateException
        catch(Exception e){
            System.err.println("Exception in PostDAO.uploadPost: " + e.getMessage());
            return null;
        }
    }

    private boolean uploadPostNeo4j(RegisteredUser user, PostDTO postDTO, Post post){
        List<String> ingredients = new ArrayList<String>();
        for (Map.Entry<String, Double> entry : post.getRecipe().getIngredients().entrySet()) {
            ingredients.add(entry.getKey());
        }

        try{
            RecipeDAO.createRecipe(postDTO);
            try{
                RecipeDAO.createRecipeIngredientsRelationship(postDTO, ingredients);
                IngredientDAO.createIngredientIngredientRelationship(ingredients);
                RegisteredUserDAO.createUserUsedIngredient(new RegisteredUserDTO(user.getId(), user.getUsername()), ingredients);
                return true;
            }
            // Other types of exceptions can be handled if necessary: Neo4jException, IllegalStateException
            catch(Exception e){
                System.err.println("Exception in uploadPostNeo4j: " + e.getMessage());
                System.err.println("Neo4j is inconsistent, Relationships of Recipe " + postDTO.getId() + " are inconsistent. Databases are not synchronized");
                return false;
            }
        }
        // Other types of exceptions can be handled if necessary: Neo4jException, IllegalStateException
        catch(Exception e){
            System.err.println("Exception in uploadPostNeo4j: " + e.getMessage());
            System.err.println("Recipe " + postDTO.getId() + " has not been inserted in Neo4j. Databases are not synchronized");
            return false;
        }
    }

    public boolean uploadPost(Post post, RegisteredUser user) {

        // We need to upload the Post on MongoDB
        String id = uploadPostMongoDB(post, user);
        if(id == null)
            // Post not uploaded on MongoDB
            return false;

        // We need to upload the Post on Neo4j (by launching a Thread)
        Thread neo4jThread = new Thread(() -> {
            PostDTO postDTO = new PostDTO(id, post.getRecipe().getImage(), post.getRecipe().getName());
            uploadPostNeo4j(user, postDTO, post);
        });
        neo4jThread.start();

        return true;
    }

    public boolean modifyPost(Post post, PostDTO postDTO) {
        try{
            return PostDAO.modifyPost(post, postDTO);
        }
        // Other types of exceptions can be handled if necessary: MongoException, IllegalArgumentException, IllegalStateException
        catch(Exception e){
            System.err.println("Exception in PostDAO.modifyPost: " + e.getMessage());
            return false;
        }
    }

    private boolean deletePostMongoDB(PostDTO postDTO, Post post){
        try{
            PostDAO.deletePost(postDTO);
            try{
                return RegisteredUserDAO.removePost(post, postDTO);
            }
            // Other types of exceptions can be handled if necessary: MongoException, IllegalArgumentException, IllegalStateException
            catch(Exception e){
                System.err.println("MongoDB is inconsistent, Post " + postDTO.getId() + " has been removed from Post collection but not from User collection");
                System.err.println("Exception in RegisteredUserDAO.removePost: " + e.getMessage());
                return false;
            }
        }
        // Other types of exceptions can be handled if necessary: MongoException, IllegalArgumentException, IllegalStateException
        catch(Exception e){
            System.err.println("Exception in PostDAO.deletePost: " + e.getMessage());
            return false;
        }
    }

    private boolean deletePostNeo4j(PostDTO postDTO, Post post){
        List<String> ingredients = new ArrayList<String>();
        for (Map.Entry<String, Double> entry : post.getRecipe().getIngredients().entrySet()) {
            ingredients.add(entry.getKey());
        }
        
        try{
            RecipeDAO.deleteRecipe(postDTO);
            try{
                RegisteredUserDAO.deleteUserUsedIngredient(post.getUsername(), ingredients);
                return true;
            }
            // Other types of exceptions can be handled if necessary: Neo4jException, IllegalStateException
            catch(Exception e){
                System.out.println("Exception in RegisteredUserDAO.deleteUserUsedIngredient: " + e.getMessage());
                System.err.println("Neo4j is inconsistent, UserUsedIngredient relationships of recipe " + postDTO.getId() + " have not been deleted");
                return false;
            }
        }
        // Other types of exceptions can be handled if necessary: Neo4jException, IllegalStateException
        catch(Exception e){
            System.err.println("Exception in RecipeDAO.deleteRecipe: " + e.getMessage());
            System.err.println("Recipe " + postDTO.getId() + " has not been deleted in Neo4j. Databases are not synchronized");
            return false;
        }
    }

    public boolean deletePost(Post post, PostDTO postDTO) {

        // We need to delete the Post on MongoDB
        if(!deletePostMongoDB(postDTO, post))
            // Post not deleted on MongoDB
            return false;

        // We need to delete the Post on Neo4j (by launching a Thread)
        Thread neo4jThread = new Thread(() -> {
            deletePostNeo4j(postDTO, post);
        });
        neo4jThread.start();

        return true;
    }


    public boolean deleteAllUserPosts(List<PostDTO> postDTOs){

        for(PostDTO postDTO: postDTOs){
            try{
                // We need to delete the Post on MongoDB
                PostDAO.deletePost(postDTO);

                // We need to delete the Post on Neo4j (by launching a Thread)
                Thread neo4jThread = new Thread(() -> {
                    try{
                        RecipeDAO.deleteRecipe(postDTO);
                    }
                    // Other types of exceptions can be handled if necessary: Neo4jException, IllegalStateException
                    catch(Exception e){
                        System.err.println("Exception in RecipeDAO.deleteRecipe: " + e.getMessage());
                        System.err.println("Recipe " + postDTO.getId() + " has not been deleted in Neo4j. Databases are not synchronized");
                    }
                });
                neo4jThread.start();
            }
            // Other types of exceptions can be handled if necessary: MongoException, IllegalArgumentException, IllegalStateException
            catch(Exception e){
                System.err.println("Exception in PostDAO.deletePost: " + e.getMessage());
                return false;
            }
        }

        return true;
    }

    public List<PostDTO> browseMostRecentTopRatedPosts(long hours, int limit) {
        try{
            return PostDAO.browseMostRecentTopRatedPosts(hours, limit);
        }
        // Other types of exceptions can be handled if necessary: MongoException, IllegalArgumentException, IllegalStateException
        catch(Exception e) {
            System.err.println("Exception in PostDAO.browseMostRecentTopRatedPosts: " + e.getMessage());
            return null;
        }
    }

    public List<PostDTO> browseMostRecentTopRatedPostsByIngredients(List<String> ingredientNames, long hours, int limit) {
        try{
            return PostDAO.browseMostRecentTopRatedPostsByIngredients(ingredientNames, hours, limit);
        }
        // Other types of exceptions can be handled if necessary: MongoException, IllegalArgumentException, IllegalStateException
        catch(Exception e) {
            System.err.println("Exception in PostDAO.browseMostRecentTopRatedPostsByIngredients: " + e.getMessage());
            return null;
        }
    }

    public List<PostDTO> browseMostRecentPostsByCalories(Double minCalories, Double maxCalories, long hours, int limit) {
        try{
            return PostDAO.browseMostRecentPostsByCalories(minCalories, maxCalories, hours, limit);
        }
        // Other types of exceptions can be handled if necessary: MongoException, IllegalArgumentException, IllegalStateException
        catch(Exception e) {
            System.err.println("Exception in PostDAO.browseMostRecentPostsByCalories: " + e.getMessage());
            return null;
        }
    }

    public Post findPostByPostDTO(PostDTO postDTO) {
        try{
            return PostDAO.findPostByPostDTO(postDTO);
        }
        // Other types of exceptions can be handled if necessary: MongoException, IllegalArgumentException, IllegalStateException
        catch(Exception e) {
            System.err.println("Exception in PostDAO.findPostByPostDTO: " + e.getMessage());
            return null;
        }
    }

    public Post findPostById(String _id) {
        try{
            return PostDAO.findPostById(_id);
        }
        // Other types of exceptions can be handled if necessary: MongoException, IllegalArgumentException, IllegalStateException
        catch(Exception e) {
            System.err.println("Exception in PostDAO.findPostById: " + e.getMessage());
            return null;
        }
    }

    public List<PostDTO> findPostsByRecipeName(String recipeName) {
        try{
            return PostDAO.findPostsByRecipeName(recipeName);
        }
        // Other types of exceptions can be handled if necessary: MongoException, IllegalArgumentException, IllegalStateException
        catch(Exception e) {
            System.err.println("Exception in PostDAO.findPostsByRecipeName: " + e.getMessage());
            return null;
        }
    }

    // #1 Aggregation
    // Show the ratio of interactions (number of comments / number of Posts
    // and  number of star rankings / number of Posts) and the average of 
    // avgStarRanking distinguishing among posts with and without images 
    // (i.e. no field image inside recipe) (Admin)
    public Map<String, Double> interactionsAnalysis() {
        try{
            return PostDAO.interactionsAnalysis();
        }
        // Other types of exceptions can be handled if necessary: MongoException, IllegalArgumentException, IllegalStateException
        catch(Exception e) {
            System.err.println("Exception in PostDAO.interactionsAnalysis: " + e.getMessage());
            return null;
        }
    }
    
    // #2 Aggregation
    // Given a User, show the number of comments he/she has done, 
    // the number of star rankings he/she has done and the average 
    // of this star rankings (Admin and RegisteredUser)
    public Map<String, Double> userInteractionsAnalysis(String username) {
        try{
            return PostDAO.userInteractionsAnalysis(username);
        }
        // Other types of exceptions can be handled if necessary: MongoException, IllegalArgumentException, IllegalStateException
        catch(Exception e) {
            System.err.println("Exception in PostDAO.userInteractionsAnalysis: " + e.getMessage());
            return null;
        }
    }

    // #3 Aggregation
    // After filtering recipes by name, retrieve the average 
    // amount of calories of first 10 recipes ordered by 
    // descending avgStarRanking (Admin and RegisteredUser)
    public Double caloriesAnalysis(String recipeName) {
        try{
            return PostDAO.caloriesAnalysis(recipeName);
        }
        // Other types of exceptions can be handled if necessary: MongoException, IllegalArgumentException, IllegalStateException
        catch(Exception e) {
            System.err.println("Exception in PostDAO.caloriesAnalysis: " + e.getMessage());
            return null;
        }
    }

    // Given a user, show the average totalCalories of recipes publishished by him/her.
    public Double averageTotalCaloriesByUser(String username) {
        try{
            return PostDAO.averageTotalCaloriesByUser(username);
        }
        // Other types of exceptions can be handled if necessary: MongoException, IllegalArgumentException, IllegalStateException
        catch(Exception e) {
            System.err.println("Exception in PostDAO.averageTotalCaloriesByUser: " + e.getMessage());
            return null;
        }
    }

    public List<PostDTO> findRecipeByIngredients(List<String> ingredientNames) {
        try{
            return RecipeDAO.findRecipeByIngredients(ingredientNames);
        }
        // Other types of exceptions can be handled if necessary: Neo4jException, IllegalStateException
        catch(Exception e) {
            System.err.println("Exception in PostDAO.findRecipeByIngredients: " + e.getMessage());
            return null;
        }
    }

}