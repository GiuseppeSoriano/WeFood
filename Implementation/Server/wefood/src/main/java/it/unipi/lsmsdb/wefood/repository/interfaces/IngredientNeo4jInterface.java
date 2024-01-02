package it.unipi.lsmsdb.wefood.repository.interfaces;

import java.util.List;

import it.unipi.lsmsdb.wefood.dto.IngredientDTO;

public interface IngredientNeo4jInterface {

    boolean createIngredient(IngredientDTO ingredientDTO);

    List<IngredientDTO> findIngredientsUsedWithIngredient(IngredientDTO ingredientDTO);
    

}
