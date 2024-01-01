package it.unipi.lsmsdb.wefood.repository.interfaces;

import it.unipi.lsmsdb.wefood.model.Recipe;

public interface RecipeNeo4jInterface {

    boolean createRecipe(Recipe recipe);


}
