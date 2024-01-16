package it.unipi.lsmsdb.wefood.dao;

import it.unipi.lsmsdb.wefood.dto.PostDTO;
import it.unipi.lsmsdb.wefood.repository.interfaces.RecipeNeo4jInterface;
import it.unipi.lsmsdb.wefood.repository.neo4j.RecipeNeo4j;
import org.neo4j.driver.exceptions.Neo4jException;

import java.util.List;

public class RecipeDAO {
    private final static RecipeNeo4jInterface recipeNeo4j = new RecipeNeo4j();

    public static boolean createRecipe(PostDTO postDTO) throws IllegalStateException, Neo4jException {
        return recipeNeo4j.createRecipe(postDTO);
    }

    public static boolean deleteRecipe(PostDTO postDTO) throws IllegalStateException, Neo4jException {
        return recipeNeo4j.deleteRecipe(postDTO);
    }

    public static List<PostDTO> findRecipeByIngredients(List<String> ingredientNames) throws IllegalStateException, Neo4jException {
        return recipeNeo4j.findRecipeByIngredients(ingredientNames);
    }

    public static boolean createRecipeIngredientsRelationship(PostDTO postDTO, List<String> ingredientNames) throws IllegalStateException, Neo4jException {
        return recipeNeo4j.createRecipeIngredientsRelationship(postDTO, ingredientNames);
    }

}
