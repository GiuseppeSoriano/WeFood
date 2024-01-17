package it.unipi.lsmsdb.wefood.service;

import it.unipi.lsmsdb.wefood.model.Ingredient;

import java.util.List;

import org.neo4j.driver.exceptions.Neo4jException;
import org.springframework.stereotype.Service;

import com.mongodb.MongoException;

import it.unipi.lsmsdb.wefood.dao.IngredientDAO;
import it.unipi.lsmsdb.wefood.dto.IngredientDTO;
import it.unipi.lsmsdb.wefood.dto.RegisteredUserDTO;

@Service
public class IngredientService {

    public boolean createIngredient(Ingredient ingredient){
        String id = "";
        try{
            id = IngredientDAO.createIngredientMongoDB(ingredient);
            try{
                return IngredientDAO.createIngredientNeo4j(new IngredientDTO(id, ingredient.getName()));
            }
            // Other types of exceptions can be handled if necessary: Neo4jException, IllegalStateException
            catch(Exception e) {
                IngredientDAO.deleteIngredientMongoDB("ObjectId('"+id+"')");
                System.err.println("Exception in IngredientDAO.createIngredient: " + e.getMessage());
                return false;
            }
        }
        // Other types of exceptions can be handled if necessary: MongoException, IllegalArgumentException, IllegalStateException
        catch(Exception e){
            if(id != "")
                System.err.println("Databases are not synchronized, ingredient " + id + " has been created in MongoDB but not in Neo4j");
            System.err.println("MongoException in IngredientService.createIngredient: " + e.getMessage());
            return false;
        }
    }
    
    public Ingredient findIngredientByName(String name){
        try{
            return IngredientDAO.findIngredientByName(name);
        }
        // Other types of exceptions can be handled if necessary: MongoException, IllegalArgumentException, IllegalStateException
        catch(Exception e) {
            System.err.println("Exception in IngredientDAO.findIngredientByName: " + e.getMessage());
            return null;
        }
    }

    public List<Ingredient> getAllIngredients(){
        try{
            return IngredientDAO.getAllIngredients();
        }
        // Other types of exceptions can be handled if necessary: MongoException, IllegalArgumentException, IllegalStateException
        catch(Exception e) {
            System.err.println("Exception in IngredientDAO.getAllIngredients: " + e.getMessage());
            return null;
        }
    }

    public List<String> mostPopularCombinationOfIngredients(String ingredientName){
        try{
            return IngredientDAO.mostPopularCombinationOfIngredients(ingredientName);
        }
        // Other types of exceptions can be handled if necessary: Neo4jException, IllegalStateException
        catch(Exception e) {
            System.err.println("Exception in IngredientDAO.mostPopularCombinationOfIngredients: " + e.getMessage());
            return null;
        }
    }

    public List<String> findNewIngredientsBasedOnFriendsUsage(RegisteredUserDTO user){
        try{
            return IngredientDAO.findNewIngredientsBasedOnFriendsUsage(user);
        }
        // Other types of exceptions can be handled if necessary: Neo4jException, IllegalStateException
        catch(Exception e) {
            System.err.println("Exception in IngredientDAO.findNewIngredientsBasedOnFriendsUsage: " + e.getMessage());
            return null;
        }
    }

    public List<String> findMostUsedIngredientsByUser(RegisteredUserDTO user){
        try{
            return IngredientDAO.findMostUsedIngredientsByUser(user);
        }
        // Other types of exceptions can be handled if necessary: Neo4jException, IllegalStateException
        catch(Exception e) {
            System.err.println("Exception in IngredientDAO.findMostUsedIngredientsByUser: " + e.getMessage());
            return null;
        }
    }

    public List<String> findMostLeastUsedIngredients(boolean DESC){
        try{
            return IngredientDAO.findMostLeastUsedIngredients(DESC);
        }
        // Other types of exceptions can be handled if necessary: Neo4jException, IllegalStateException
        catch(Exception e) {
            System.err.println("Exception in IngredientDAO.findMostLeastUsedIngredients: " + e.getMessage());
            return null;
        }
    }
}