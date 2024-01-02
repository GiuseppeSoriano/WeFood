package it.unipi.lsmsdb.wefood.repository.interfaces;

import java.util.List;

import it.unipi.lsmsdb.wefood.dto.IngredientDTO;
import it.unipi.lsmsdb.wefood.dto.RecipeDTO;
import it.unipi.lsmsdb.wefood.model.Recipe;

public interface RecipeNeo4jInterface {

    boolean createRecipe(Recipe recipe);

    boolean deleteRecipe(Recipe recipe);

    List<RecipeDTO> findRecipeByIngredients(List<IngredientDTO> ingredientDTOs);


}
