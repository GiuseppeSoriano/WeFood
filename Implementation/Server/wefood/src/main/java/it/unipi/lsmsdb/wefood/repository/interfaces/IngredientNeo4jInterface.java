package it.unipi.lsmsdb.wefood.repository.interfaces;

import java.util.List;

import it.unipi.lsmsdb.wefood.dto.IngredientDTO;
import it.unipi.lsmsdb.wefood.dto.RegisteredUserDTO;

public interface IngredientNeo4jInterface {

    boolean createIngredient(IngredientDTO ingredientDTO);

    // Suggest most popular combination of ingredients 
    List<IngredientDTO> findIngredientsUsedWithIngredient(IngredientDTO ingredientDTO, int limit);
    List<IngredientDTO> findIngredientsUsedWithIngredient(IngredientDTO ingredientDTO);
    
    public boolean createIngredientIngredientRelationship(List<IngredientDTO> ingredientDTOs);

    public List<IngredientDTO> mostPopularCombinationOfIngredients(IngredientDTO ingredient);
    
    public List<IngredientDTO> findNewIngredientsBasedOnFriendsUsage(RegisteredUserDTO user);

    public List<IngredientDTO> findMostUsedIngredientsByUser(RegisteredUserDTO user);
    public List<IngredientDTO> findMostLeastUsedIngredients(boolean DESC);
}
