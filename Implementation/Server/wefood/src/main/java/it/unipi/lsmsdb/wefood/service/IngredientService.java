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
            catch(Neo4jException e){
                IngredientDAO.deleteIngredientMongoDB("ObjectId('"+id+"')");
                System.out.println("Neo4jException in IngredientService.createIngredient: " + e.getMessage());
                return false;
            }
            catch(IllegalStateException e){
                IngredientDAO.deleteIngredientMongoDB("ObjectId('"+id+"')");
                System.out.println("IllegalStateException in IngredientService.createIngredient: " + e.getMessage());
                return false;
            }
            catch(Exception e){
                IngredientDAO.deleteIngredientMongoDB("ObjectId('"+id+"')");
                System.out.println("Exception in IngredientService.createIngredient: " + e.getMessage());
                return false;
            }
        }
        catch(MongoException e){
            if(id != "")
                System.err.println("Databases are not synchronized, ingredient " + id + " has been created in MongoDB but not in Neo4j");
            System.out.println("MongoException in IngredientService.createIngredient: " + e.getMessage());
            return false;
        }
        catch(IllegalArgumentException e){
            if(id != "")
                System.err.println("Databases are not synchronized, ingredient " + id + " has been created in MongoDB but not in Neo4j");
            System.out.println("IllegalArgumentException in IngredientService.createIngredient: " + e.getMessage());
            return false;
        }
        catch(IllegalStateException e){
            if(id != "")
                System.err.println("Databases are not synchronized, ingredient " + id + " has been created in MongoDB but not in Neo4j");
            System.out.println("IllegalStateException in IngredientService.createIngredient: " + e.getMessage());
            return false;
        }
        catch(Exception e){
            if(id != "")
                System.err.println("Databases are not synchronized, ingredient " + id + " has been created in MongoDB but not in Neo4j");
            System.out.println("Exception in IngredientService.createIngredient: " + e.getMessage());
            return false;
        }
    }
    
    public Ingredient findIngredientByName(String name){
        try{
            return IngredientDAO.findIngredientByName(name);
        }
        catch(MongoException e){
            System.out.println("MongoException in IngredientService.findIngredientByName: " + e.getMessage());
            return null;
        }
        catch(IllegalArgumentException e){
            System.out.println("IllegalArgumentException in IngredientService.findIngredientByName: " + e.getMessage());
            return null;
        }
        catch(IllegalStateException e){
            System.out.println("IllegalStateException in IngredientService.findIngredientByName: " + e.getMessage());
            return null;
        }
        catch(Exception e){
            System.out.println("Exception in IngredientService.findIngredientByName: " + e.getMessage());
            return null;
        }
    }

    public List<Ingredient> getAllIngredients(){
        try{
            return IngredientDAO.getAllIngredients();
        }
        catch(MongoException e){
            System.out.println("MongoException in IngredientService.getAllIngredients: " + e.getMessage());
            return null;
        }
        catch(IllegalArgumentException e){
            System.out.println("IllegalArgumentException in IngredientService.getAllIngredients: " + e.getMessage());
            return null;
        }
        catch(IllegalStateException e){
            System.out.println("IllegalStateException in IngredientService.getAllIngredients: " + e.getMessage());
            return null;
        }
        catch(Exception e){
            System.out.println("Exception in IngredientService.getAllIngredients: " + e.getMessage());
            return null;
        }
    }

    public List<String> mostPopularCombinationOfIngredients(String ingredientName){
        try{
            return IngredientDAO.mostPopularCombinationOfIngredients(ingredientName);
        }
        catch(Neo4jException e){
            System.out.println("Neo4JException in IngredientService.mostPopularCombinationOfIngredients: " + e.getMessage());
            return null;
        }
        catch(IllegalStateException e){
            System.out.println("IllegalStateException in IngredientService.mostPopularCombinationOfIngredients: " + e.getMessage());
            return null;
        }
        catch(Exception e){
            System.out.println("Exception in IngredientService.mostPopularCombinationOfIngredients: " + e.getMessage());
            return null;
        }
    }

    public List<String> findNewIngredientsBasedOnFriendsUsage(RegisteredUserDTO user){
        try{
            return IngredientDAO.findNewIngredientsBasedOnFriendsUsage(user);
        }
        catch(Neo4jException e){
            System.out.println("Neo4JException in IngredientService.findNewIngredientsBasedOnFriendsUsage: " + e.getMessage());
            return null;
        }
        catch(IllegalStateException e){
            System.out.println("IllegalStateException in IngredientService.findNewIngredientsBasedOnFriendsUsage: " + e.getMessage());
            return null;
        }
        catch(Exception e){
            System.out.println("Exception in IngredientService.findNewIngredientsBasedOnFriendsUsage: " + e.getMessage());
            return null;
        }
    }

    public List<String> findMostUsedIngredientsByUser(RegisteredUserDTO user){
        try{
            return IngredientDAO.findMostUsedIngredientsByUser(user);
        }
        catch(Neo4jException e){
            System.out.println("Neo4JException in IngredientService.findMostUsedIngredientsByUser: " + e.getMessage());
            return null;
        }
        catch(IllegalStateException e){
            System.out.println("IllegalStateException in IngredientService.findMostUsedIngredientsByUser: " + e.getMessage());
            return null;
        }
        catch(Exception e){
            System.out.println("Exception in IngredientService.findMostUsedIngredientsByUser: " + e.getMessage());
            return null;
        }
    }

    public List<String> findMostLeastUsedIngredients(boolean DESC){
        try{
            return IngredientDAO.findMostLeastUsedIngredients(DESC);
        }
        catch(Neo4jException e){
            System.out.println("Neo4JException in IngredientService.findMostLeastUsedIngredients: " + e.getMessage());
            return null;
        }
        catch(IllegalStateException e){
            System.out.println("IllegalStateException in IngredientService.findMostLeastUsedIngredients: " + e.getMessage());
            return null;
        }
        catch(Exception e){
            System.out.println("Exception in IngredientService.findMostLeastUsedIngredients: " + e.getMessage());
            return null;
        }
    }
}