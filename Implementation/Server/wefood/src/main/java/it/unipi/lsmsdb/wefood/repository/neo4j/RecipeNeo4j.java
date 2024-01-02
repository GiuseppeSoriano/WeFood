package it.unipi.lsmsdb.wefood.repository.neo4j;

import it.unipi.lsmsdb.wefood.model.Recipe;
import it.unipi.lsmsdb.wefood.model.Ingredient;
import it.unipi.lsmsdb.wefood.dto.RecipeDTO;
import it.unipi.lsmsdb.wefood.dto.IngredientDTO;
import org.neo4j.driver.Record;
import java.util.List;
import it.unipi.lsmsdb.wefood.repository.base.BaseNeo4j;
import it.unipi.lsmsdb.wefood.repository.interfaces.RecipeNeo4jInterface;

public class RecipeNeo4j implements RecipeNeo4jInterface {

    public boolean createRecipe(Recipe recipe) {
        String query = "";
        List<Record> results = BaseNeo4j.executeQuery(query);
        if (results.isEmpty()) {
            return false;
        } else {
            return true;
        }
    }; //da implementare
    
    public boolean deleteRecipe(Recipe recipe) {
        String query = "";
        List<Record> results = BaseNeo4j.executeQuery(query);
        if (results.isEmpty()) {
            return false;
        } else {
            return true;
        }
    }

    public List<RecipeDTO> findRecipeByIngredients(List<IngredientDTO> ingredients) {
        String query = "";
        List<Record> results = BaseNeo4j.executeQuery(query);
        if (results.isEmpty()) {
            return null;
        } else {
            /*
                ritornare una lista di RecipeDTO
                sotto forma di List<RecipeDTO> recipe = new LinkedList<> ...
            */
           return recipe;
        }
    }
}