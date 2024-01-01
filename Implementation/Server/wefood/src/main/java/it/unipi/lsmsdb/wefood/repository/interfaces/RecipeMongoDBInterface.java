package it.unipi.lsmsdb.wefood.repository.interfaces;

import it.unipi.lsmsdb.wefood.model.Post;
import it.unipi.lsmsdb.wefood.model.Recipe;

public interface RecipeMongoDBInterface {

    boolean createRecipe(Recipe recipe);

    Recipe findRecipeByPost(Post post);
    

}
