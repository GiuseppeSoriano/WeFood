package it.unipi.lsmsdb.wefood.repository.interfaces;

import java.util.List;

import it.unipi.lsmsdb.wefood.dto.IngredientDTO;

public interface IngredientNeo4jInterface {

    boolean createIngredient(IngredientDTO ingredientDTO);

    // Suggest most popular combination of ingredients 
    List<IngredientDTO> findIngredientsUsedWithIngredient(IngredientDTO ingredientDTO, int limit);
    List<IngredientDTO> findIngredientsUsedWithIngredient(IngredientDTO ingredientDTO);
    

}
