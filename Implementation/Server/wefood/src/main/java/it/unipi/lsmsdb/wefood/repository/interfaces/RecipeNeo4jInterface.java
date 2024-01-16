package it.unipi.lsmsdb.wefood.repository.interfaces;

import java.util.List;

import it.unipi.lsmsdb.wefood.dto.PostDTO;
import org.neo4j.driver.exceptions.Neo4jException;

public interface RecipeNeo4jInterface {

    boolean createRecipe(PostDTO postDTO) throws IllegalStateException, Neo4jException;

    boolean deleteRecipe(PostDTO postDTO) throws IllegalStateException, Neo4jException;

    List<PostDTO> findRecipeByIngredients(List<String> ingredientNames) throws IllegalStateException, Neo4jException;

    boolean createRecipeIngredientsRelationship(PostDTO postDTO, List<String> ingredientNames) throws IllegalStateException, Neo4jException;

}
