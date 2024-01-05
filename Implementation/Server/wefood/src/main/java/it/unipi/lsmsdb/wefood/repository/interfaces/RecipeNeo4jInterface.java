package it.unipi.lsmsdb.wefood.repository.interfaces;

import java.util.List;

import it.unipi.lsmsdb.wefood.dto.IngredientDTO;
import it.unipi.lsmsdb.wefood.dto.RecipeDTO;

public interface RecipeNeo4jInterface {

    boolean createRecipe(RecipeDTO recipeDTO);

    boolean deleteRecipe(RecipeDTO recipeDTO);

    List<RecipeDTO> findRecipeByIngredients(List<IngredientDTO> ingredientDTOs);

}
