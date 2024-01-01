package it.unipi.lsmsdb.wefood.repository.interfaces;

import it.unipi.lsmsdb.wefood.model.Ingredient;

public interface IngredientMongoDBInterface {

    Ingredient findIngredientByName(String name);

    boolean createIngredient(Ingredient ingredient);
    

}
