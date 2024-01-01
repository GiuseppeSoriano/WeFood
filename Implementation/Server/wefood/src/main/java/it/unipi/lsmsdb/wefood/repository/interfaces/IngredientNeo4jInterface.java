package it.unipi.lsmsdb.wefood.repository.interfaces;

import it.unipi.lsmsdb.wefood.model.Ingredient;

public interface IngredientNeo4jInterface {

    boolean createIngredient(Ingredient ingredient);
    

}
