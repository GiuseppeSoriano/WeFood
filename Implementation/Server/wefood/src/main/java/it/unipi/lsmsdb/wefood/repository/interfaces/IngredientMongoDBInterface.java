package it.unipi.lsmsdb.wefood.repository.interfaces;

import it.unipi.lsmsdb.wefood.model.Ingredient;

public interface IngredientMongoDBInterface {

    boolean createIngredient(Ingredient ingredient);
    
    Ingredient findIngredientByName(String name);


}
