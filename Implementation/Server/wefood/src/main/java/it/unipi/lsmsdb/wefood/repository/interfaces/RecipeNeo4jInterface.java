package it.unipi.lsmsdb.wefood.repository.interfaces;

import java.util.List;

import it.unipi.lsmsdb.wefood.dto.IngredientDTO;
import it.unipi.lsmsdb.wefood.dto.RecipeDTO;
import org.neo4j.driver.exceptions.Neo4jException;

public interface RecipeNeo4jInterface {

    boolean createRecipe(RecipeDTO recipeDTO) throws IllegalStateException, Neo4jException;

    boolean deleteRecipe(RecipeDTO recipeDTO) throws IllegalStateException, Neo4jException;

    List<RecipeDTO> findRecipeByIngredients(List<String> ingredientNames) throws IllegalStateException, Neo4jException;

    boolean createRecipeIngredientsRelationship(RecipeDTO recipeDTO, List<String> ingredientNames) throws IllegalStateException, Neo4jException;

}
