package it.unipi.lsmsdb.wefood.repository.interfaces;

import java.util.List;

import it.unipi.lsmsdb.wefood.dto.IngredientDTO;
import it.unipi.lsmsdb.wefood.dto.RegisteredUserDTO;
import org.neo4j.driver.exceptions.Neo4jException;

public interface IngredientNeo4jInterface {

    boolean createIngredient(IngredientDTO ingredientDTO);

    // Suggest most popular combination of ingredients 
    List<IngredientDTO> findIngredientsUsedWithIngredient(IngredientDTO ingredientDTO, int limit) throws IllegalStateException, Neo4jException;
    List<IngredientDTO> findIngredientsUsedWithIngredient(IngredientDTO ingredientDTO) throws IllegalStateException, Neo4jException;
    
    boolean createIngredientIngredientRelationship(List<String> ingredientNames) throws IllegalStateException, Neo4jException;

    List<IngredientDTO> mostPopularCombinationOfIngredients(IngredientDTO ingredient) throws IllegalStateException, Neo4jException;
    
    List<IngredientDTO> findNewIngredientsBasedOnFriendsUsage(RegisteredUserDTO user) throws IllegalStateException, Neo4jException;

    List<IngredientDTO> findMostUsedIngredientsByUser(RegisteredUserDTO user) throws IllegalStateException, Neo4jException;
    List<IngredientDTO> findMostLeastUsedIngredients(boolean DESC) throws IllegalStateException, Neo4jException;
}
