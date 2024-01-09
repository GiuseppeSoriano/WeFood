package it.unipi.lsmsdb.wefood.repository.interfaces;

import java.util.List;

import it.unipi.lsmsdb.wefood.dto.IngredientDTO;
import it.unipi.lsmsdb.wefood.dto.RegisteredUserDTO;
import org.neo4j.driver.exceptions.Neo4jException;

public interface IngredientNeo4jInterface {

    boolean createIngredient(IngredientDTO ingredientDTO);

    // Suggest most popular combination of ingredients 
    List<String> findIngredientsUsedWithIngredient(String ingredientName, int limit) throws IllegalStateException, Neo4jException;
    List<String> findIngredientsUsedWithIngredient(String ingredientName) throws IllegalStateException, Neo4jException;
    
    boolean createIngredientIngredientRelationship(List<String> ingredientNames) throws IllegalStateException, Neo4jException;

    List<String> mostPopularCombinationOfIngredients(String ingredientName) throws IllegalStateException, Neo4jException;
    
    List<String> findNewIngredientsBasedOnFriendsUsage(RegisteredUserDTO user) throws IllegalStateException, Neo4jException;

    List<String> findMostUsedIngredientsByUser(RegisteredUserDTO user) throws IllegalStateException, Neo4jException;
    List<String> findMostLeastUsedIngredients(boolean DESC) throws IllegalStateException, Neo4jException;
}
