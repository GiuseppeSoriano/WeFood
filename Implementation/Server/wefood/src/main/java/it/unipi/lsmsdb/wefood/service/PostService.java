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
import it.unipi.lsmsdb.wefood.dto.IngredientDTO;
import it.unipi.lsmsdb.wefood.dto.PostDTO;
import it.unipi.lsmsdb.wefood.dto.RecipeDTO;
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
            catch(MongoException e){
                PostDAO.deletePost(postDTO);
                System.out.println("MongoException in IngredientService.createIngredient: " + e.getMessage());
                return null;
            }
            catch(IllegalArgumentException e){
                PostDAO.deletePost(postDTO);
                System.out.println("IllegalArgumentException in IngredientService.createIngredient: " + e.getMessage());
                return null;
            }
            catch(IllegalStateException e){
                PostDAO.deletePost(postDTO);
                System.out.println("IllegalStateException in IngredientService.createIngredient: " + e.getMessage());
                return null;
            }
            catch(Exception e){
                PostDAO.deletePost(postDTO);
                System.out.println("Exception in IngredientService.createIngredient: " + e.getMessage());
                return null;
            }
        }
        catch(MongoException e){
            if(id != "")
                System.err.println("MongoDB is inconsistent, Post " + id + " has not been inserted in User posts");
            System.out.println("MongoException in IngredientService.createIngredient: " + e.getMessage());
            return null;
        }
        catch(IllegalArgumentException e){
            if(id != "")
                System.err.println("MongoDB is inconsistent, Post " + id + " has not been inserted in User posts");
            System.out.println("IllegalArgumentException in IngredientService.createIngredient: " + e.getMessage());
            return null;
        }
        catch(IllegalStateException e){
            if(id != "")
                System.err.println("MongoDB is inconsistent, Post " + id + " has not been inserted in User posts");
            System.out.println("IllegalStateException in IngredientService.createIngredient: " + e.getMessage());
            return null;
        }
        catch(Exception e){
            if(id != "")
                System.err.println("MongoDB is inconsistent, Post " + id + " has not been inserted in User posts");
            System.out.println("Exception in IngredientService.createIngredient: " + e.getMessage());
            return null;
        }
    }

    private boolean deletePostMongoDB(PostDTO postDTO, RegisteredUser user){
        try{
            RegisteredUserDAO.removePost(user, postDTO);
            try{
                PostDAO.deletePost(postDTO);
                return true;
            }
            catch(MongoException e){
                System.err.println("MongoDB is inconsistent, Post " + postDTO.getId() + " has been removed from User posts but not from Posts");
                System.out.println("MongoException in IngredientService.createIngredient: " + e.getMessage());
                return false;
            }
            catch(IllegalArgumentException e){
                System.err.println("MongoDB is inconsistent, Post " + postDTO.getId() + " has been removed from User posts but not from Posts");
                System.out.println("IllegalArgumentException in IngredientService.createIngredient: " + e.getMessage());
                return false;
            }
            catch(IllegalStateException e){
                System.err.println("MongoDB is inconsistent, Post " + postDTO.getId() + " has been removed from User posts but not from Posts");
                System.out.println("IllegalStateException in IngredientService.createIngredient: " + e.getMessage());
                return false;
            }
            catch(Exception e){
                System.err.println("MongoDB is inconsistent, Post " + postDTO.getId() + " has been removed from User posts but not from Posts");
                System.out.println("Exception in IngredientService.createIngredient: " + e.getMessage());
                return false;
            }
        }
        catch(MongoException e){
            System.out.println("MongoException in IngredientService.createIngredient: " + e.getMessage());
            return false;
        }
        catch(IllegalArgumentException e){
            System.out.println("IllegalArgumentException in IngredientService.createIngredient: " + e.getMessage());
            return false;
        }
        catch(IllegalStateException e){
            System.out.println("IllegalStateException in IngredientService.createIngredient: " + e.getMessage());
            return false;
        }
        catch(Exception e){
            System.out.println("Exception in IngredientService.createIngredient: " + e.getMessage());
            return false;
        }
    }

    private boolean uploadPostNeo4j(RegisteredUser user, PostDTO postDTO, Post post){
        RecipeDTO recipeDTO = new RecipeDTO(postDTO.getRecipeName(), postDTO.getImage());
        List<String> ingredients = new ArrayList<String>();
        for (Map.Entry<String, Double> entry : post.getRecipe().getIngredients().entrySet()) {
            ingredients.add(entry.getKey());
        }
        boolean created = false;
        try{
            created = RecipeDAO.createRecipe(recipeDTO);
            try{
                RecipeDAO.createRecipeIngredientsRelationship(recipeDTO, ingredients);
                // This method call is at the same level of the
                // previous one because if the previous one throws
                // an exception, or the following one does, the
                // first catches blocks will be executed and when
                // RecipeDAO.deleteRecipe(recipeDTO) is called, it
                // in addition of deleting the recipe, it will also
                // delete the relationships created by the previous
                // method call if they have been created (DETACH DELETE r
                // inside the implementation of deleteRecipe)
                IngredientDAO.createIngredientIngredientRelationship(ingredients);
                // The following method call is at the same level of the
                // previous ones because if the previous one does not throw
                // an exception, but the following one does, the previous
                // method call does not have to be rolled back because
                // it is not expected a delete method call to be executed
                // for the creation of IngredientIngredientRelationships (see documentation)
                RegisteredUserDAO.createUserUsedIngredient(new RegisteredUserDTO(user.getId(), user.getUsername()), ingredients);
                return true;
            }
            catch(Neo4jException e){
                System.out.println("Neo4jException in uploadPostNeo4j: " + e.getMessage());
                RecipeDAO.deleteRecipe(recipeDTO);
                // deleteUserUsedIngredient does not have to be called because
                // the method call createUserUsedIngredient is the last one to be
                // executed and if it throws an exception, so it's not necessary 
                // to execute the rollback                
                return false;
            }
            catch(IllegalStateException e){
                System.out.println("IllegalStateException in uploadPostNeo4j: " + e.getMessage());
                RecipeDAO.deleteRecipe(recipeDTO);
                return false;
            }
            catch(Exception e){
                System.out.println("Exception in uploadPostNeo4j: " + e.getMessage());
                RecipeDAO.deleteRecipe(recipeDTO);
                return false;
            }
        }
        catch(Neo4jException e){
            if(created)
                System.err.println("Neo4j is inconsistent, Recipe " + recipeDTO.getName() + " has not been inserted in Ingredients");
            System.out.println("Neo4jException in uploadPostNeo4j: " + e.getMessage());
            return false;
        }
        catch(IllegalStateException e){
            if(created)
                System.err.println("Neo4j is inconsistent, Recipe " + recipeDTO.getName() + " has not been inserted in Ingredients");
            System.out.println("IllegalStateException in uploadPostNeo4j: " + e.getMessage());
            return false;
        }
        catch(Exception e){
            if(created)
                System.err.println("Neo4j is inconsistent, Recipe " + recipeDTO.getName() + " has not been inserted in Ingredients");
            System.out.println("Exception in uploadPostNeo4j: " + e.getMessage());
            return false;
        }
    }

    public boolean uploadPost(Post post, RegisteredUser user) {
        String id = uploadPostMongoDB(post, user);
        if(id == null)
            return false;
        PostDTO postDTO = new PostDTO(id, post.getRecipe().getImage(), post.getRecipe().getName());
        if(uploadPostNeo4j(user, postDTO, post))
            return true;
        else{
            if(deletePostMongoDB(postDTO, user)){
                // Databases are consistent again
            }
            else 
                System.err.println("Databases are not synchronized, Post " + id + " has not been added only in MongoDB");
            return false;
        }
    }

    public boolean modifyPost(Post post, PostDTO postDTO) {
        try{
            return PostDAO.modifyPost(post, postDTO);
        }
        catch(MongoException e){
            System.out.println("MongoException in modifyPost: " + e.getMessage());
            return false;
        }
        catch(IllegalArgumentException e){
            System.out.println("IllegalArgumentException in modifyPost: " + e.getMessage());
            return false;
        }
        catch(IllegalStateException e){
            System.out.println("IllegalStateException in modifyPost: " + e.getMessage());
            return false;
        }
        catch(Exception e){
            System.out.println("Exception in modifyPost: " + e.getMessage());
            return false;
        }
    }

    private boolean deletePostNeo4j(RegisteredUser user, PostDTO postDTO, Post post){
        RecipeDTO recipeDTO = new RecipeDTO(postDTO.getRecipeName(), postDTO.getImage());
        List<String> ingredients = new ArrayList<String>();
        for (Map.Entry<String, Double> entry : post.getRecipe().getIngredients().entrySet()) {
            ingredients.add(entry.getKey());
        }
        
        try{
            RecipeDAO.deleteRecipe(recipeDTO);
            try{
                RegisteredUserDAO.deleteUserUsedIngredient(new RegisteredUserDTO(user.getId(), user.getUsername()), ingredients);
                return true;
            }
            catch(Neo4jException e){
                System.out.println("Neo4jException in uploadPostNeo4j: " + e.getMessage());
                System.err.println("Neo4j is inconsistent, UserUsedIngredient relationships of recipe " + recipeDTO.getName() + " have not been deleted");
                return false;
            }
            catch(IllegalStateException e){
                System.out.println("Neo4jException in uploadPostNeo4j: " + e.getMessage());
                System.err.println("Neo4j is inconsistent, UserUsedIngredient relationships of recipe " + recipeDTO.getName() + " have not been deleted");
                return false;
            }
            catch(Exception e){
                System.out.println("Neo4jException in uploadPostNeo4j: " + e.getMessage());
                System.err.println("Neo4j is inconsistent, UserUsedIngredient relationships of recipe " + recipeDTO.getName() + " have not been deleted");
                return false;
            }
        }
        catch(Neo4jException e){
            System.out.println("Neo4jException in uploadPostNeo4j: " + e.getMessage());
            return false;
        }
        catch(IllegalStateException e){
            System.out.println("IllegalStateException in uploadPostNeo4j: " + e.getMessage());
            return false;
        }
        catch(Exception e){
            System.out.println("Exception in uploadPostNeo4j: " + e.getMessage());
            return false;
        }
    }

    public boolean deletePost(Post post, PostDTO postDTO, RegisteredUser user) {
        if(!deletePostMongoDB(postDTO, user))
            return false;
        if(deletePostNeo4j(user, postDTO, post))
            return true;
        else{
            System.err.println("Databases are not synchronized, Recipe " + postDTO.getId() + " has not been deleted in Neo4j");
            return false;
        }
    }

    public List<PostDTO> browseMostRecentTopRatedPosts(long hours, int limit) {
        try{
            return PostDAO.browseMostRecentTopRatedPosts(hours, limit);
        }
        catch(MongoException e){
            System.out.println("MongoException in browseMostRecentTopRatedPosts: " + e.getMessage());
            return null;
        }
        catch(IllegalArgumentException e){
            System.out.println("IllegalArgumentException in browseMostRecentTopRatedPosts: " + e.getMessage());
            return null;
        }
        catch(IllegalStateException e){
            System.out.println("IllegalStateException in browseMostRecentTopRatedPosts: " + e.getMessage());
            return null;
        }
        catch(Exception e){
            System.out.println("Exception in browseMostRecentTopRatedPosts: " + e.getMessage());
            return null;
        }
    }

    public List<PostDTO> browseMostRecentTopRatedPostsByIngredients(List<String> ingredientNames, long hours, int limit) {
        try{
            return PostDAO.browseMostRecentTopRatedPostsByIngredients(ingredientNames, hours, limit);
        }
        catch(MongoException e){
            System.out.println("MongoException in browseMostRecentTopRatedPostsByIngredients: " + e.getMessage());
            return null;
        }
        catch(IllegalArgumentException e){
            System.out.println("IllegalArgumentException in browseMostRecentTopRatedPostsByIngredients: " + e.getMessage());
            return null;
        }
        catch(IllegalStateException e){
            System.out.println("IllegalStateException in browseMostRecentTopRatedPostsByIngredients: " + e.getMessage());
            return null;
        }
        catch(Exception e){
            System.out.println("Exception in browseMostRecentTopRatedPostsByIngredients: " + e.getMessage());
            return null;
        }        
    }

    public List<PostDTO> browseMostRecentPostsByCalories(Double minCalories, Double maxCalories, long hours, int limit) {
        try{
            return PostDAO.browseMostRecentPostsByCalories(minCalories, maxCalories, hours, limit);
        }
        catch(MongoException e){
            System.out.println("MongoException in browseMostRecentPostsByCalories: " + e.getMessage());
            return null;
        }
        catch(IllegalArgumentException e){
            System.out.println("IllegalArgumentException in browseMostRecentPostsByCalories: " + e.getMessage());
            return null;
        }
        catch(IllegalStateException e){
            System.out.println("IllegalStateException in browseMostRecentPostsByCalories: " + e.getMessage());
            return null;
        }
        catch(Exception e){
            System.out.println("Exception in browseMostRecentPostsByCalories: " + e.getMessage());
            return null;
        } 
    }

    public Post findPostByPostDTO(PostDTO postDTO) {
        try{
            return PostDAO.findPostByPostDTO(postDTO);
        }
        catch(MongoException e){
            System.out.println("MongoException in findPostByPostDTO: " + e.getMessage());
            return null;
        }
        catch(IllegalArgumentException e){
            System.out.println("IllegalArgumentException in findPostByPostDTO: " + e.getMessage());
            return null;
        }
        catch(IllegalStateException e){
            System.out.println("IllegalStateException in findPostByPostDTO: " + e.getMessage());
            return null;
        }
        catch(Exception e){
            System.out.println("Exception in findPostByPostDTO: " + e.getMessage());
            return null;
        }
    }

    public Post findPostById(String _id) {
        try{
            return PostDAO.findPostById(_id);
        }
        catch(MongoException e){
            System.out.println("MongoException in findPostById: " + e.getMessage());
            return null;
        }
        catch(IllegalArgumentException e){
            System.out.println("IllegalArgumentException in findPostById: " + e.getMessage());
            return null;
        }
        catch(IllegalStateException e){
            System.out.println("IllegalStateException in findPostById: " + e.getMessage());
            return null;
        }
        catch(Exception e){
            System.out.println("Exception in findPostById: " + e.getMessage());
            return null;
        }    
    }

    public List<PostDTO> findPostsByRecipeName(String recipeName) {
        try{
            return PostDAO.findPostsByRecipeName(recipeName);
        }
        catch(MongoException e){
            System.out.println("MongoException in findPostsByRecipeName: " + e.getMessage());
            return null;
        }
        catch(IllegalArgumentException e){
            System.out.println("IllegalArgumentException in findPostsByRecipeName: " + e.getMessage());
            return null;
        }
        catch(IllegalStateException e){
            System.out.println("IllegalStateException in findPostsByRecipeName: " + e.getMessage());
            return null;
        }
        catch(Exception e){
            System.out.println("Exception in findPostsByRecipeName: " + e.getMessage());
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
        catch(MongoException e){
            System.out.println("MongoException in interactionsAnalysis: " + e.getMessage());
            return null;
        }
        catch(IllegalArgumentException e){
            System.out.println("IllegalArgumentException in interactionsAnalysis: " + e.getMessage());
            return null;
        }
        catch(IllegalStateException e){
            System.out.println("IllegalStateException in interactionsAnalysis: " + e.getMessage());
            return null;
        }
        catch(Exception e){
            System.out.println("Exception in interactionsAnalysis: " + e.getMessage());
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
        catch(MongoException e){
            System.out.println("MongoException in userInteractionsAnalysis: " + e.getMessage());
            return null;
        }
        catch(IllegalArgumentException e){
            System.out.println("IllegalArgumentException in userInteractionsAnalysis: " + e.getMessage());
            return null;
        }
        catch(IllegalStateException e){
            System.out.println("IllegalStateException in userInteractionsAnalysis: " + e.getMessage());
            return null;
        }
        catch(Exception e){
            System.out.println("Exception in userInteractionsAnalysis: " + e.getMessage());
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
        catch(MongoException e){
            System.out.println("MongoException in caloriesAnalysis: " + e.getMessage());
            return null;
        }
        catch(IllegalArgumentException e){
            System.out.println("IllegalArgumentException in caloriesAnalysis: " + e.getMessage());
            return null;
        }
        catch(IllegalStateException e){
            System.out.println("IllegalStateException in caloriesAnalysis: " + e.getMessage());
            return null;
        }
        catch(Exception e){
            System.out.println("Exception in caloriesAnalysis: " + e.getMessage());
            return null;
        }
    }

    // Given a user, show the average totalCalories of recipes publishished by him/her.
    public Double averageTotalCaloriesByUser(String username) {
        try{
            return PostDAO.averageTotalCaloriesByUser(username);
        }
        catch(MongoException e){
            System.out.println("MongoException in averageTotalCaloriesByUser: " + e.getMessage());
            return null;
        }
        catch(IllegalArgumentException e){
            System.out.println("IllegalArgumentException in averageTotalCaloriesByUser: " + e.getMessage());
            return null;
        }
        catch(IllegalStateException e){
            System.out.println("IllegalStateException in averageTotalCaloriesByUser: " + e.getMessage());
            return null;
        }
        catch(Exception e){
            System.out.println("Exception in averageTotalCaloriesByUser: " + e.getMessage());
            return null;
        }
    }

    public List<RecipeDTO> findRecipeByIngredients(List<String> ingredientNames) {
        try{
            return RecipeDAO.findRecipeByIngredients(ingredientNames);
        }
        catch(Neo4jException e){
            System.out.println("Neo4jException in findRecipeByIngredients: " + e.getMessage());
            return null;
        }
        catch(IllegalStateException e){
            System.out.println("IllegalStateException in findRecipeByIngredients: " + e.getMessage());
            return null;
        }
        catch(Exception e){
            System.out.println("Exception in findRecipeByIngredients: " + e.getMessage());
            return null;
        }
    }

}