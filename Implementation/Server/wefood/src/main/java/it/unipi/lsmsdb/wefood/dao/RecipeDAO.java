package it.unipi.lsmsdb.wefood.dao;

import it.unipi.lsmsdb.wefood.dto.IngredientDTO;
import it.unipi.lsmsdb.wefood.dto.RecipeDTO;
import it.unipi.lsmsdb.wefood.repository.interfaces.RecipeNeo4jInterface;
import it.unipi.lsmsdb.wefood.repository.neo4j.RecipeNeo4j;
import org.neo4j.driver.exceptions.Neo4jException;

import java.util.List;

public class RecipeDAO {
    private final static RecipeNeo4jInterface recipeNeo4j = new RecipeNeo4j();

    public static boolean createRecipe(RecipeDTO recipeDTO) throws IllegalStateException, Neo4jException {
        return recipeNeo4j.createRecipe(recipeDTO);
    }

    public static boolean deleteRecipe(RecipeDTO recipeDTO) throws IllegalStateException, Neo4jException {
        return recipeNeo4j.deleteRecipe(recipeDTO);
    }

    public static List<RecipeDTO> findRecipeByIngredients(List<IngredientDTO> ingredientDTOs) throws IllegalStateException, Neo4jException {
        return recipeNeo4j.findRecipeByIngredients(ingredientDTOs);
    }

    public static boolean createRecipeIngredientsRelationship(RecipeDTO recipeDTO, List<String> ingredientNames) throws IllegalStateException, Neo4jException {
        return recipeNeo4j.createRecipeIngredientsRelationship(recipeDTO, ingredientNames);
    }

}
